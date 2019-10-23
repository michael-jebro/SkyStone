package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Smth extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor motorTest;
    private DcMotor motorTest2;
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    private Servo servoTest;



    @Override
    public void runOpMode() {

        motorTest = hardwareMap.get(DcMotor.class, "motor");
        motorTest2 = hardwareMap.get(DcMotor.class, "motor2");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double tgtPower = 0;
        double tgtPower2 = 0;
        while (opModeIsActive()) {
            tgtPower = -this.gamepad2.left_stick_x;
            motorTest.setPower(tgtPower);
            tgtPower2 = -this.gamepad1.right_stick_x;
            motorTest2.setPower(tgtPower2);
            telemetry.addData("Power 1", tgtPower);
            telemetry.addData("Power 2", tgtPower2);
            telemetry.addData("1st Motor Power", motorTest.getPower());
            telemetry.addData("2st Motor Power", motorTest2.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}