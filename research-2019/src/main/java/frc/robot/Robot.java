/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import java.util.ArrayList;
import com.kauailabs.navx.frc.*;
import frc.util.snail_vision.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.*;
import java.io.*;

import edu.wpi.first.wpilibj.I2C.Port;

public class Robot extends TimedRobot {

    WPI_TalonSRX FrontRight;
    WPI_TalonSRX FrontLeft;
    WPI_TalonSRX BackRight;
    WPI_TalonSRX BackLeft;
    SpeedControllerGroup Right;
    SpeedControllerGroup Left;
    DifferentialDrive DriveTrain;
    XboxController Controller;
    ArrayList<Double> DistanceToArea;
    Boolean leftStickPressed;
    Boolean rightStickPressed;
    double driveSpeed;
    double turnSpeed;
    int savedData;

    SnailVision vision;
    public static final double[] AREA_TO_DISTANCE_ROCKET = {1};
    public static final double[] AREA_TO_DISTANCE_SHIP = {1};
    PrintWriter out;

    @Override
    public void robotInit () {

        FrontLeft = new WPI_TalonSRX(3);
        BackLeft = new WPI_TalonSRX(6);
        BackRight = new WPI_TalonSRX(1);
        FrontRight = new WPI_TalonSRX(2);

        Right = new SpeedControllerGroup(FrontRight, BackRight);
        Left = new SpeedControllerGroup(FrontLeft, BackLeft);

        DriveTrain = new DifferentialDrive(Left, Right);

        Controller = new XboxController(0);

        driveSpeed = 0;
        turnSpeed = 0;

        vision = new SnailVision(false);

        vision.ANGLE_CORRECT_P = -0.03;
        vision.ANGLE_CORRECT_F = 0.05;
        vision.ANGLE_CORRECT_MIN_ANGLE = 2.0; // degrees
        
        vision.GET_IN_DISTANCE_P = 4.0;
        vision.GET_IN_DISTANCE_ERROR = 3.0; // inches
        vision.DISTANCE_ESTIMATION_METHOD = "area";

        vision.JERK_COLLISION_THRESHOLD = 1;

        vision.TARGETS.add(new Target(0, 12, 60, AREA_TO_DISTANCE_ROCKET)); // Rocket
        vision.TARGETS.add(new Target(0, 12, 60, AREA_TO_DISTANCE_SHIP));
        // SmartDashboard.putNumber("kP", -0.3);
        // SmartDashboard.putNumber("min_command", 0.05); 

        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter("VisionPrediction.csv")));
        }
        catch(IOException e){}

        savedData = 0;
    }
    @Override
    public void teleopInit () {

    }

    @Override
    public void teleopPeriodic () {
        // NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        vision.networkTableFunctionality(NetworkTableInstance.getDefault().getTable("limelight"));

        driveSpeed = 0;
        turnSpeed = 0;

        if(savedData < 54000){
            savedData++;
            // Latency, Tx, Ty, Horizontal, Vertical, Skew, robot angle, turn velocity, turn accelleration, Target Area, Target Visibility
            out.println(vision.Latency.get(0) + "," + vision.TargetX.get(0) + "," + vision.TargetY.get(0) + "," + vision.TargetHorizontal.get(0) + "," + vision.TargetVertical.get(0) + "," + vision.TargetS.get(0) + "," + vision.getRotationalAngle() + "," + vision.navx.getVelocityX() + "," +  + vision.getAccelleration() + "," + vision.TargetA.get(0) + "," + vision.TargetV.get(0));
            vision.resetRotationalAngle();
        } 
       
        if(savedData == 54000){// 10 minutes
            out.close();
            savedData++;
        }
         // Basic Teleop Drive Code
         if(Controller.getAButton()) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kLeft);
            driveSpeed = -y;
            turnSpeed = x;
        } 
        else if(Controller.getBumper(GenericHID.Hand.kLeft)) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kRight);
            driveSpeed = -y;
            turnSpeed = x;
        } 
        else if(Controller.getBumper(GenericHID.Hand.kRight)) {
            double x = Controller.getX(GenericHID.Hand.kLeft);
            double y = Controller.getY(GenericHID.Hand.kRight);
            driveSpeed = -y;
            turnSpeed = x;
        }
        if(Controller.getStickButtonPressed(GenericHID.Hand.kRight)){
            vision.recordTargetArea();
        }
        if(Controller.getStickButtonReleased(GenericHID.Hand.kRight)){
            vision.clearTargetArea();
        }
        if(Controller.getStickButtonPressed(GenericHID.Hand.kLeft)){
            vision.printTargetArea();
        }

        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0){ //If left trigger pressed and a target on screen then turn to it
            
            turnSpeed += vision.angleCorrect();
        }
    }
 

}