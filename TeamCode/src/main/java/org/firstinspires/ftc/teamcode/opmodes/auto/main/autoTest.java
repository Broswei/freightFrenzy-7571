package org.firstinspires.ftc.teamcode.opmodes.auto.main;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

//vision libraries
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;

import org.firstinspires.ftc.teamcode.lib.hardware.base.DriveTrain;
import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;
import org.firstinspires.ftc.teamcode.lib.hardware.manip.Intake;


@Autonomous(group="Main")
public class autoTest extends LinearOpMode {

    /* Declare OpMode members. */
    private DriveTrain dt = new DriveTrain();
    private ElapsedTime     runtime = new ElapsedTime();
    private HardwareDevice webcam_1;
    private DcMotorEx[] motors;
    private BNO055IMU gyro;
    private int degreeOffset = 2;
    public Intake intake = new Intake();
    public DcMotorEx lift;
    public DcMotor spinner;
    public Servo leftServo;
    public Servo rightServo;
    public Servo midServo;
    public Servo rampServo;
    public TouchSensor magLim;
    public RevColorSensorV3 color;
    private int level = 1;
    public RevColorSensorV3 color2;

    public RevColorSensorV3 distR;
    public RevColorSensorV3 distL;


    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_DM.tflite";
    private static final String[] LABELS = {
            "Duck",
            "Marker"
    };

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        motors = new DcMotorEx[]{hardwareMap.get(DcMotorEx.class, "fl"), hardwareMap.get(DcMotorEx.class, "fr"), hardwareMap.get(DcMotorEx.class, "bl"), hardwareMap.get(DcMotorEx.class, "br")};
        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        intake.init(hardwareMap.get(DcMotor.class, "intake"));
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        spinner = hardwareMap.get(DcMotor.class, "spinner");
        leftServo = hardwareMap.get(Servo.class, "pushServo");
        rightServo = hardwareMap.get(Servo.class,"platServo");
        midServo = hardwareMap.get(Servo.class, "midServo");
        rampServo = hardwareMap.get(Servo.class, "rampServo");
        magLim = hardwareMap.get(TouchSensor.class, "magLim");
        color = hardwareMap.get(RevColorSensorV3.class,"color");
        color2 = hardwareMap.get(RevColorSensorV3.class,"color2");

        //distR = hardwareMap.get(RevColorSensorV3.class, "distanceR");
        //distL = hardwareMap.get(RevColorSensorV3.class, "distanceL");


        dt.initMotors(motors);
        dt.initGyro(gyro);
        waitForStart();

        dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dt.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dt.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dt.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Auto Commands
        while(opModeIsActive()){
            telemetry.addData("fr: ", dt.fr.getCurrentPosition());
            telemetry.addData("fl: ", dt.fl.getCurrentPosition());
            telemetry.addData("br: ", dt.br.getCurrentPosition());
            telemetry.addData("bl: ", dt.bl.getCurrentPosition());
            telemetry.update();
        }


    }

    public void strafeUntilMarker(double distanceIn, int velocity, boolean isRunning){
        int ticks = (int)(-distanceIn/(Math.PI*4)*515*1.1);
        dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt.setDrivetrainPositions((int)ticks, (int)-ticks,(int)-ticks, (int)ticks);
        dt.setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
        dt.setDrivetrainVelocity(velocity);

        ElapsedTime runtime = new ElapsedTime();
        boolean run = true;
        runtime.reset();
        while(!seesMarker()&&dt.fr.isBusy() && isRunning){
            telemetry.addData("Sees Marker: ", seesMarker());
            telemetry.addData("Distance: ", color.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        if(seesMarker()){
            dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    //Returns true when it sees the marker
    public boolean seesMarker(){
        int benchmarkDist = 2;

        return color.getDistance(DistanceUnit.INCH)<benchmarkDist;
    }

    public boolean seesMarker2(){
        int benchmarkDist = 2;

        return color2.getDistance(DistanceUnit.INCH)<benchmarkDist;
    }

    //Turn by degrees
    public void turnDegrees(double turnDegrees,int velocity){
        dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        double offset;
        offset = dt.getGyroRotation(AngleUnit.DEGREES);
        int predictedTicks = (int)(turnDegrees*9.34579439252+2000);
        double correctedDegrees;


        correctedDegrees = offset + turnDegrees;
        if(correctedDegrees>180){
            correctedDegrees-=360;
        }
        if(correctedDegrees>0){
            dt.setDrivetrainPositions(-predictedTicks,predictedTicks,-predictedTicks,predictedTicks);
            dt.setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
            dt.setDrivetrainVelocity(velocity);
            while(correctedDegrees-degreeOffset>dt.getGyroRotation(AngleUnit.DEGREES)&&opModeIsActive()){
                telemetry.addLine("Running left");
                telemetry.addData("gyro Target: ", correctedDegrees);
                telemetry.addData("gyro: ", dt.getGyroRotation(AngleUnit.DEGREES));
                telemetry.addData("Offset: ", offset);
                telemetry.update();
            }
        }else if(correctedDegrees<0){
            dt.setDrivetrainPositions(predictedTicks,-predictedTicks,predictedTicks,-predictedTicks);
            dt.setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
            dt.setDrivetrainVelocity(velocity);
            while(correctedDegrees+degreeOffset<dt.getGyroRotation(AngleUnit.DEGREES)&&opModeIsActive()){
                telemetry.addLine("Running right");
                telemetry.addData("gyro: ", dt.getGyroRotation(AngleUnit.DEGREES));
                telemetry.addData("gyro Target: ", correctedDegrees);
                telemetry.addData("Offset: ", offset);
                telemetry.update();
            }

        }
        dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    //Lift commands
    public void liftToLevel(int level){
        if(level == 1){
            lift.setTargetPosition(300);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
            dt.driveDistance(1.5,200,opModeIsActive());
        }
        //lift level 2 position on x
        else if(level == 2){
            lift.setTargetPosition(600);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
            dt.driveDistance(-1.5,200,opModeIsActive());
        }
        //lift level 3 position on y
        else if(level ==3) {
            lift.setTargetPosition(1000);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
            dt.driveDistance(-2,200,opModeIsActive());
        }
        //Default to lowest position
        else if(level == 0) {
            lift.setTargetPosition(5);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(200);
        }

        while(lift.isBusy()&&opModeIsActive()){
            telemetry.addData("Running lift to level ", level);
            telemetry.update();
        }
    }

    //Deposit command
    public void deposit(){
        leftServo.setPosition(0);
        rightServo.setPosition(.55);
        runtime.reset();
        while(runtime.milliseconds()<2000&&opModeIsActive()){
            telemetry.addLine("Depositing");
            telemetry.update();
        }
        leftServo.setPosition(.55);
        rightServo.setPosition(0);
    }

    public void correctPos(){

        lift.setTargetPosition(110);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setVelocity(600);

        while(lift.isBusy()){}

        leftServo.setPosition(0.15);
        rightServo.setPosition(0.4);

        //drive forward until one sees
        while(!(rightSees()||leftSees())){
            dt.driveDistance(-.5, 200, opModeIsActive());

            telemetry.addData("left: ",  leftSees());
            telemetry.addData("right: ", rightSees());
            telemetry.addData("left dist: ", distL.getDistance(DistanceUnit.INCH));
            telemetry.addData("right dist: ", distR.getDistance(DistanceUnit.INCH));
            telemetry.update();

        }


        //If both see, it is straight and we continue
        if(rightSees()&&leftSees()){
            resetLift();
            return;
        }

        //Strafe left until left sees if only right sees
        else if(rightSees()){

            while(!leftSees()){
                dt.strafeDistance(-0.5,200,opModeIsActive());
                telemetry.addData("left: ",  leftSees());
                telemetry.addData("right: ", rightSees());
                telemetry.addData("left dist: ", distL.getDistance(DistanceUnit.INCH));
                telemetry.addData("right dist: ", distR.getDistance(DistanceUnit.INCH));
                telemetry.update();
            }

        }

        //Strafe right until right sees if only left sees
        else if(leftSees()){
            while(!rightSees()){
                dt.strafeDistance(0.5,200,opModeIsActive());
                telemetry.addData("left: ",  leftSees());
                telemetry.addData("right: ", rightSees());
                telemetry.addData("left dist: ", distL.getDistance(DistanceUnit.INCH));
                telemetry.addData("right dist: ", distR.getDistance(DistanceUnit.INCH));
                telemetry.update();
            }

        }

        if(rightSees()&&leftSees()){
            resetLift();
            return;
        }

    }

    public boolean rightSees(){
        return distR.getDistance(DistanceUnit.INCH) < 2.4;
    }

    public boolean leftSees(){
        return distL.getDistance(DistanceUnit.INCH) < 1.3;
    }

    public void resetLift(){
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setVelocity(300);

        leftServo.setPosition(.55);
        rightServo.setPosition(0);
    }

    public void driveToHub(){
        lift.setTargetPosition(50);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setVelocity(600);
        dt.setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int ticksForward = 0;
        while(!rightSees()){
            ticksForward += 10;
            dt.setDrivetrainPositions(ticksForward);
            dt.setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
            dt.setDrivetrainVelocity(150);

            telemetry.addData("right: ", rightSees());
            telemetry.addData("right dist: ", distR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }

    }
}