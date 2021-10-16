package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Autopath", group="Pushbot")

public class Autopath extends LinearOpMode {
    robotDeclarations robot = new robotDeclarations();
    private ElapsedTime runtime = new ElapsedTime();

    final double wheelDiameterInches = 4; //this is the diameter of the wheels we are using
    final double circumferece = Math.PI * wheelDiameterInches;
    //needs testing
    final double driveSpeed = 1.0;
    //needs testing
    final double turnSpeed = 1.0;
    final double gearReduction = 1;
    final double ticksPerRev = 537.6; //amount of ticks per revolution for *insert motor name*
    public final int tileLength = 24; //each tile is 24 inches or 2 feet
    public final double carouselCircumference = (Math.PI * 15) + 2; //carousel diameter is 15 inches, add 2 inches to push off duck

    final double countsPerInch = (ticksPerRev * gearReduction)/circumferece;

    @Override
    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftFrontDrive.getCurrentPosition(),
                robot.leftBackDrive.getCurrentPosition(),
                robot.rightFrontDrive.getCurrentPosition(),
                robot.rightBackDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        //moves robot to the carousel (needs testing)
        encoderDrive(driveSpeed, tileLength * 2, tileLength * 2, 3.0);  // S1: Forward 47 Inches with 5 Sec timeout

        //spins the duck carousel (needs testing)
        spinDuck(0.5, 5);

        //moves robot to the warehouse (needs testing)
        encoderDrive(driveSpeed, -tileLength * 5.25, -tileLength * 5.25, 7.0);
    }

    //methods

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutSeconds) {

        //new location robot has to travel to
        int newRightFrontTarget;
        int newLeftFrontTarget;
        int newRightBackTarget;
        int newLeftBackTarget;

        if(opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition() + (int)(rightInches * countsPerInch);
            newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newRightBackTarget = robot.rightBackDrive.getCurrentPosition() + (int)(rightInches * countsPerInch);
            newLeftBackTarget = robot.leftBackDrive.getCurrentPosition() + (int)(leftInches * countsPerInch);

            robot.leftFrontDrive.setTargetPosition(newLeftFrontTarget);
            robot.rightFrontDrive.setTargetPosition(newRightFrontTarget);
            robot.leftBackDrive.setTargetPosition(newLeftBackTarget);
            robot.rightBackDrive.setTargetPosition(newRightBackTarget);

            //turns on RUN_TO_POSITION
            robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //resets the timeout time and start motion
            runtime.reset();
            robot.leftFrontDrive.setPower(Math.abs(speed));
            robot.rightFrontDrive.setPower(Math.abs(speed));
            robot.leftBackDrive.setPower(Math.abs(speed));
            robot.rightBackDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutSeconds) &&
                    (robot.leftFrontDrive.isBusy() && robot.rightFrontDrive.isBusy()
                    && robot.leftBackDrive.isBusy() && robot.rightBackDrive.isBusy())) {


                //display for driver
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftFrontTarget,
                        newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Running to %7d :%7d",
                        robot.leftFrontDrive.getCurrentPosition(),
                        robot.rightFrontDrive.getCurrentPosition(),
                        robot.leftBackDrive.getCurrentPosition(),
                        robot.rightBackDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftFrontDrive.setPower(0);
            robot.rightFrontDrive.setPower(0);
            robot.leftBackDrive.setPower(0);
            robot.rightBackDrive.setPower(0);

            //turn off RUN_TO_POSITION
            robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /* turns on the carousel wheel's motor to spin the carousel
    *  precondition: Robot has to be next to the carousel and duck should be touching the carousel
    *  post-condition: The duck should be pushed off the carousel
    */
    public void spinDuck(double motorSpeed, double timeoutSeconds) {

        //reset runtime
        runtime.reset();

        int newCarouselTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            newCarouselTarget = robot.carouselWheel.getCurrentPosition() + (int) (carouselCircumference * ticksPerRev);
            robot.carouselWheel.setTargetPosition(newCarouselTarget);
            robot.carouselWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.carouselWheel.setPower(Math.abs(motorSpeed));

            //update driver
            while (opModeIsActive() && runtime.seconds() < timeoutSeconds && robot.carouselWheel.isBusy()) {
                telemetry.addData("Carousel spinning", "Running to %7d :%7d",
                        robot.carouselWheel.getCurrentPosition());
                telemetry.update();
            }

        //reset motor power
        robot.carouselWheel.setPower(0);
        }
    }
}
