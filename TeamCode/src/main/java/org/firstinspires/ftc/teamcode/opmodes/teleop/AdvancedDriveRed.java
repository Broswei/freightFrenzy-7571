package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;

@TeleOp (group = "DriveTest")
public class AdvancedDriveRed extends Robot{

private boolean yButton2Toggle=false;

        boolean isSlow = false;
        //Changes behavior when intaking
        boolean isIntaking = false;
        int liftZero = 0;
        double gyroOffset = 0;

private ElapsedTime timer=new ElapsedTime();


    @Override
    public void init(){
        super.init();

        isAuto(false);
        }

    @Override
    public void start(){

        }

    @Override
    public void loop(){
        super.loop();

        //Sets if we are intaking or not
        isIntaking = gamepad2.right_trigger>.1;

        isSlow = gamepad1.left_trigger>.01 || gamepad1.right_trigger>.01;

        if(gamepad1.y){
            gyroOffset = dt.getGyroRotation(AngleUnit.RADIANS);
        }

        //Strafe drive, slows down when intaking
        if(gamepad1.left_trigger>.1){
        dt.manualControl(gamepad1,isSlow);
        }else{
        dt.fieldOrientedControl(gamepad1, isSlow,gyroOffset);
        }
        //Intake controls
        if(isIntaking){
            intake.setPower(-1);
        }else if(gamepad2.right_bumper){
            intake.setPower(1);
        }else{
            intake.setPower(0);
        }



        /***********************************\
         | Run lift positions using encoders |
         \***********************************/

            //lift level 1 position on b
            if(gamepad2.b){
                lift.setTargetPosition(400);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //lift level 2 position on x
            else if(gamepad2.x){
                lift.setTargetPosition(600);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //lift level 3 position on y
            else if(gamepad2.y) {
                lift.setTargetPosition(1100);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //Default to lowest position that doesn't hit the barrier for stability
            else{
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(200);
            }
        //Drop down ramp when intaking otherwise hold ramp up
        if(isIntaking){
            if(gamepad2.right_bumper){
                rampServo.setPosition(.9);
            }else {
                rampServo.setPosition(.8);
            }
        }else if(gamepad2.right_bumper){
            rampServo.setPosition(.8);
        }else{
            rampServo.setPosition(.9);
        }

        if(gamepad2.left_bumper){
            midServo.setPosition(.1);
        }else{
            midServo.setPosition(.75);
        }

        if(gamepad2.left_trigger >.01){
            leftServo.setPosition(0);
            rightServo.setPosition(.55);
        }else{
            leftServo.setPosition(.55);
            rightServo.setPosition(0);
        }

        if(gamepad2.dpad_down) {
            lift.setTargetPosition(340);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
        }
        if(gamepad2.dpad_up) {
            lift.setTargetPosition(1300);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
        }
        if(gamepad2.a){
            leftServo.setPosition(0.15);
            rightServo.setPosition(0.4);
        }
        spinner.setPower(-gamepad1.right_trigger);
        spinner2.setPower(-gamepad1.right_trigger);


        telemetry.update();
    }
}