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

import edu.wpi.first.wpilibj.I2C.Port;

public class Robot extends TimedRobot {

    SnailVision vision;
    public static final double[] AREA_TO_DISTANCE_ROCKET;
    public static final double[] AREA_TO_DISTANCE_SHIP;

    @Override
    public void robotInit () {

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
    }

    @Override
    public void teleopInit () {

    }

    @Override
    public void teleopPeriodic () {
        // NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    }
 
}