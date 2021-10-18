
package org.firstinspires.ftc.teamcode.lib.hardware.manip;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.lib.hardware.base.Subsystem;

public class Intake extends Subsystem {

    public DcMotor intake;

    private final double intakeSoftDeliverySpeed = 0.4;

    private double target = 0;


    public Intake(){

    }

    public void init(DcMotor intake) {

        this.intake = intake;

        this.intake.setDirection(DcMotorSimple.Direction.REVERSE);

        this.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }


//updates the intake in relation to its target power


    @Override
    public void update() {


            if(target > 0){
                target = intakeSoftDeliverySpeed;
            }

        intake.setPower(target);



    }




    public void manaualControl(Gamepad gamepad){

        setPower(gamepad.right_trigger);

    }






    //updates the intakes target power
     //@param target the target power


    public void setTarget(double target) {

        this.target = target;


    }




      //updates the intakes target power
      //@param target the target power


    public void setTarget(double target, boolean hitterTarget) {

        this.target = target;


    }

    public void setPower(double power){
        intake.setPower(power);
    }

    @Override
    public void finishJob() {

    }


    public static void manualControl(Gamepad gamepad){


    }
}



