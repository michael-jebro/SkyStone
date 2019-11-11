package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;

@TeleOp

public class Smth extends LinearOpMode {

    static DcMotor motorWheel1;
    static DcMotor motorWheel2;
    static DcMotor motorArm;

    static Servo servoHand1;
    static Servo servoHand2;

    public static ElapsedTime elapsedTime;

    static private Thread thread;

    // Key press response
    static double firstWheelPower;
    static double secondWheelPower;

    static double rightStickSignalY;
    static double rightStickSignalX;

    static boolean dpadUpPressed = false;
    static boolean dpadDownPressed = false;

    static boolean isLeftBumbperPressed = false;

    static boolean xPressed = false;
    static boolean yPressed = false;

    static boolean xWasPressed = false;
    static boolean yWasPressed = false;

    static boolean dead = false;
    static boolean threadRuns = false;


    public void playR2D2Loudly(){
        for (int i = 0; i < 7; i++) {
            SoundPlayer.getInstance().play(hardwareMap.appContext,
                    new File(Environment.getExternalStorageDirectory() +
                            File.separator + "rd2d2_sound.mp3"),
                    20, 0, 0);
        }
    }


    @Override
    public void runOpMode() {

        motorWheel1 = hardwareMap.get(DcMotor.class, "motor");
        motorWheel2 = hardwareMap.get(DcMotor.class, "motor2");
        motorArm = hardwareMap.get(DcMotor.class, "motor3");

        servoHand1 = hardwareMap.get(Servo.class, "servo");
        servoHand2 = hardwareMap.get(Servo.class, "servo1");

        elapsedTime = new ElapsedTime();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        int goldSoundID   = hardwareMap.appContext.getResources().getIdentifier("gold",   "raw", hardwareMap.appContext.getPackageName());

        // Reset timer
        elapsedTime.reset();

        while (opModeIsActive()) {

            firstWheelPower = -this.gamepad1.left_stick_y;
            motorWheel1.setPower(firstWheelPower);
            secondWheelPower = -this.gamepad1.right_stick_y;
            motorWheel2.setPower(-secondWheelPower);

            // Get state of arm buttons
            dpadUpPressed = this.gamepad1.dpad_left;
            dpadDownPressed = this.gamepad1.dpad_right;

            yPressed = this.gamepad1.y;

            dpadUpPressed = this.gamepad1.dpad_up;
            dpadDownPressed = this.gamepad1.dpad_down;


            // Check if arm buttons pressed and set power
            if(!dpadUpPressed & !dpadDownPressed) {
                motorArm.setPower(0);
            }else{

                if(dpadUpPressed){
                    motorArm.setPower(0.5);
                }

                if(dpadDownPressed){
                    motorArm.setPower(-0.5);
                }

            }

            // Get state of servo controller
            isLeftBumbperPressed = this.gamepad1.left_bumper;


            // Check if left bumber pressed and set
            //          servos' power

            if (isLeftBumbperPressed){
                servoHand1.setPosition(1);
                servoHand2.setPosition(0);
            }else{
                servoHand1.setPosition(0.5);
                servoHand2.setPosition(0.5);
            }


            // Check if Y button is pressed
            if (yPressed&!yWasPressed){

                    SoundPlayer.getInstance().play(hardwareMap.appContext,
                            goldSoundID,
                                        20, 0, 0);


                telemetry.addData("Playing", "Resource Gold");
            }

            yWasPressed = yPressed;

            /*dead = (int)firstWheelPower==0 & (int)secondWheelPower==0 & !dpadDownPressed &
                    !dpadUpPressed & !yPressed & !isLeftBumbperPressed;

            if((int)elapsedTime.time()==10 & dead ){
                elapsedTime.reset();
                while(dead) {
                    if((int)elapsedTime.time()==2){
                        elapsedTime.reset();
                        servoHand1.setPosition(0.5);
                        servoHand2.setPosition(0.5);
                    }

                    if((int)elapsedTime.time()==1){
                        servoHand1.setPosition(1);
                        servoHand2.setPosition(0);
                    }


                    motorWheel1.setPower(1);
                    servoHand1.setPosition(1);
                    servoHand2.setPosition(0);


                    firstWheelPower = -this.gamepad1.left_stick_y;
                    secondWheelPower = -this.gamepad1.right_stick_y;


                    dpadUpPressed = this.gamepad1.dpad_left;
                    dpadDownPressed = this.gamepad1.dpad_right;

                    yPressed = this.gamepad1.y;

                    dpadUpPressed = this.gamepad1.dpad_up;
                    dpadDownPressed = this.gamepad1.dpad_down;
                    isLeftBumbperPressed = this.gamepad1.left_bumper;
                    dead = (int)firstWheelPower==0 & (int)secondWheelPower==0 & !dpadDownPressed &
                            !dpadUpPressed & !yPressed & !isLeftBumbperPressed;
                }

                elapsedTime.reset();


            }

            if((int)elapsedTime.time()==5){
                elapsedTime.reset();
            }*/

            /*if(dead){
                threadRuns = true;

                DoSmth dsmth = new DoSmth();
                dsmth.start();
            }*/


            telemetry.addData("Dead", dead);
            telemetry.addData("dpadDown", dpadDownPressed);
            telemetry.addData("dpadUp", dpadUpPressed);
            telemetry.addData("yPressed", yPressed);
            telemetry.addData("threadRuns", threadRuns);

            telemetry.addData("Motor 1", firstWheelPower);
            telemetry.addData("Motor 2", secondWheelPower);
            telemetry.addData("Timer", elapsedTime.time());
            telemetry.addData("Status", "Running");
            telemetry.update();


        }
    }
}

class DoSmth extends Thread {
    private Thread thread;
    static int timer;

    @Override
    public void run() {

            //Smth.elapsedTime.reset();
            while (Smth.dead) {
                try{

                    thread.wait(1000);
                    timer++;
                    if (timer == 5) {
                        Smth.motorWheel1.setPower(1);
                    }

                }catch (InterruptedException ie) {

                }

            }

            Smth.motorWheel1.setPower(0);
            Smth.threadRuns = false;

        /*try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }*/
    }

    @Override
    public void start () {

        if (thread == null) {
            this.thread = new Thread (this, "DoSmth");
            thread.start();
        }

    }
}