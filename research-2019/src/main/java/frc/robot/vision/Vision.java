package frc.robot.vision;

import frc.robot.constants.*;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision{

    public static double angleCorrect(NetworkTable table){
        NetworkTableEntry tvE = table.getEntry("tv");
        NetworkTableEntry txE = table.getEntry("tx"); // Angle of the target away from the target -26 to 26 degrees
        double tx = txE.getDouble(0); //Gets the angle of how far away from the crosshair the object is
        double tv = tvE.getDouble(0);
        double Kp;
        double heading_error;
        double steering_adjust = 0;
        if(tv == 1){ //If the target is onscreen
            double min_command = 0.10; //Minimum motor input to move robot in case P can't do it 
            Kp = -0.03; // for PID (pcontrol)
            heading_error = tx; // How far from target
            steering_adjust = 0.0;

            if (tx > 2.0){ // If tx > 1.0, do normal pid
                steering_adjust = Kp * heading_error;
            } 
            else if (tx < 2.0) // If tx 1.0, the motor will not be able to move the robot due to friction, so min_command is added to give it the minimum speed to move
            {
                steering_adjust = Kp * heading_error + min_command;
            }
        }
        return(-steering_adjust);
    }

    public static double getInDistance(NetworkTable table){ 
        double KpDistance = 0.03; // For p control
        double currentDistance = tableDistanceFromObject(table); //cameraHeight and cameraAngle are constants
        double distanceError = currentDistance - Constants.desiredDistance;
        double driving_adjust = 0;
        if(distanceError > 3){ // 3 inches of error space for PID
            driving_adjust = KpDistance * distanceError;
        }
        return(driving_adjust);
    }

    public static double tableDistanceFromObject(NetworkTable table){
        NetworkTableEntry taE = table.getEntry("ta");
        NetworkTableEntry tvE = table.getEntry("tv");
        double ta = taE.getDouble(0);
        double tv1 = tvE.getDouble(0);
        double tv2 = tvE.getDouble(1); //So that if the camera misses the target in one frame it does not stop driving and then continue rapidly
        double tv3 = tvE.getDouble(2);
        double minDifference = 10000;
        double minIndex = Constants.measurementAmount - 1;
        if(tv1 == 1.0 || tv2 == 1.0 || tv3 == 1.0){ // If the target is on screen
            for(int i = 0; i < Constants.measurementAmount - 1; i++){
                if(Constants.distanceToPercent[i] - ta < minDifference && Constants.distanceToPercent[i] - ta > 0){
                    minDifference = Constants.distanceToPercent[i] - ta;
                    minIndex = i;
                }
            }
            return(minIndex);
        }
        else{
            return(0); // If there is no target on the screen then do not drive 
        }
    }

}