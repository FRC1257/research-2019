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

    }

    public static void toggleScreenshot(NetworkTable Table){ // Takes 2 screenshots per second
        if(Table.getEntry("snapshot").getDouble(0) == 0){ // If the camera is not taking screenshots currently
            Table.getEntry("snapshot").setNumber(1);
        }
        else if(Table.getEntry("snapshot").getDouble(0) == 1){ // If the camera is taking screenshots currently
            Table.getEntry("snapshot").setNumber(0);
        }
    }

}