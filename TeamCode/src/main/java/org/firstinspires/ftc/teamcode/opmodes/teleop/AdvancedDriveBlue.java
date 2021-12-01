package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;

@TeleOp (group = "DriveTest")
public class AdvancedDriveBlue extends Robot{

private boolean yButton2Toggle=false;

        boolean isSlow = false;
        //Changes behavior when intaking
        boolean isIntaking = false;
        int liftZero = 0;

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

        //Strafe drive, slows down when intaking
        if(gamepad1.left_trigger>.1){
        dt.manualControl(gamepad1,isSlow);
        }else{
        dt.fieldOrientedControl(gamepad1, isSlow);
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
            //Lift base/intake position on a or if intaking
            if(gamepad2.a || isIntaking || gamepad2.left_bumper) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(100);

            }
            //lift level 1 position on b
            else if(gamepad2.b){
                lift.setTargetPosition(200);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //lift level 2 position on x
            else if(gamepad2.x){
                lift.setTargetPosition(550);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //lift level 3 position on y
            else if(gamepad2.y) {
                lift.setTargetPosition(1000);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(600);
            }
            //Default to lowest position that doesn't hit the barrier for stability
            else{
                lift.setTargetPosition(5);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(200);
                if(magLim.isPressed()){
                    liftZero+=lift.getCurrentPosition();
                    lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
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

        spinner.setPower(-gamepad1.right_trigger);


        telemetry.update();
    }
}