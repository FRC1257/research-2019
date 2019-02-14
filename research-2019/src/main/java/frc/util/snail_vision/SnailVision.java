package frc.util.snail_vision;

import edu.wpi.first.networktables.*;

import java.util.*;

public class SnailVision {
    static double ANGLE_CORRECT_P;
    static double ANGLE_CORRECT_F;
    static double ANGLE_CORRECT_MIN_ANGLE;

    static double GET_IN_DISTANCE_P;
    static double GET_IN_DISTANCE_ERROR;

    static double CAMERA_HEIGHT;
    static double CAMERA_ANGLE;

    static ArrayList<Double> TargetX; // Target's angle on the x-axis 
    static ArrayList<Double> TargetY; // Target's angle on the y-axis 
    static ArrayList<Double> TargetA; // Target's area on the screen
    static ArrayList<Double> TargetV; // Target's Visibility on the screen 1.0 = true 0.0 = false
    static ArrayList<Double> TargetS; // Target's skew/rotation on the screen
    static ArrayList<Double> Latency; // Latency of the camera in miliseconds
    static ArrayList<Double> TargetShort; // Sidelength of shortest side of the fitted bounding box (pixels)
    static ArrayList<Double> TargetLong; // Sidelength of longest side of the fitted bounding box (pixels)
    static ArrayList<Double> TargetHorizontal; // Horizontal length of the fitted bounding box
    static ArrayList<Double> TargetVertical; // Vertical length of the fitted bounding box
    static ArrayList<Double> currentPipeline; // Array because it might be used when switching pipeline

    static ArrayList<Double> Targets = new ArrayList<Double>();

    public SnailVision(){
        TargetX = new ArrayList<Double>(); // Target's angle on the x-axis 
        TargetY = new ArrayList<Double>(); // Target's angle on the y-axis 
        TargetA = new ArrayList<Double>(); // Target's area on the screen
        TargetV = new ArrayList<Double>(); // Target's visibility on the screen 1.0 = true 0.0 = false
        TargetS = new ArrayList<Double>(); // Target's skew/rotation on the screen
        Latency = new ArrayList<Double>(); // Latency of the camera in miliseconds
        TargetShort = new ArrayList<Double>(); // Sidelength of shortest side of the fitted bounding box (pixels)
        TargetLong = new ArrayList<Double>(); // Sidelength of longest side of the fitted bounding box (pixels)
        TargetHorizontal = new ArrayList<Double>(); // Horizontal length of the fitted bounding box
        TargetVertical = new ArrayList<Double>(); // Vertical length of the fitted bounding box
        currentPipeline = new ArrayList<Double>(); // Array because it might be used when switching pipeline
    }

