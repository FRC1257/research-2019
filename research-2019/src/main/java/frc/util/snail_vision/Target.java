package frc.util.snail_vision;

public class Target{
    double[] TARGET_HEIGHT;   
    double[] DESIRED_DISTANCE; // Array so that if a target has multiple levels of height
    
    double AREA_PERCENT_MEASUREMENTS;
    double[] AREA_TO_DISTANCE = {};

    public Target(double[] targetHeight, double[] desiredDistance, double areaPercentMeasurements, double[] areaToDistance){
        TARGET_HEIGHT = targetHeight;
        DESIRED_DISTANCE = desiredDistance;
        AREA_PERCENT_MEASUREMENTS = areaPercentMeasurements;
        AREA_TO_DISTANCE = areaToDistance;
    }

}