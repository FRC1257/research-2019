package frc.robot.vision;

import frc.robot.Robot;
import frc.robot.constants.*;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision{

     public static double angleCorrect(NetworkTable table){

      NetworkTableEntry txE = table.getEntry("tx");
      double tx = txE.getDouble(0); //Gets the angle of how far away from the corsshair the object is

      double min_command = 0.05; //Minimum motor input to move robot in case P can't do it 
      double Kp = -0.03; // for PID
      double heading_error = tx; 
      double steering_adjust = 0.0;

      if (tx > 2.0){ // If tx > 1.0, do normal pid
        steering_adjust = Kp * heading_error - min_command;
      } 
      else if (tx < 2.0) // If tx 1.0, the motor will not be able to move the robot due to friction, so min_command is added to give it the minimum speed to move
      {
        steering_adjust = Kp * heading_error + min_command;
      }

     return(-steering_adjust);
    }

    public static double getInDistance(NetworkTable table){ 
        double KpDistance = 0.1;
        double desiredDistance = Constants.target1_distance; // 60 inches - uses trig so it does not matter which unit as long as unit is uniform
        double currentDistance = distanceFromObject(table); //cameraHeight and cameraAngle are constants
        double distanceError = desiredDistance - currentDistance;
        double driving_adjust = 0;
        if(distanceError > 10){ // 10 inches of error space for PID
            driving_adjust = KpDistance * distanceError;
            
        }
        return(driving_adjust);
    }

    public static void findObject(NetworkTable table, DifferentialDrive DriveTrain){ // Spins until the robot finds the target
        NetworkTableEntry tvE = table.getEntry("tv");
        double tv = tvE.getDouble(0);
        if(tv == 0.0){ // No target on screen, then spin
            DriveTrain.arcadeDrive(0, 1); 
        }
        else if(tv == 1.0){ // Target on screen, then aim on it
            angleCorrect(table);
        }
    }

    //Not used in this season because the targets are close to the ground
    public static double distanceFromObject(NetworkTable table){ //d = (h2-h1) / tan(a1+a2)
        NetworkTableEntry tyE = table.getEntry("ty");
        return((Constants.target1_height - Constants.cameraHeight) / Math.toDegrees(Math.tan(Constants.cameraAngle + tyE.getDouble(0)))); // Use trig knowing the height of the object and angle to find distnace
    } 
    
    // public static double tableDistanceFromObject(NetworkTable table){
    //     NetworkTableEntry taE = table.getEntry("ta");
    //     double ta = taE.getDouble;
        
    // }

    public static void findCameraAngle(NetworkTable table, double distance){ // a1 = arctan((h2 - h1)/d) - a2
        //One time function used to determine the angle at which the camera is mounted at
        //After the angle is found, it is put in the code permanently in Constants.java and never edited. (unless the limelight is moved physically on the robot)
        //The robot must be put a distance away from the target that is KNOWN and is EXACT
        NetworkTableEntry tyE = table.getEntry("ty");
        double cameraAngle = Math.toDegrees(Math.atan((Constants.target1_height - Constants.cameraHeight)) / distance) - tyE.getDouble(0);
        System.out.println(cameraAngle); // Not sure if works, shuffleboard could be used
        SmartDashboard.putNumber("Elevator Height ", cameraAngle);
    }

    //Handles all vision firing and other stuff
    public static void shoot(NetworkTable table, DifferentialDrive DriveTrain){ //Sample code that the robot fired the projectile
        DriveTrain.arcadeDrive(-getInDistance(table), angleCorrect(table)); // getInDistance and angleCorrect return values to correct the robot.
    }

}