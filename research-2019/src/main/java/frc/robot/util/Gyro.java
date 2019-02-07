package frc.robot.util;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.I2C.Port;




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
    public void resetAngle() {
        navx.zeroYaw();
    }

    /**
     * Gets the current yaw angle.
     * @return The angle in degrees.
     */
    public double getAngle() {
        return navx.getYaw();
    }
}
