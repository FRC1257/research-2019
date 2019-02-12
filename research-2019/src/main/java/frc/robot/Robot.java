/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.util.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import java.util.ArrayList;
import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.I2C.Port;

public class Robot extends TimedRobot {

    @Override
    public void robotInit () {

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