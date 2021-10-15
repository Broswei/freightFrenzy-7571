package org.firstinspires.ftc.teamcode.lib.util;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Global variables used throughout classes
 * these need to be used by more than one class at a time
 */

public class GlobalVars {

    /*public final static double STONE_LENGTH = 20.32; //cm
    public final static double STONE_WIDTH = 10.16; //cm
*/
    public final static double ROBOT_WIDTH = 45.72; //cm
    public final static double ROBOT_LENGTH = 45.085; //cm

    /*public final static double DIST_FROM_WALL_TO_STONES = 119.38; //cm
    public final static double ORIGIN_TO_STONES = DIST_FROM_WALL_TO_STONES - (ROBOT_WIDTH/2);*/

    public static double worldXPosition = 0.0;
    public static double worldYPosition = 0.0;
    public static double worldAngle_rad = 0.0;

    public static double currentXTicks = 0;
    public static double currentYTicks = 0;


    public static double wxRelative = 0.0;
    public static double wyRelative = 0.0;

    public static double deltaX = 0.0;
    public static double deltaY = 0.0;

    public static double movement_x = 0;
    public static double movement_y = 0;
    public static double movement_turn = 0;

    public static double xTarget = 0;
    public static double yTarget = 0;
    public static double aTarget = 0;

    public static boolean atTarget = false;

    //use these to make more accurate control names
    // i.e. if(spinIntake) rather than if(gamepad1.a)
    public static Gamepad mainGp, auxGp;

    public static int auto = 0;

    //public static boolean isAuto = false;

    public enum AutoType{
        RED, BLUE
    }

    public static AutoType autoType = AutoType.RED;

    public enum AutoStates{
        START, MOVE, MOVE2, MOVE3, MOVE4, END
    }

    public enum LZStates{
        START, GET_SKYSTONE_LOCATIONS, MOVE_TO_SS_1, GRAB_SS_1, MOVE_TO_FOUNDATION, MOVE_FOUNDATION, DEPLOY_YEETER, YEET, DEPOSIT_SS_1, MOVE_TO_SS_2, GRAB_SS_2, DEPOSIT_SS_2, PARK_NEAR, PARK_FAR, END
    }

    public static AutoStates autoState = AutoStates.START;

    public static LZStates autoStateLZ = LZStates.START;


    public enum RobotStates{
        FINISHED, AT_TARGET, MOVING_TO_TARGET, MOVING, STOPPED
    }

    public enum ClawStates{
        DEPOSITING, GRIPPING, IDLE
    }


    public static ClawStates clawState = ClawStates.IDLE;


    public static RobotStates roboState = RobotStates.STOPPED;

    public final static double autoMoveSpeed = 0.5;
    public final static double autoTurnSpeed = 0.5;

    public final static double mTolerance = 1;
    public final static double aTolerance = 1;

    public static double strafeConstant = 19.65;

    //PIDx
    public static double xKp = 0.07;
    public static double xKi = 0;
    public static double xKd = 0;

    //PIDy
    public static double yKp = 0.07;
    public static double yKi = 0;
    public static double yKd = 0;

    //PIDa
    public static double aKp = 0.03;
    public static double aKi = 0;
    public static double aKd = 0;

    public static double eKp = 0.001;
    public static double eKi = 0;
    public static double eKd = 0;

    public static double dKp = 0.03;
    public static double dKi = 0;
    public static double dKd = 0;

}
