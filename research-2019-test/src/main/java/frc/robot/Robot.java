/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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
    void RobotInit () {
        FrontRight = new WPI_TalonSRX(2);
        FrontLeft = new WPI_TalonSRX(3);
        BackRight = new WPI_TalonSRX(4);
        BackLeft = new WPI_TalonSRX(5);

        Right = new SpeedControllerGroup(FrontRight, BackRight);
        Left = new SpeedControllerGroup(FrontLeft, BackLeft);

        DriveTrain = new DifferentrialDrive(Left, Right);

        Controller = new XboxController(1);

    }

    @Override
    void teleopInit () {

    }

    @Override
    void teleopPeriodic () {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); 
        NetworkTableEntry tv = table.getEntry("tv");

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

        // Limelight vision code
        if(Controller.getBButton() && tv.getDouble(0) == 1){ //If b button pressed and a target on screen
          visionCorrect();
        }
    }
  
  void visionCorrect(){

      NetworkTableEntry tx = table.getEntry("tx");
      float tx = tx.getDouble(0);

      float min_command = 0.05; //Minimum motor input to move robot in case P can't do it 
      float Kp = -0.03; // for PI
      float heading_error = tx;
      float steering_adjust = 0.0;
      if (tx > 1.0){
        steering_adjust = Kp * heading_error - min_command;
      } 
      else if (tx < 1.0)
      {
        steering_adjust = Kp * heading_error + min_command;
      }

      DriveTrain.arcadeDrive(0, steering_adjust);
    }
  }
    
}