    public static void networkTableFunctionality(NetworkTable Table){ // Works with limelight!
        // NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        
        // Creates objects which retrieve data from the limelight. The limelight MUST have these entries. Fill with 0 if they do not exist
        NetworkTableEntry txE = Table.getEntry("tx");
        NetworkTableEntry tyE = Table.getEntry("ty");
        NetworkTableEntry taE = Table.getEntry("ta");
        NetworkTableEntry tvE = Table.getEntry("tv");
        NetworkTableEntry tsE = Table.getEntry("ts");
        NetworkTableEntry tlE = Table.getEntry("tl");
        NetworkTableEntry tshortE = Table.getEntry("tshort");
        NetworkTableEntry tlongE = Table.getEntry("tlong");
        NetworkTableEntry thorE = Table.getEntry("thor");
        NetworkTableEntry tvertE = Table.getEntry("tvert");
        NetworkTableEntry tgetpipeE = Table.getEntry("getpipe");

        TargetX.add(0, txE.getDouble(0)); // Target's angle on the x-axis 
        TargetY.add(0, tyE.getDouble(0)); // Target's angle on the y-axis 
        TargetA.add(0, taE.getDouble(0)); // Target's area on the screen
        TargetV.add(0, tvE.getDouble(0)); // Target's visibility on the screen 1.0 = true 0.0 = false
        TargetS.add(0, tsE.getDouble(0)); // Target's skew/rotation on the screen
        Latency.add(0, tlE.getDouble(0)); // Latency of the camera in miliseconds
        TargetShort.add(0, tshortE.getDouble(0)); // Sidelength of shortest side of the fitted bounding box (pixels)
        TargetLong.add(0, tlongE.getDouble(0)); // Sidelength of longest side of the fitted bounding box (pixels)
        TargetHorizontal.add(0, thorE.getDouble(0)); // Horizontal length of the fitted bounding box
        TargetVertical.add(0, tvertE.getDouble(0)); // Vertical length of the fitted bounding box
        currentPipeline.add(0, tgetpipeE.getDouble(0));
            
        if(TargetX.size() > 60){ // Removes the last entry in the arraylist and shifts over the rest
            TargetX.remove(60); // Target's angle on the x-axis 
            TargetY.remove(60); // Target's angle on the y-axis 
            TargetA.remove(60); // Target's area on the screen
            TargetV.remove(60); // Target's visibility on the screen 1.0 = true 0.0 = false
            TargetS.remove(60); // Target's skew/rotation on the screen
            Latency.remove(60); // Latency of the camera in miliseconds
            TargetShort.remove(60); // Sidelength of shortest side of the fitted bounding box (pixels)
            TargetLong.remove(60); // Sidelength of longest side of the fitted bounding box (pixels)
            TargetHorizontal.remove(60); // Horizontal length of the fitted bounding box
            TargetVertical.remove(60); // Vertical length of the fitted bounding box
            currentPipeline.remove(60); // Array because it might be used when switching pipeline
        }
    }

    public double angleCorrect(){
        double tx = TargetX.get(0); // Gets the angle of how far away from the corsshair the object is

        double Kf = ANGLE_CORRECT_F;  // Minimum motor input to move robot in case P can't do it 
        double Kp = ANGLE_CORRECT_P; // for PID
        double heading_error = tx; 
        double steering_adjust = 0.0;

        if (tx > ANGLE_CORRECT_MIN_ANGLE){ // If the angle of the target is farther than n degrees do normal pid
            steering_adjust = Kp * heading_error;
        } 
        else if (tx < ANGLE_CORRECT_MIN_ANGLE) // If angle fo the target is less than n degrees, the motor will not be able to move the robot due to friction, so Kf is added to give it the minimum speed to move
        {
            steering_adjust = Kp * heading_error + Kf;
        }

        return(-steering_adjust); // return motor output
    }

    public double getInDistance(Target Target){ 

        return(0); // temp
    }
    
    public double areaDistance(Target Target){

        return(0); // temp
    }

    public double trigDistance(Target Target){

        return(0); // temp
    }

    public double findCameraAngle(double currentDistance, Target Target){ // Give the distance from a known target in inches
        // Camera Angle = arctan((Target Height - Camera Height) / Distance from Target) - Angle of Target Above Camera's Crosshair
        double cameraAngle = Math.atan(Math.toRadians( ( (Target.TARGET_HEIGHT[0] - CAMERA_HEIGHT) / currentDistance) - TargetY.get(0)));
        System.out.println("Camera Angle" + cameraAngle);
        return(cameraAngle);
    }

    public double findTarget(){

        return(0); // temp
    }

    public static void changePipeline(NetworkTable Table, int pipeline){
        if(pipeline < 0 || pipeline > 9){
            pipeline = 0; // In case that someone tries to switch to an impossible pipeline then set it to default.
        }
        Table.getEntry("pipeline").setNumber(pipeline);
    }

    // Limelight ONLY functions

    public static void toggleLimelightScreenshot(NetworkTable Table){ // Takes 2 screenshots per second
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

    public static void toggleLimelightMode(NetworkTable Table){ // 0 = vision processing 1 = drive camera
        if(Table.getEntry("camMode").getDouble(0) == 0){
            Table.getEntry("camMode").setNumber(1);
        }
        else if(Table.getEntry("camMode").getDouble(0) == 1){
            Table.getEntry("camMode").setNumber(0);
        }
    }

}