package org.firstinspires.ftc.Team16877Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "WASD", group = "Linear Opmode")
public class OpMOde extends LinearOpMode {

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;

    DcMotorSimple liftMotor;
    DcMotorSimple parkingMotor;

    Servo getServo;
    Servo foundation1;
    Servo foundation2;
    Servo liftservo;

    boolean rightBumperWasPressed;
    boolean leftBumperWasPressed;
    boolean bWasPressed;
    boolean xWasPressed;

    boolean reverseControl = false;
    boolean changePower = false;

    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;


    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "MotorFrontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "MotorFrontRight");
        leftBack = hardwareMap.get(DcMotor.class, "MotorBackLeft");
        rightBack = hardwareMap.get(DcMotor.class, "MotorBackRight");

        parkingMotor = hardwareMap.get(DcMotorSimple.class, "motor5");
        liftMotor = hardwareMap.get(DcMotorSimple.class, "liftMotor");

        foundation1 = hardwareMap.get(Servo.class, "servoFoundation1");
        foundation2 = hardwareMap.get(Servo.class, "servoFoundation2");
        liftservo = hardwareMap.get(Servo.class, "liftServo");


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();

        liftservo.setPosition(0.8);

        foundation1.setPosition(0);
        foundation2.setPosition(1);

        while (opModeIsActive()) {
            if ((gamepad1.right_stick_y != 0) || (gamepad1.left_stick_y != 0)) { //Control by sticks
                if(!reverseControl) {
                    leftFrontPower = (-gamepad1.left_stick_y);
                    leftBackPower = (-gamepad1.left_stick_y);
                    rightFrontPower = (gamepad1.right_stick_y);
                    rightBackPower = (gamepad1.right_stick_y);
                } else {
                    leftFrontPower = (-gamepad1.right_stick_y);
                    leftBackPower = (-gamepad1.right_stick_y);
                    rightFrontPower = (gamepad1.left_stick_y);
                    rightBackPower = (gamepad1.left_stick_y);
                }
            } else if (gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_left) { // Move forward
                leftFrontPower = (0.5);
                leftBackPower = (0.5);
                rightFrontPower = (-0.5);
                rightBackPower = (-0.5);
            } else if (!gamepad1.dpad_up && gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_left) { // Move back
                leftFrontPower = (-0.5);
                leftBackPower = (-0.5);
                rightFrontPower = (0.5);
                rightBackPower = (0.5);
            } else if (!gamepad1.dpad_up && !gamepad1.dpad_down && gamepad1.dpad_right && !gamepad1.dpad_left) { // Move Right
                leftFrontPower = (0.5);
                leftBackPower = (-0.5);
                rightFrontPower = (0.5);
                rightBackPower = (-0.5);
            } else if (!gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_right && gamepad1.dpad_left) { // Move Left
                leftFrontPower = (-0.5);
                leftBackPower = (0.5);
                rightFrontPower = (-0.5);
                rightBackPower = (0.5);
            } else if (gamepad1.dpad_up && !gamepad1.dpad_down && gamepad1.dpad_right && !gamepad1.dpad_left) { // Move forwardRight
                leftFrontPower = (0.5);
                leftBackPower = (0);
                rightFrontPower = (0);
                rightBackPower = (-0.5);
            } else if (gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_right && gamepad1.dpad_left) { // Move forwardLeft
                leftFrontPower = (0);
                leftBackPower = (0.5);
                rightFrontPower = (-0.5);
                rightBackPower = (0);
            } else if (!gamepad1.dpad_up && gamepad1.dpad_down && !gamepad1.dpad_right && gamepad1.dpad_left) { // Move downLeft
                leftFrontPower = (-0.5);
                leftBackPower = (0);
                rightFrontPower = (0);
                rightBackPower = (0.5);
            } else if (!gamepad1.dpad_up && gamepad1.dpad_down && gamepad1.dpad_right && !gamepad1.dpad_left) { // Move downRight
                leftFrontPower = (0);
                leftBackPower = (-0.5);
                rightFrontPower = (0.5);
                rightBackPower = (0);
            } else { // Stop
                leftFrontPower = (0);
                leftBackPower = (0);
                rightFrontPower = (0);
                rightBackPower = (0);
            }

            if (gamepad1.b && !bWasPressed){
                if (reverseControl){
                    bWasPressed = true;
                    reverseControl = false;
                } else {
                    bWasPressed = true;
                    reverseControl = true;
                }
            } else if (!gamepad1.b){
                bWasPressed = false;
            }
            if (gamepad1.x && !xWasPressed){
                if (reverseControl){
                    xWasPressed = true;
                    changePower = false;
                } else {
                    xWasPressed = true;
                    changePower = true;
                }
            } else if (!gamepad1.x){
                xWasPressed = false;
            }

            }if (changePower){
               leftFrontPower =  leftFrontPower / 2;
               rightFrontPower = rightFrontPower / 2;
               leftBackPower = leftBackPower / 2;
               rightBackPower = rightBackPower /2 ;
            }

            if (reverseControl){
               leftFront.setPower(-leftFrontPower);
               rightFront.setPower(-rightFrontPower);
               leftBack.setPower(-leftBackPower);
               rightBack.setPower(-rightBackPower);
            } else {
                leftFront.setPower(leftFrontPower);
                rightFront.setPower(rightFrontPower);
                leftBack.setPower(leftBackPower);
                rightBack.setPower(rightBackPower);


            //lift servo control
            if (gamepad1.right_bumper && !rightBumperWasPressed){
                if (liftservo.getPosition()!= 0.8){
                    rightBumperWasPressed = true;
                    liftservo.setPosition(0.8);
                } else {
                    rightBumperWasPressed = true;
                    liftservo.setPosition(0.3);
                }
            } else if (!gamepad1.right_bumper){
                rightBumperWasPressed = false;
            }

            if (gamepad1.left_bumper && !leftBumperWasPressed){
                if (foundation1.getPosition()<0.05){
                    leftBumperWasPressed = true;
                    foundation1.setPosition(1);
                    foundation2.setPosition(0);
                } else {
                    leftBumperWasPressed = true;
                    foundation1.setPosition(0);
                    foundation2.setPosition(1);
                }
            } else if (!gamepad1.left_bumper){
                leftBumperWasPressed = false;
            }

            //lift control
            if(gamepad1.a)
            {
                liftMotor.setPower(1);
            }
            else
            {
                liftMotor.setPower(0);
            }
            if(gamepad1.y)
            {
                liftMotor.setPower(-1);
            }
            else
            {
                liftMotor.setPower(0);
            }
        }
    }
}
