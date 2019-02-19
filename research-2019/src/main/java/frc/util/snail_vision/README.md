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
  * [Changeable Variables and Options](#changeable-variables-and-options)
  * [Retrievable Data](#retrievable-data)
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

## Changeable Variables and Options
Changeable variables and options contains values that could be changed to change options and such by using 

``` 
{snail-vision-object}.{variable} = {value};
``` 

## Retrievable Data
Retrievable data contains values that are calculated by Snail Vision during operation and might be useful depending on the situation.

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
