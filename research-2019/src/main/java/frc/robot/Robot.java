/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.vision.*;
import frc.robot.constants.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.ArrayList;

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
    boolean takingSnapshot;
    double driveSpeed;
    double turnSpeed;
    
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

        DistanceToArea = new ArrayList<Double>();

        SmartDashboard.putNumber("kP", -0.3);
        SmartDashboard.putNumber("min_command", 0.05); 

        leftStickPressed = false;
        rightStickPressed = false;
        takingSnapshot = false;

        driveSpeed = 0;
        turnSpeed = 0;
    }

    @Override
    public void teleopInit () {

    }

    @Override
    public void teleopPeriodic () {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        System.out.println("short " + table.getEntry("tshort").getDouble(0));
        System.out.println("long " + table.getEntry("tlong ").getDouble(0));
        System.out.println("ratio " + table.getEntry("tshort").getDouble(0)/table.getEntry("tlong ").getDouble(0));

        // Basic Teleop Drive Code
        if(Controller.getAButton()) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kLeft);
            // DriveTrain.arcadeDrive(-y, x);
            driveSpeed = -y;
            turnSpeed = x;
        } else if(Controller.getBumper(GenericHID.Hand.kLeft)) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kRight);
            // DriveTrain.arcadeDrive(-y, x);
            driveSpeed = -y;
            turnSpeed = x;
        } else if(Controller.getBumper(GenericHID.Hand.kRight)) {
            double y = Controller.getX(GenericHID.Hand.kRight);
            double x = Controller.getY(GenericHID.Hand.kLeft);
            // DriveTrain.arcadeDrive(-y, x);
            driveSpeed = -y;
            turnSpeed = x;
        }

        // Limelight vision code  temp ==&& tvE.getDouble(0) == 1
        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5){ //If left trigger pressed and a target on screen then turn to it
            // DriveTrain.arcadeDrive(0, Vision.angleCorrect(table));
            turnSpeed += Vision.angleCorrect(table);
        }
        if(Controller.getTriggerAxis(GenericHID.Hand.kRight) > 0.5){
            Vision.shoot(table, DriveTrain);
        }
        if(Controller.getXButton()){
            // Vision.findObject(table, DriveTrain);
        }
        if(Controller.getYButton()){
            Vision.findCameraAngle(table, 120);
        }
        if(Controller.getBButton()){
            // DriveTrain.arcadeDrive(Vision.getInDistance(table), 0);
            driveSpeed += Vision.getInDistance(table);
        }
        if(Controller.getStickButtonPressed(GenericHID.Hand.kRight) && rightStickPressed == false){
            addDistancePercent(table);
            rightStickPressed = true;
        }
        else if(Controller.getStickButtonReleased(GenericHID.Hand.kRight) && rightStickPressed == true){
            rightStickPressed = false;
        }
        if(Controller.getStickButtonPressed(GenericHID.Hand.kLeft) && leftStickPressed == false){
            printDistancePercent();
            leftStickPressed = true;
        }
        else if(Controller.getStickButtonReleased(GenericHID.Hand.kLeft) && leftStickPressed == true){
            leftStickPressed = false;
        }

        if(turnSpeed != 0 || driveSpeed != 0){
            DriveTrain.arcadeDrive(driveSpeed, turnSpeed);
        }
    }
 
    public void addDistancePercent(NetworkTable table){
        NetworkTableEntry taE = table.getEntry("ta");
        DistanceToArea.add(taE.getDouble(0));
        System.out.println(DistanceToArea.size());
    }	    

    public void printDistancePercent(){
        for(int i = 0; i < DistanceToArea.size() - 1; i++){
            System.out.print(DistanceToArea.get(i) + ", ");
        }
        System.out.println(DistanceToArea.get(DistanceToArea.size() - 1));
    }
}