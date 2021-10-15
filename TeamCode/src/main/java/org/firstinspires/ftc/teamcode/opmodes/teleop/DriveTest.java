package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.hardware.base.Robot;

@TeleOp (group = "DriveTest")
public class DriveTest extends Robot{

private boolean yButton2Toggle=false;

        boolean test=true;

        boolean isSlow=false;

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

        }

        else{
        isSlow=false;
        }

        dt.manualControl(gamepad1,isSlow);

        telemetry.update();
    }
}