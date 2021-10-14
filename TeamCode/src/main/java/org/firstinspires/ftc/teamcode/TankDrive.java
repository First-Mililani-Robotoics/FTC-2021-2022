package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Tank Drive", group="Pushbot")

public class TankDrive extends OpMode {

   /* Declare OpMode members. */
   hardwareDeclarations robot       = new hardwareDeclarations(); // use the class created to define a Pushbot's hardware


   /*
    * Code to run ONCE when the driver hits INIT
    */
   @Override
   public void init() {
      /* Initialize the hardware variables.
       * The init() method of the hardware class does all the work here
       */
      robot.init(hardwareMap);

      // Send telemetry message to signify robot waiting;
      telemetry.addData("Say", "Hello Driver");
   }

   /*
    * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    */
   @Override
   public void init_loop() {
   }

   /*
    * Code to run ONCE when the driver hits PLAY
    */
   @Override
   public void start() {
   }

   /*
    * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    */
   @Override
   public void loop() {
      double left;
      double right;
      float intake;
      float scoreFreight;
      boolean payloadUp;
      boolean payloadDown;
      boolean spinCarousel;

      // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
      left = -gamepad1.left_stick_y;
      right = -gamepad1.right_stick_y;
      intake = gamepad1.left_trigger;
      scoreFreight = gamepad1.right_trigger;
      payloadUp = gamepad1.left_bumper;
      payloadDown = gamepad1.right_bumper;
      spinCarousel = gamepad1.b;

      robot.leftFrontDrive.setPower(left);
      robot.leftBackDrive.setPower(left);
      robot.rightFrontDrive.setPower(right);
      robot.rightBackDrive.setPower(right);
      // Power is from 0 to 1; this is saying that the duck motor will always be on.
      // Is that what you want?
      robot.duckMotor.setPower(0);


     if (intake == 1) {
        robot.freightMotor.setPower(1);
     } else {
        robot.freightMotor.setPower(0);
     }

      if (scoreFreight == 1) {
         robot.freightMotor.setPower(-1);
      } else {
         robot.freightMotor.setPower(0);
      }

      if (payloadUp == true) {
         robot.pivotMotor.setPower(1);
      } else {
         robot.pivotMotor.setPower(0);
      }

      if (payloadDown == true) {
         robot.pivotMotor.setPower(-1);
      } else {
         robot.pivotMotor.setPower(0);
      }




      // Move both servos to new position.  Assume servos are mirror image of each other.




      // Send telemetry message to signify robot running;
      telemetry.addData("left",  "%.2f", left);
      telemetry.addData("right", "%.2f", right);
   }

   /*
    * Code to run ONCE after the driver hits STOP
    */
   @Override
   public void stop() {
   }
}
