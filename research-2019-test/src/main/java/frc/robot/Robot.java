/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Robot extends IterativeRobot {

    WPI_TalonSRX FrontRight;
    WPI_TalonSRX FrontLeft;
    WPI_TalonSRX BackRight;
    WPI_TalonSRX BackLeft;
    SpeedControllerGroup Right;
    SpeedControllerGroup Left;
    DifferentialDrive DriveTrain;
    XboxController Controller;
    Target target1; //Vision
    public static double cameraHeight;
    public static double cameraAngle;

    @Override
    public void robotInit () {
        FrontRight = new WPI_TalonSRX(2);
        FrontLeft = new WPI_TalonSRX(3);
        BackRight = new WPI_TalonSRX(4);
        BackLeft = new WPI_TalonSRX(5);

        Right = new SpeedControllerGroup(FrontRight, BackRight);
        Left = new SpeedControllerGroup(FrontLeft, BackLeft);

        DriveTrain = new DifferentialDrive(Left, Right);

        Controller = new XboxController(1);

        // Vision
        target1 = new Target(48, 36); //height of 4 ft, desired distance of 3 ft
        cameraHeight = 8; //placeholder fix-ITNOWHJIFOSOIFEJS
        cameraAngle = 0; //Correct
    }

    @Override
    public void teleopInit () {

    }

    @Override
    public void teleopPeriodic () {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); 
        NetworkTableEntry tvE = table.getEntry("tv");

        // Basic Teleop Drive Code
        if(Controller.getAButton()) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kLeft);
            DriveTrain.arcadeDrive(y, x);
        } else if(Controller.getBumper(GenericHID.Hand.kLeft)) {
            double y = Controller.getY(GenericHID.Hand.kLeft);
            double x = Controller.getX(GenericHID.Hand.kRight);
            DriveTrain.arcadeDrive(y, x);
        } else if(Controller.getBumper(GenericHID.Hand.kRight)) {
            double y = Controller.getX(GenericHID.Hand.kRight);
            double x = Controller.getY(GenericHID.Hand.kLeft);
            DriveTrain.arcadeDrive(y, x);
        }

        // Limelight vision code  temp ==&& tvE.getDouble(0) == 1
        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5){ //If left trigger pressed and a target on screen then turn to it
            angleCorrect(table);
        }
        if(Controller.getTriggerAxis(GenericHID.Hand.kRight) > 0.5){
            shoot(table, target1);
        }
        if(Controller.getXButton()){
            findObject(table);
        }
        
    }
  
    public void angleCorrect(NetworkTable table){

      NetworkTableEntry txE = table.getEntry("tx");
      double tx = txE.getDouble(0); //Gets the angle of how far away from the corsshair the object is

      double min_command = 0.05; //Minimum motor input to move robot in case P can't do it 
      double Kp = -0.03; // for PID
      double heading_error = tx; 
      double steering_adjust = 0.0;

      if (tx > 1.0){ // If tx > 1.0, do normal pid
        steering_adjust = Kp * heading_error - min_command;
      } 
      else if (tx < 1.0) // If tx 1.0, the motor will not be able to move the robot due to friction, so min_command is added to give it the minimum speed to move
      {
        steering_adjust = Kp * heading_error + min_command;
      }

      DriveTrain.arcadeDrive(0, - steering_adjust);
    }

    public void getInDistance(NetworkTable table, Target target){ // Target is a class with the information about the specific target
    //Target exists so that if the robot has two different targets it needs to get in distance of, it can refer to target's info
    //and see the height of the target, its desired distance, launch power, etc. 
        double KpDistance = 0.1;
        double desiredDistance = target.m_desiredDistance; // 60 inches - uses trig so it does not matter which unit as long as unit is uniform
        double currentDistance = distanceFromObject(table, target.m_targetHeight, cameraHeight, cameraAngle); //cameraHeight and cameraAngle are constants
        double distanceError = desiredDistance - currentDistance;
        if(distanceError > 10){ // 10 inches of error space for PID
            double driving_adjust = KpDistance * distanceError;
            DriveTrain.arcadeDrive(driving_adjust, 0);
        }
        else{
            angleCorrect(table); //Haven't tried running both at once, but I don't want our motors to explode so probably best to do this
        }
    }

    public void findObject(NetworkTable table){ // Spins until the robot finds the target
        NetworkTableEntry tvE = table.getEntry("tv");
        double tv = tvE.getDouble(0);
        if(tv == 0.0){ // No target on screen, then spin
            DriveTrain.arcadeDrive(0, 0.3); 
        }
        else if(tv == 1.0){ // Target on screen, then aim on it
            angleCorrect(table);
        }
    }

    public double distanceFromObject(NetworkTable table, double objectHeight, double cameraHeight, double cameraAngle){ //d = (h2-h1) / tan(a1+a2)
        NetworkTableEntry tyE = table.getEntry("ty");
        return((objectHeight - cameraHeight) / Math.tan(cameraAngle + tyE.getDouble(0))); // Use trig knowing the height of the object and angle to find distnace
    } 
    
    public void findCameraAngle(NetworkTable table, double objectHeight, double cameraHeight, double distance){ // a1 = arctan((h2 - h1)/d) - a2
        //One time function used to determine the angle at which the camera is mounted at
        //After the angle is found, it is put in the code permanently in Constants.java and never edited. (unless the limelight is moved physically on the robot)
        //The robot must be put a distance away from the target that is KNOWN and is EXACT
        NetworkTableEntry tyE = table.getEntry("ty");
        double cameraAngle = Math.atan((objectHeight - cameraHeight) / distance) - tyE.getDouble(0);
        System.out.println(cameraAngle); // Not sure if works, shuffleboard could be used
    }

    //Handles all vision firing and other stuff
    public void shoot(NetworkTable table, Target target){ //Sample code that the robot fired the projectile
        //Haven't tried running both getInDistance and angleCorrect at once, but I don't want our motors to explode so probably best to do this
        getInDistance(table, target); //calls on angleCorrect when it is in the correct spot
    }
    
    class Target{
        double m_targetHeight;
        double m_desiredDistance;
        double launchPower = 0; // placeholder
    
        public Target(double targetHeight, double desiredDistance){
            m_targetHeight = targetHeight;
            m_desiredDistance = desiredDistance;
        }
    }
}
