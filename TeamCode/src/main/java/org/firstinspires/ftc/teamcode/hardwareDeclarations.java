package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class hardwareDeclarations
{
   /* Public OpMode members. */
   public DcMotor  leftFrontDrive   = null;
   public DcMotor  rightFrontDrive  = null;
   public DcMotor  leftBackDrive   = null;
   public DcMotor  rightBackDrive  = null;
   public DcMotor  freightMotor = null;
   public DcMotor  duckMotor = null;
   public DcMotor  pivotMotor = null;

   /* local OpMode members. */
   HardwareMap hwMap           =  null;
   private ElapsedTime period  = new ElapsedTime();

   /* Constructor */
   public hardwareDeclarations(){
   }

   /* Initialize standard Hardware interfaces */
   public void init(HardwareMap ahwMap) {
      // Save reference to Hardware map

      leftFrontDrive = hwMap.get(DcMotor.class, "left_front_drive");
      rightFrontDrive = hwMap.get(DcMotor.class, "right_front_drive");
      leftBackDrive = hwMap.get(DcMotor.class, "left_back_drive");
      rightBackDrive = hwMap.get(DcMotor.class, "right_back_drive");
      freightMotor = hwMap.get(DcMotor.class, "freight_motor");
      duckMotor = hwMap.get(DcMotor.class, "duck_motor");
      pivotMotor = hwMap.get(DcMotor.class, "pivot_motor");

       leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
       leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
       rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
       rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
       duckMotor.setDirection(DcMotor.Direction.REVERSE); // check pivot + freight direction?
       pivotMotor.setDirection(DcMotor.Direction.FORWARD);
       freightMotor.setDirection(DcMotor.Direction.FORWARD);

       leftFrontDrive.setPower(0);
       leftBackDrive.setPower(0);
       rightFrontDrive.setPower(0);
       rightBackDrive.setPower(0);
       freightMotor.setPower(0);
       duckMotor.setPower(0);
       pivotMotor.setPower(0);


        //should make it so that they run with encoders
        //indentation for this whole section is weird. Try fix?
           leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           freightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           duckMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           pivotMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

   }
}