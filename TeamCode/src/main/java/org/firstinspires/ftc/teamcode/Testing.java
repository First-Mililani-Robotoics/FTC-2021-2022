package org.firstinspires.ftc.teamcode;
    //Drugs = cure depression
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="Auto", group="Pushbot")
public class Testing extends LinearOpMode {


    //Declaring OpMode members

    hardwareDeclarations robot = new hardwareDeclarations();
    private ElapsedTime runtime = new ElapsedTime();

    //Constants
    final double COUNTS_PER_MOTOR_REV = 1120; //Counts to rotations, testing later
    final double DRIVE_GEAR_REDUCTION = 1.0; //If gears are added
    final double WHEEL_DIAMETER_INCHES = 4.0; //Wheel size
    final double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_INCHES; //Circumference of wheel
    final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / CIRCUMFERENCE; //Converting counts to inches
    final double ROBOT_RADIUS = 12940148309520593.003; //change value later on

    static final double DRIVE_SPEED = 1; //new settings for speed?
    static final double TURN_SPEED = 1;

    @Override //change method name?
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.duckMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Duck", "Starting at %7d");
        telemetry.update();
        waitForStart();
        while(runtime.seconds() < 10) {
            robot.duckMotor.setPower(0.7);
        }
        //spin carousel with duckMotor


    }
}
/*
        robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",

                robot.leftBackDrive.getCurrentPosition(),
                robot.leftFrontDrive.getCurrentPosition(),
                robot.rightBackDrive.getCurrentPosition(),
                robot.rightFrontDrive.getCurrentPosition());



        encoderDrive(-1, 12, 12,2); //reset very measurement present, they're probably inaccurate-peko
        //move backwards to carousel



        turnDrive(1, 90,2);
        //turns right and faces west wall (refer to final path)

        encoderDrive(1, 12, 12, 2);
        //move towards west wall

        turnDrive(1, 90, 2);
        //turns right and faces north wall

        encoderDrive(1, 108, 108,10);
        //robot moves forward and completely enters warehouse

    }



    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS){
        int newLeftTarget;
        int newRightTarget;
        double rightPower = speed;
        double leftPower = speed;
        newLeftTarget = (int)(leftInches * COUNTS_PER_INCH);
        newRightTarget = (int)(rightInches * COUNTS_PER_INCH);

        robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftBackDrive.setTargetPosition(newLeftTarget);
        robot.leftFrontDrive.setTargetPosition(newLeftTarget);
        robot.rightFrontDrive.setTargetPosition(newRightTarget);
        robot.rightBackDrive.setTargetPosition(newRightTarget);

        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        robot.leftFrontDrive.setPower(leftPower);
        robot.leftBackDrive.setPower(leftPower);
        robot.rightFrontDrive.setPower(rightPower);
        robot.rightBackDrive.setPower(rightPower);

        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) && robot.leftBackDrive.isBusy() || robot.leftFrontDrive.isBusy() || robot.rightFrontDrive.isBusy() || robot.rightBackDrive.isBusy()) {

            telemetry.addData("robotCurrentPosition", "Running at %7d :%7d :%7d :%7d",
                    robot.leftBackDrive.getCurrentPosition(),
                    robot.leftFrontDrive.getCurrentPosition(),
                    robot.rightBackDrive.getCurrentPosition(),
                    robot.rightFrontDrive.getCurrentPosition());

        }

        robot.leftFrontDrive.setPower(0);
        robot.leftBackDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.rightBackDrive.setPower(0);

        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public void turnDrive(double speed, double degrees, double timeoutS){

        int newLeftTarget;
        int newRightTarget;
        double rightPower = speed;
        double leftPower = speed;
        double degreesToInches = (degrees * (Math.PI / 180) * ROBOT_RADIUS);

        if (degrees > 0) {
            //if we're turning right (a positive amount of degrees)...

            newLeftTarget = (int)(degreesToInches * COUNTS_PER_INCH);
            newRightTarget = -(int)(degreesToInches * COUNTS_PER_INCH);

            rightPower = -speed;
            leftPower = speed;

        } else {
            //if we're turning left (a negative amount of degrees)...

            newLeftTarget = -(int)(degreesToInches * COUNTS_PER_INCH);
            newRightTarget = (int)(degreesToInches * COUNTS_PER_INCH);

            rightPower = speed;
            leftPower = -speed;
        }


        robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftBackDrive.setTargetPosition(newLeftTarget);
        robot.leftFrontDrive.setTargetPosition(newLeftTarget);
        robot.rightFrontDrive.setTargetPosition(newRightTarget);
        robot.rightBackDrive.setTargetPosition(newRightTarget);

        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        robot.leftFrontDrive.setPower(leftPower);
        robot.leftBackDrive.setPower(leftPower);
        robot.rightFrontDrive.setPower(rightPower);
        robot.rightBackDrive.setPower(rightPower);

        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (robot.leftBackDrive.isBusy() ||
                        robot.leftFrontDrive.isBusy() ||
                        robot.rightFrontDrive.isBusy() ||
                        robot.rightBackDrive.isBusy()))
        {


            telemetry.addData("robotCurrentPosition", "Running at %7d :%7d :%7d :%7d",
                    robot.leftBackDrive.getCurrentPosition(),
                    robot.leftFrontDrive.getCurrentPosition(),
                    robot.rightBackDrive.getCurrentPosition(),
                    robot.rightFrontDrive.getCurrentPosition());

        }

        robot.leftFrontDrive.setPower(0);
        robot.leftBackDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.rightBackDrive.setPower(0);

        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
 */
