package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;

@TeleOp (group = "DriveTest")
public class CoastalCloonup extends Robot{

private boolean yButton2Toggle=false;

        boolean isSlow = false;
        boolean isFast = false;
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
        isIntaking = gamepad1.right_trigger>.1;

        isSlow = gamepad1.left_trigger>.01;

        if(gamepad1.y){
            gyroOffset = dt.getGyroRotation(AngleUnit.RADIANS);
        }
        if(gamepad1.a){
            isFast = true;
        }else{
            isFast = false;
        }

        //Strafe drive, slows down when intaking
        if(gamepad1.left_trigger>.1){
        dt.manualControl(gamepad1,isSlow);
        }else{
        dt.fieldOrientedControl(gamepad1, isSlow, isFast, gyroOffset);
        }
        //Intake controls
        if(isIntaking){
            if (gamepad1.b){
                intake.setPower(-1);
            }
            else {
                intake.setPower(-0.8);
            }
        }else if(gamepad1.right_bumper){
            intake.setPower(0.8);
        }else{
            intake.setPower(0);
        }


        //Drop down ramp when intaking otherwise hold ramp up
        if(isIntaking){
            if(gamepad1.right_bumper){
                rampServo.setPosition(.9);
            }else {
                rampServo.setPosition(.8);
            }
        }else if(gamepad1.right_bumper){
            rampServo.setPosition(.8);
        }else{
            rampServo.setPosition(.9);
        }

        if(gamepad1.left_bumper){
            midServo.setPosition(.1);
        }else{
            midServo.setPosition(.75);
        }


        telemetry.update();
    }
}