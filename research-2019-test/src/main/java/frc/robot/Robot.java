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
        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5){ //If b button pressed and a target on screen
          angleCorrect(table);
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

    public void findObject(NetworkTable table){ // Spins until it finds the target
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
}

