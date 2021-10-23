/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Tank Drive", group="Pushbot")

public class TankDrive extends OpMode{

    /* Declare OpMode members. */
    robotDeclarations robot       = new robotDeclarations(); // use the class created to define a Pushbot's hardware
    ElapsedTime timer = new ElapsedTime();
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
        telemetry.addData("Say", "Hello Driver");    //
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
        double leftTriggerGamepad1;

        final int carouselRPM = 47; //Max RMP of the carousel before the duck falls off
        final int carouselWheelRPM = 185; //Max RPM of the carousel wheel before it's too fast
        final double optimalTicksPerSecond = 1657.6; //the RPM of the motor in ticks per second
        double curentTicksPerSecond =

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        leftTriggerGamepad1 = gamepad1.left_trigger;

        //sets motors to half speed (half speed drive)
        if(leftTriggerGamepad1 > 0.5) {
            robot.leftBackDrive.setPower(left/2.0);
            robot.leftFrontDrive.setPower(left/2.0);
            robot.rightBackDrive.setPower(right/2.0);
            robot.rightFrontDrive.setPower(right/2.0);
        }
        else {
            robot.leftBackDrive.setPower(left);
            robot.leftFrontDrive.setPower(left);
            robot.rightBackDrive.setPower(right);
            robot.rightFrontDrive.setPower(right);
        }


        // first payload code/intake

        //rightTrigger returns value from 0-1
        //rightBumper returns true or false statement depending on if it's pressed
        double rightTrigger = gamepad2.right_trigger;
        double leftTriggerGamepad2 = gamepad2.left_trigger;
        boolean rightBumper = gamepad2.right_bumper;

        //rightTrigger value can be altered
        if(rightTrigger > 0.5 && rightBumper) {
            robot.intakeWheel.setPower(0.5);
        }
        else if (rightTrigger > 0.5) {
            robot.intakeWheel.setPower(1);
        }
        else {
            robot.intakeWheel.setPower(0);
        }

        // second payload code
//        if(leftTriggerGamepad2 > 0.5) {
//            robot.carouselWheel.setPower(1);
//        }
//        else {
//            robot.carouselWheel.setPower(0);
//        }

        //test duck code for kikugawa strategy
        double carouselWheelCurrentTicks = 0;
        double carouselWheelTPS; //TPS = ticks per second

        if(leftTriggerGamepad2 >= 0.5) {
            robot.carouselWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.carouselWheel.setPower(0.725);
            while(timer.seconds() <= 0.1) {
                carouselWheelCurrentTicks = robot.carouselWheel.getCurrentPosition();
            }

            carouselWheelTPS = carouselWheelCurrentTicks * 10;

            if(carouselWheelTPS < optimalTicksPerSecond) {

            }
        }
        else {
            robot.carouselWheel.setPower(0);
        }




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

///* Copyright (c) 2017 FIRST. All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted (subject to the limitations in the disclaimer below) provided that
// * the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice, this list
// * of conditions and the following disclaimer.
// *
// * Redistributions in binary form must reproduce the above copyright notice, this
// * list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *
// * Neither the name of FIRST nor the names of its contributors may be used to endorse or
// * promote products derived from this software without specific prior written permission.
// *
// * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
// * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
// * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
// * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.firstinspires.ftc.teamcode;
//
//        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//        import com.qualcomm.robotcore.util.Range;
//
///**
// * This file provides basic Telop driving for a Pushbot robot.
// * The code is structured as an Iterative OpMode
// *
// * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
// * All device access is managed through the HardwarePushbot class.
// *
// * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
// * It raises and lowers the claw using the Gampad Y and A buttons respectively.
// * It also opens and closes the claws slowly using the left and right Bumper buttons.
// *
// * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
// * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
// */
//
//@TeleOp(name="Tank Drive", group="Pushbot")
//
//public class TankDrive extends OpMode{
//
//        /* Declare OpMode members. */
//        robotDeclarations robot       = new robotDeclarations(); // use the class created to define a Pushbot's hardware
//
///*
// * Code to run ONCE when the driver hits INIT
// */
//@Override
//public void init() {
//        /* Initialize the hardware variables.
//         * The init() method of the hardware class does all the work here
//         */
//        robot.init(hardwareMap);
//
//        // Send telemetry message to signify robot waiting;
//        telemetry.addData("Say", "Hello Driver");    //
//        }
//
///*
// * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
// */
//@Override
//public void init_loop() {
//        }
//
///*
// * Code to run ONCE when the driver hits PLAY
// */
//@Override
//public void start() {
//        }
//
///*
// * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
// */
//@Override
//public void loop() {
//        // double left;
//        // double right;
//        // double leftTriggerGamepad1;
//
//        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
//        // left = gamepad1.left_stick_y;
//        // right = -gamepad1.right_stick_y;
//        // leftTriggerGamepad1 = gamepad1.left_trigger;
//
//        //sets motors to half speed (half speed drive)
//        // if(leftTriggerGamepad1 > 0.5) {
//        //     robot.leftBackDrive.setPower(left/2.0);
//        //     robot.leftFrontDrive.setPower(left/2.0);
//        //     robot.rightBackDrive.setPower(right/2.0);
//        //     robot.rightFrontDrive.setPower(right/2.0);
//        // }
//        // else {
//        //     robot.leftBackDrive.setPower(left);
//        //     robot.leftFrontDrive.setPower(left);
//        //     robot.rightBackDrive.setPower(right);
//        //     robot.rightFrontDrive.setPower(right);
//        // }
//
//
//        // first payload code/intake
//
//        //rightTrigger returns value from 0-1
//        //rightBumper returns true or false statement depending on if it's pressed
//        // double rightTrigger = gamepad2.right_trigger;
//        double leftTriggerGamepad2 = gamepad2.left_trigger;
//        boolean leftBumperGamepad2 = gamepad2.left_bumper;
//        float leftGamepad2 = -gamepad2.left_stick_y;
//        boolean a = gamepad2.a;
//        boolean b = gamepad2.b;
//        boolean x = gamepad2.x;
//        boolean y = gamepad2.y;
//        // boolean rightBumper = gamepad2.right_bumper;
//
//        //rightTrigger value can be altered
//        // if(rightTrigger > 0.5 && rightBumper) {
//        //     robot.intakeWheel.setPower(0.5);
//        // }
//        // else if (rightTrigger > 0.5) {
//        //     robot.intakeWheel.setPower(1);
//        // }
//        // else {
//        //     robot.intakeWheel.setPower(0);
//        // }
//
//        // second payload code
//
//
//        if(leftTriggerGamepad2 > 0.5 && leftBumperGamepad2) {
//        robot.carouselWheel.setPower(0.7);
//        }
//        else if(leftTriggerGamepad2 > 0.5) {
//        robot.carouselWheel.setPower(0.8);
//        }
//        else if(leftGamepad2 > 0){
//        robot.carouselWheel.setPower(leftGamepad2);
//        }
//        else if(a) {
//        robot.carouselWheel.setPower(0.5);
//        }
//        else if(b) {
//        robot.carouselWheel.setPower(0.75);
//        }
//        else if(x) {
//        robot.carouselWheel.setPower(0.6);
//        }
//        else if(y) {
//        robot.carouselWheel.setPower(0.9);
//        }
//        else {
//        robot.carouselWheel.setPower(0);
//        }
//
//
//        // Send telemetry message to signify robot running;
//        // telemetry.addData("left",  "%.2f", left);
//        // telemetry.addData("right", "%.2f", right);
//        }
//
///*
// * Code to run ONCE after the driver hits STOP
// */
//@Override
//public void stop() {
//        }
//        }
