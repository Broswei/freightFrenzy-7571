package org.firstinspires.ftc.teamcode.lib.hardware.base;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

/*import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Clamp;
import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Depositor;
import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Elevator;
import org.firstinspires.ftc.teamcode.lib.hardware.skystone.FoundationMover;*/
//import org.firstinspires.ftc.teamcode.lib.hardware.skystone.Intake;

/**
 * main robot class
 * all opmodes will extend this class
 */
//@TeleOp
public class RobotGyro extends OpMode{

    public static boolean isAuto = true;

    public DecimalFormat df = new DecimalFormat("###.###");

    //private RevBulkData revExpansionMasterBulkData;

    //private ExpansionHubEx revMaster;
    // used in future if you need bulk reads from the other hub
    //private ExpansionHubEx revSlave;

    private DcMotorEx[] motors;

    public DriveTrain dt = new DriveTrain();
    //public Intake intake = new Intake();
    /*public FoundationMover fm = new FoundationMover();
    public Elevator elevator = new Elevator();
    public Depositor depositor = new Depositor();
    public Clamp clamp = new Clamp();*/

    public static ElapsedTime timer = new ElapsedTime();

    public static Telemetry telemetry;

    //public FtcDashboard dashboard = FtcDashboard.getInstance();
    //public TelemetryPacket packet = new TelemetryPacket();

    @Override
    public void init() {

        //RevExtensions2.init();

        //revMaster = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 6");
        //revSlave = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 9");

        motors = new DcMotorEx[]{hardwareMap.get(DcMotorEx.class, "fl"), hardwareMap.get(DcMotorEx.class, "fr"), hardwareMap.get(DcMotorEx.class, "bl"), hardwareMap.get(DcMotorEx.class, "br")};
        //stores gamepads in global variables
        if(!isAuto){
            getGamepads(gamepad1, gamepad2);
            //dt.initGyro(hardwareMap.get(BNO055IMU.class, "imu"));
        }

        dt.initMotors(motors);
        dt.initGyro(hardwareMap.get(BNO055IMU.class, "imu"));
        //fm.init(hardwareMap.get(Servo.class, "fmLeft"), hardwareMap.get(Servo.class, "fmRight"));
        //intake.init(hardwareMap.get(DcMotor.class, "intake"), hardwareMap.get(Servo.class, "hitter"), hardwareMap.get(ColorSensor.class, "intakeSensor"));
        //elevator.init(hardwareMap.get(DcMotor.class, "elevator"));
        //depositor.init(hardwareMap.get(Servo.class, "depositor"));
        //clamp.init(hardwareMap.get(Servo.class, "plate"), hardwareMap.get(Servo.class, "nub"));

    }

    @Override
    public void init_loop(){



        telemetry.addLine("wx: " + worldXPosition);
        telemetry.addLine("wy: " + worldYPosition);
        telemetry.update();

        //fm.setTarget(false);
        //fm.update();


        //depositor.setTarget(1);
        //depositor.update();



        timer.reset();
    }

    @Override
    public void loop() {

        //gets sensor data
        //getRevBulkData();

        //calculate our x and y coordinates


        //if the robot is not finished, apply the motor powers to the motors
        if(roboState != RobotStates.FINISHED) {

        }

        if(isAuto) {
            dt.update();
        } else {
            dt.applyMovement();
        }
        //intake.update();
        /*fm.update();
        elevator.update();
        depositor.update();
        clamp.update();
*/
        //fetch our rotation in radians from the imu
        //worldAngle_rad = Double.parseDouble(df.format(AngleWrap(dt.getGyroRotation(AngleUnit.RADIANS))));



        //update our auto states
        //updateAutoState(); //this is currently done inside the opmode instance

        //update our robot states
        //updateAtTargetAlt();

        //telemetry.addLine("positions set!");


        telemetry.addLine("wx: " + worldXPosition);
        telemetry.addLine("wy: " + worldYPosition);
    /*
    //telemetry.addLine("wa: " + Math.toDegrees(worldAngle_rad));
    telemetry.addLine("");
   // telemetry.addLine("r: " + dt.fr.getCurrentPosition());
   // telemetry.addLine("a: " + dt.bl.getCurrentPosition());
    //telemetry.addLine("");
    //telemetry.addLine("auto state: " + auto);
      telemetry.addLine("");
      telemetry.addLine("auto color: " + autoType);
    telemetry.addLine("");
    telemetry.addLine("robot state: " + roboState);
    telemetry.addLine("");
    telemetry.addLine("auto state: " + autoStateLZ);
    telemetry.addLine("");
    //telemetry.addLine("strafe const: " + strafeConstant);

    //telemetry.addLine("targetX: " + xTarget);
    //telemetry.addLine("targetY: " + yTarget);

    telemetry.addLine("");
    telemetry.addLine("deposit target: " + depositor.getTarget());
    //telemetry.addLine("");
    //telemetry.addLine("elevator target: " + elevator.getTarget());


*/
        telemetry.update();



   /* packet.put("wx", worldXPosition);
    packet.put("wy", worldYPosition);
    packet.put("wa", Math.toDegrees(worldAngle_rad));
    packet.put("auto", auto);
    packet.put("autostate", autoState);
    packet.put("robot state", roboState);*/

        //dashboard.sendTelemetryPacket(packet);

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
//    public void getRevBulkData() {
////        boolean needToPollMaster = !AutoFeeder.canPollMasterAtLowerRate ||
////            currTimeMillis-lastUpdateMasterTime > 300;
////        if(needToPollMaster){
//        RevBulkData newDataMaster;
//        try{
//            newDataMaster = revMaster.getBulkInputData();
//            if(newDataMaster != null){
//                revExpansionMasterBulkData = newDataMaster;
//            }
//        }catch(Exception e){
//            //don't set anything if we get an exception
//        }
//
///*
//    for(DcMotor dcMotor : motors) {
//      if (dcMotor == null) {
//        continue;
//      }
//      if (revExpansionMasterBulkData != null) {
//        revExpansionMasterBulkData.getMotorCurrentPosition(dcMotor);
//      }
//    }
//    */
//
//        currentXTicks = revExpansionMasterBulkData.getMotorCurrentPosition(dt.fl);
//        currentYTicks = revExpansionMasterBulkData.getMotorCurrentPosition(dt.fr);
//
//    }
}
