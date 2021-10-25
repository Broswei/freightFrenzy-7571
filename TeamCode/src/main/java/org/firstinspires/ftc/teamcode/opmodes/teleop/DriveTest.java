package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;
import org.firstinspires.ftc.teamcode.lib.hardware.manip.Intake;

@TeleOp (group = "DriveTest")
public class DriveTest extends Robot{

private boolean yButton2Toggle=false;

        boolean test=true;

        boolean isSlow = false;

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

        if(gamepad1.left_trigger>=0.01){
            isSlow=true;
        }else{
            isSlow=false;
        }

        //Drivetrain control
        dt.manualControl(gamepad1,isSlow);

        //Intake in/out on right trigger/bumper
        if (gamepad2.right_bumper){
            intake.setPower(0.5);
        }
        else{
            intake.setPower(-gamepad2.right_trigger);
        }

        //Lift base/intake position on a
        if(gamepad2.a) {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
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
            lift.setTargetPosition(910);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setVelocity(600);
        }
        if(gamepad2.left_bumper){
            pushServo.setPower(-1);
        }else{
            pushServo.setPower(0);
        }

        if (gamepad2.left_trigger > 0){
            platServo.setPower(0.5);
        }
        else{
            platServo.setPower(0);
        }

        if (gamepad1.right_trigger > 0){
            midServo.setPower(-1);
        }
        else{
            midServo.setPower(0.25);
        }

        if (gamepad1.right_bumper){
            rampServo.setPosition(.8);
        }
        if(gamepad1.left_bumper){
            rampServo.setPosition(.95);
        }

        telemetry.update();
    }
}