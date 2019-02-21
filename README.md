# Snail Vision

### What is Snail Vision?

Snail Vision is a library created as a calculation library for vision. It works with the limelight but could take inputs from any camera and still work.

### What is required to use Snail Vision?

  * Limelight or a Camera which processes the image and feeds the library data.
  * At MOST 5 mb of RAM on the RoboRIO. Expect approximately 2 mb to be used.
  * NavX vendordeps file (Required).
  * NavX (Optional - Certain features are embellished by a gyro).
  
# Documentation
When seeing code with brackets, the text inside the brackets is meant to represent something which is broad and general and has a different value for whatever case. For example the following style is used to show that an integer with some name was assigned some value.
```
int {integer} = {number}
```


# Table of Contents
  * [The SnailVision Object](#the-snailvision-object)
  * [Constants and Options](#constants-and-options)
    * [angleCorrect Constants](#anglecorrect-constants)
      * [ANGLE_CORRECT_P](#angle_correct_p)
      * [ANGLE_CORRECT_F](#angle_correct_f)
      * [ANGLE_CORRECT_MIN_ANGLE](#angle_correct_min_angle)
    * [getInDistance Constants](#getindistance-constants)
      * [GET_IN_DISTANCE_P](#get_in_distance_p)
      * [GET_IN_DISTANCE_ERROR](#get_in_distance_error)
      * [DISTANCE_ESTIMATION_METHOD](#distance_estimation_method)
      * [TARGETS](#targets)
    * [trigDistance Constants](#trigdistance-constants)
      * [CAMERA_HEIGHT](#camera_height)
      * [CAMERA_ANGLE](#camera_angle)
    * [Gyro Constants](#gyro-constants)
      * [USE_GYRO](#use_gyro)
      * [ROTATIONAL_AXIS](#rotational_axis)
  * [Retrievable Data](#retrievable-data)
      * [Vision Data](#vision-data)
        * [TargetX](#targetx)
        * [TargetY](#targety)
        * [TargetA](#targeta)
        * [TargetV](#targetv)
        * [TargetS](#targets)
        * [Latency](#latency)
        * [TargetShort](#targetshort)
        * [TargetLong](#targetlong)
        * [TargetHorizontal](#targethorizontal)
        * [TargetVertical](#targetvertical)
        * [currentPipeline](#currentpipeline)
      * [Gyro Data](#gyro-data)
        * [navx](#navx)
        * [resetAngle](#resetangle)
        * [currentAccelleration](#currentaccelleration)
        * [pastAccelleration](#pastaccelleration)
        * [printIterationTime](#printiterationtime)
        * [horizontalAngleFromTarget](#horizontalanglefromtarget)
        * [instantaneousJerk](#instantaneousjerk)
      * [Measurement Data](#measurement-data)
        * [Timer](#Timer)
        * [storedTargetAreaValues](#storedtargetareavalues)
        * [targetAreaValues](#targetareavalues)
        
  * [Functions](#functions)
    * [Vision Functions](#vision-functions)
      * [networkTableFunctionality](#networktablefunctionality)
      * [angleCorrect](#anglecorrect)
      * [getInDistance](#getindistance)
      * [areaDistance](#areadistance)
      * [trigDistance](#trigdistance)
      * [findTarget](#findtarget)
      * [trackTargetPosition](#tracktargetposition)
      * [changePipeline](#changepipeline)
    * [Data Collection Functions](#data-collection-functions)
      * [findCameraAngle](#findcameraangle)
      * [recordTargetArea](#recordtargetarea)
      * [clearTargetArea](#cleartargetarea)
      * [resetTargetArea](#resettargetarea)
      * [printTargetArea](#printtargetarea)
      * [printIterationTime](#printIterationTime)
    * [Limelight Functions](#limelight-functions)
      * [toggleLimelightScreenshot](#togglelimelightscreenshot)
      * [turnOffLimelight](#turnofflimelight)
      * [turnOnLimelight](#turnonlimelight)
      * [blinkLimelight](#blinklimelight)
      * [toggleLimelightMode](#togglelimelightmode)
    * [NavX Gyro Functions](#navx-gyro-functions)
      * [gyroFunctionality](#gyrofunctionality)
      * [getRotationalAngle](#getrotationalangle)
      * [resetRotationalAngle](#resetrotationalangle)
      * [getYawAngle](#getyawangle)
      * [getRollAngle](#getrollangle)
      * [getPitchAngle](#getpitchangle)
      * [getAccelleration](#getaccelleration)
      * [calculateJerk](#calculatejerk)
  * [Contact Information](#contact-information)

## The SnailVision Object



## Constants and Options
Constants and options contains values that could be changed to change options and such by using 

``` 
{snail-vision-object}.{variable} = {value};
``` 

## angleCorrect Constants

### ANGLE_CORRECT_P

### ANGLE_CORRECT_F

### ANGLE_CORRECT_MIN_ANGLE


## getInDistance Constants

### GET_IN_DISTANCE_P

### GET_IN_DISTANCE_ERROR

### DISTANCE_ESTIMATION_METHOD

### TARGETS


## trigDistance Constants

### CAMERA_HEIGHT

### CAMERA_ANGLE


## Gyro Constants

### USE_GYRO

### ROTATIONAL_AXIS



## Retrievable Data
Retrievable data contains values that are calculated by Snail Vision during operation and might be useful depending on the situation.


## Vision Data

All of the Vision Data is stored in ArrayLists. The follwoing variables hold the last 60 frames the camera has seen.

Vision data in ArrayLists could be retrieved by
```
{snail-vision-object}.{variable-name}.get({index-of-the-ArrayList})
```

Example: The robot needs to check whether it saw the target exactly 3 frames ago:
```
{snail-vision-object}.TargetV.get(2);
```

### TargetX
**Datatype:** Double ArrayList


TargetX represents the horizontal angle the target is from the center of the camera's vision (or crosshair) measured in degrees.

### TargetY
**Datatype:** Double ArrayList


TargeyY represents the vertical angle the target is from the center of the camera's vision (or crosshair) measured in degrees.

### TargetA
**Datatype:** Double ArrayList


TargetA represents the percent area of the screen the bounding box around the target on the screen takes up; the area of the screen taken up by the target.

### TargetV
**Datatype:** Boolean ArrayList


TargetV represents whether the target is on the screen or not.

### TargetS
**Datatype:** Double ArrayList


TargetS represents the rotation/skew of the target measured in degrees.

### Latency
**Datatype:** Short ArrayList


Latency is time time it takes for the feed from the camera to get to the RoboRIO.

### TargetShort
**Datatype:** Double ArrayList


TargetShort is the short side of the target measured in pixels.

### TargetLong
**Datatype:** Double ArrayList


TargetLong is the long side of the target measured in pixels.

### TargetHorizontal
**Datatype:** Double ArrayList


TargetHorizontal is the horizontal side of the target measured in pixels.

### TargetVertical
**Datatype:** Double ArrayList


TargetVertical is the vertical side of the target measured in pixels.

### currentPipeline
**Datatype:** Byte 


currentPipeline is the current pipeline settings of the camera. Byte to conserve memory.

## Gyro Data

### navx

### resetAngle

### currentAccelleration

### pastAccelleration

### printIterationTime

### horizontalAngleFromTarget

### instantaneousJerk


## Measurement Data

### Timer

### storedTargetAreaValues

### TargetAreaValues


## Functions
Functions describes all of the functions used by Snail Vision.


## Vision Functions

### networkTableFunctionality

### angleCorrect

### getInDistance

### areaDistance

### trigDistance

### findTarget

### trackTargetPosition

### changePipeline


## Data Collection Functions

### findCameraAngle

### recordTargetArea

### clearTargetArea

### resetTargetArea

### printTargetArea

### printIterationTime


## Limelight Functions

### toggleLimelightScreenshot

### turnOffLimelight

### turnOnLimelight

### blinkLimelight

### toggleLimelightMode

## NavX Gyro functions

### gyroFunctionality

### getRotationalAngle

### resetRotationalAngle

### getYawAngle

### getRollAngle

### getPitchAngle

### getAccelleration

### calculateJerk



## Contact Information
Snail Vision was created by Adam Zamlynny in 2019. Feel free to contact me at azamlynny@hotmail.com if you have any questions!

[Back to Top](#snail-vision)
