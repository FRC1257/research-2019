package frc.util.snail_vision;

import edu.wpi.first.networktables.*;

import java.util.*;

public class SnailVision {
    double ANGLE_CORRECT_P;
    double ANGLE_CORRECT_F;
    double ANGLE_CORRECT_MIN_ANGLE;

    double GET_IN_DISTANCE_P;
    double GET_IN_DISTANCE_ERROR;

    double CAMERA_HEIGHT;
    double CAMERA_ANGLE;

    ArrayList<Double> Targets = new ArrayList<Double>();

    public SnailVision(){

    }

    public static void visionFunctionality(NetworkTable Table){
        
    }

    public static double angleCorrect(NetworkTable Table){
        

        return(0); // temp
    }

    public static double getInDistance(NetworkTable Table, Target Target){

        return(0); // temp
    }
    
    public static double areaDistance(NetworkTable Table, Target Target){

        return(0); // temp
    }

    public static double trigDistance(NetworkTable Table, Target Target){

        return(0); // temp
    }

    public static double findTarget(NetworkTable Table){

        return(0); // temp
    }

    public static void changePipeline(NetworkTable Table, int pipeline){
        if(pipeline < 0 || pipeline > 9){
            pipeline = 0; // In case that someone tries to switch to an impossible pipeline then set it to default.
        }
        Table.getEntry("pipeline").setNumber(pipeline);
    }

    public static void toggleScreenshot(NetworkTable Table){ // Takes 2 screenshots per second
        if(Table.getEntry("snapshot").getDouble(0) == 0){ // If the camera is not taking screenshots currently
            Table.getEntry("snapshot").setNumber(1);
        }
        else if(Table.getEntry("snapshot").getDouble(0) == 1){ // If the camera is taking screenshots currently
            Table.getEntry("snapshot").setNumber(0);
        }
    }

    public static void turnOffLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 1){
            Table.getEntry("ledMode").setNumber(1);
        }
    }

    public static void turnOnLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 3){
            Table.getEntry("ledMode").setNumber(3);
        }
    }

    public static void blinkLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 2){
            Table.getEntry("ledMode").setNumber(2);
        }
    }

    public static void toggleCameraMode(NetworkTable Table){ // 0 = vision processing 1 = drive camera
        if(Table.getEntry("camMode").getDouble(0) == 0){
            Table.getEntry("camMode").setNumber(1);
        }
        else if(Table.getEntry("camMode").getDouble(0) == 1){
            Table.getEntry("camMode").setNumber(0);
        }
    }

}