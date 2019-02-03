package frc.robot.util;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.constants.*;


/**
 * <h1>Gyro</h1>
 * The Gyro gets us our yaw angle, in degrees. Our "0" point is the yaw at the start of the match.
 * @author Allen Du and Matt Hui
 * @since 2019-01-23
 */
public class Gyro {
    private AHRS navx;
    private static Gyro instance = null;
    private String angle = "Yaw angle: ";

    /**
     * Constructs a Gyro object.
     */
    private Gyro() {
        navx = new AHRS(Port.kMXP);
    }

    /**
     * Singleton.
     * @return A Gyro object.
     */
    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }

    /**
     * Sets the current yaw angle to "0".
     */
    public void zeroAngle() {
        navx.zeroYaw();
    }

    /**
     * Gets the current yaw angle.
     * @return The angle in degrees.
     */
    public double getAngle() {
        return navx.getYaw();
    }

    /**
     * Resets the Gyro.
     */
    public void resetAngle() {
        navx.reset();
    }


    // Put in key & default value
    public void displayValues() {
        SmartDashboard.putNumber(angle, navx.getYaw());
        SmartDashboard.putNumber("Setpoint: ", Constants.setpoint);
        SmartDashboard.putNumber("Turn P: ", Constants.TURN_PIDF[0]);
        SmartDashboard.putNumber("Turn I: ", Constants.TURN_PIDF[1]);
        SmartDashboard.putNumber("Turn D: ", Constants.TURN_PIDF[2]);
        SmartDashboard.putNumber("Turn F: ", Constants.TURN_PIDF[3]);
    
    }

    // Update constants from Smart Dashboard
    public void updateValues() {
        if(Constants.setpoint != SmartDashboard.getNumber("Setpoint: ", Constants.setpoint)) {
            Constants.setpoint = SmartDashboard.getNumber("Setpoint: ", Constants.setpoint);
        }
        if(Constants.TURN_PIDF[0] != SmartDashboard.getNumber("Turn P", Constants.TURN_PIDF[0])) {
            Constants.TURN_PIDF[0] = SmartDashboard.getNumber("Turn P", Constants.TURN_PIDF[0]);
        }
    
        if(Constants.TURN_PIDF[1] != SmartDashboard.getNumber("Turn I", Constants.TURN_PIDF[1])) {
            Constants.TURN_PIDF[1] = SmartDashboard.getNumber("Turn I", Constants.TURN_PIDF[1]);   
        }

        if(Constants.TURN_PIDF[2] != SmartDashboard.getNumber("Turn D", Constants.TURN_PIDF[2])) {
            Constants.TURN_PIDF[2] = SmartDashboard.getNumber("Turn D", Constants.TURN_PIDF[2]);   
        }

        if(Constants.TURN_PIDF[3] != SmartDashboard.getNumber("Turn F", Constants.TURN_PIDF[3])) {
            Constants.TURN_PIDF[3] = SmartDashboard.getNumber("Turn F", Constants.TURN_PIDF[3]);   
        }
}
