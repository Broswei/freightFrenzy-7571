package org.firstinspires.ftc.teamcode.lib.hardware.base;

/**
 * subclass for any hardware class that is not the drivetrain
 */

public abstract class Subsystem {

  private static boolean enabled = true;

  public Subsystem(){

  }

  /**
   * sets hardware mapped references equal to local references
   */

  //public abstract void setup(Object object);

  /**
   * updates the subsystem in according to its designated target or desired state
   */
  public abstract void update();

  /**
   * sets the target or state for the subsystem to want to reach
   */
  //public abstract void setTarget(double target);


  /**
   * used for finishing a movement or other state change at another time
   */
  public abstract void finishJob();

  /**
   * sets the enabled state to true
   * means the system will operate
   */
  public static void enable(){

    enabled = true;

  }

  /**
   * sets the enabled state to false
   * means the system will not operate
   */
  public static void disable(){

    enabled = false;

  }

  /**
   *
   * @return whether or not the system is enabled
   */
  public static boolean isEnabled(){
    return enabled;
  }





}
