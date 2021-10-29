package org.firstinspires.ftc.teamcode.lib.hardware.base;


//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.hardware.manip.Intake;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.text.DecimalFormat;

import static org.firstinspires.ftc.teamcode.lib.hardware.base.DriveTrain.PIDx;
import static org.firstinspires.ftc.teamcode.lib.hardware.base.DriveTrain.PIDy;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.RobotStates;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.aTarget;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.aTolerance;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.auto;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.auxGp;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.currentXTicks;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.currentYTicks;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.mTolerance;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.mainGp;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.roboState;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.worldAngle_rad;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.worldXPosition;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.worldYPosition;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.wxRelative;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.wyRelative;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.xTarget;
import static org.firstinspires.ftc.teamcode.lib.util.GlobalVars.yTarget;

/*
import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Clamp;
import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Elevator;
 */
//import org.openftc.revextensions2.ExpansionHubEx;
//import org.openftc.revextensions2.ExpansionHubMotor;
//import org.openftc.revextensions2.RevBulkData;
//import org.openftc.revextensions2.RevExtensions2;

/**
 * main robot class
 * all opmodes will extend this class
 */
//@TeleOp
public class Robot extends OpMode{

  public static boolean isAuto = true;

  public DecimalFormat df = new DecimalFormat("###.###");

  private DcMotor[] motors;

  public DriveTrain dt = new DriveTrain();
  public Intake intake = new Intake();
  public DcMotorEx lift;
  public BNO055IMU gyro;
  public Servo pushServo;
  public Servo platServo;
  public Servo midServo;
  public Servo rampServo;

  public static ElapsedTime timer = new ElapsedTime();

  //public FtcDashboard dashboard = FtcDashboard.getInstance();
  //public TelemetryPacket packet = new TelemetryPacket();

  @Override
  public void init() {

    motors = new DcMotor[]{hardwareMap.dcMotor.get("fl"), hardwareMap.dcMotor.get("fr"), hardwareMap.dcMotor.get("bl"), hardwareMap.dcMotor.get("br")};
    
    //stores gamepads in global variables
    if(!isAuto){
      getGamepads(gamepad1, gamepad2);
      //dt.initGyro(hardwareMap.get(BNO055IMU.class, "imu"));
    }
    
    dt.initMotors(motors);
    //fm.init(hardwareMap.get(Servo.class, "fmLeft"), hardwareMap.get(Servo.class, "fmRight"));
    gyro = hardwareMap.get(BNO055IMU.class, "imu");
    intake.init(hardwareMap.get(DcMotor.class, "intake"));
    lift = hardwareMap.get(DcMotorEx.class, "lift");
    pushServo = hardwareMap.get(Servo.class, "pushServo");
    platServo = hardwareMap.get(Servo.class,"platServo");
    midServo = hardwareMap.get(Servo.class, "midServo");
    rampServo = hardwareMap.get(Servo.class, "rampServo");

  }

  @Override
  public void init_loop(){
    dt.initGyro(gyro);
    telemetry.update();

    //fm.setTarget(false);
    //fm.update();


    //depositor.setTarget(1);
    //loader.update();



      timer.reset();
  }

  @Override
  public void loop() {

    //gets sensor data
    //getRevBulkData();


    //if the robot is not finished, apply the motor powers to the motors
    if(roboState != RobotStates.FINISHED) {

    }

    if(isAuto) {
        dt.update();
    } else {
        dt.applyMovement();
    }



    telemetry.addLine("Lift: " + lift.getCurrentPosition());
    telemetry.addLine("Platform Servo: " + platServo.getPosition());
    telemetry.addLine("Push Servo: " + pushServo.getPosition());
    telemetry.addLine("Middle Servo: " + midServo.getPosition());
    telemetry.addLine("Ramp Servo: " + rampServo.getPosition());
    telemetry.addLine("Gyro: " + dt.getGyroRotation(AngleUnit.RADIANS));
    telemetry.update();

  }

  /**
   * stores gamepads in global variables
   * @param main main gamepad, used for driving the robot base
   * @param aux auxiliary gamepad, used for driving the scoring mechanisms
   */
  public void getGamepads(Gamepad main, Gamepad aux){

    mainGp = main;
    auxGp = aux;

  }

  /**
   * updates the robot state in relation to whether or not we are at our target position or not
   */
  private void updateAtTarget(){
    if(((worldXPosition >= xTarget -mTolerance) && (worldXPosition <= xTarget+mTolerance)) && ((worldYPosition >= yTarget -mTolerance) && (worldYPosition <= yTarget+mTolerance)) && ((Math.toDegrees(worldAngle_rad) >= aTarget - aTolerance) && (Math.toDegrees(worldAngle_rad) <= aTarget +aTolerance))){
      roboState = RobotStates.AT_TARGET;
      wxRelative = 0;
      wyRelative = 0;
    } else if(roboState == RobotStates.AT_TARGET){
      roboState = RobotStates.MOVING_TO_TARGET;
    }
  }
  
  /**
  * updates the robot state in relation to the error of the pid loops
  */
  private void updateAtTargetAlt(){

    if((PIDx.getError() < 2 && PIDy.getError() < 2) && roboState == RobotStates.MOVING_TO_TARGET){
      roboState = RobotStates.AT_TARGET;
      wxRelative = 0;
      wyRelative = 0;

    }

  }

  /**
   * updates our auto state in relation to our robot state
   */
  private void updateAutoState(){

    if(roboState == RobotStates.AT_TARGET){

      roboState = RobotStates.STOPPED;
      auto++;

    }
  }

  /**
   * denotes whether or not the opmode currently running is auto or not
   * @param isAuto true if this is auto, false if it is not auto
   */
  public void isAuto(boolean isAuto){
    this.isAuto = isAuto;
  }

  /**
   * Gets all the data from the expansion hub in one command to increase loop times
   */
//  public void getRevBulkData() {
////        boolean needToPollMaster = !AutoFeeder.canPollMasterAtLowerRate ||
////            currTimeMillis-lastUpdateMasterTime > 300;
////        if(needToPollMaster){
//    RevBulkData newDataMaster;
//    try{
//      newDataMaster = revMaster.getBulkInputData();
//      if(newDataMaster != null){
//        revExpansionMasterBulkData = newDataMaster;
//      }
//    }catch(Exception e){
//      //don't set anything if we get an exception
//    }
//
//
//    for(DcMotor dcMotor : motors) {
//      if (dcMotor == null) {
//        continue;
//      }
//      if (revExpansionMasterBulkData != null) {
//        revExpansionMasterBulkData.getMotorCurrentPosition(dcMotor);
//      }
//    }
//
//
//    currentXTicks = revExpansionMasterBulkData.getMotorCurrentPosition(dt.fl);
//    currentYTicks = revExpansionMasterBulkData.getMotorCurrentPosition(dt.fr);
//
//  }
}
