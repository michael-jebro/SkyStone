package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.io.File;

@TeleOp

public class Smth extends LinearOpMode {

    static DcMotor motorWheel1;
    static DcMotor motorWheel2;
    static DcMotor motorArm;
    static ColorSensor WheelLightSensor;

    static Servo servoHand1;
    static Servo servoHand2;
    static Servo servoBack;

    public static ElapsedTime elapsedTime;

    static private Thread thread;

    // Key press response
    static double firstWheelPower;
    static double secondWheelPower;

    static double rightStickSignalY;
    static double rightStickSignalX;

    static boolean dpadUpPressed = false;
    static boolean dpadDownPressed = false;
    static boolean isDpadLeftPressed = false;
    static boolean isDpadRightPressed = false;

    static boolean isLeftBumbperPressed = false;

    static boolean xPressed = false;
    static boolean yPressed = false;

    static boolean xWasPressed = false;
    static boolean yWasPressed = false;

    static boolean dead = false;
    static boolean threadRuns = false;

    static boolean isBlackInFrontOfLightSensor;

    double leftStickX;
    double leftStickY;
    double rightStickX;
    double rightStickY;

    double LeftWheelPower;
    double RightWheelPower;

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


        WheelLightSensor = hardwareMap.get(ColorSensor.class, "l");
        WheelLightSensor.enableLed(true);

        motorWheel1 = hardwareMap.get(DcMotor.class, "motor");
        motorWheel2 = hardwareMap.get(DcMotor.class, "motor2");
        motorArm = hardwareMap.get(DcMotor.class, "motor3");

        servoHand1 = hardwareMap.get(Servo.class, "servo");
        servoHand2 = hardwareMap.get(Servo.class, "servo1");

        servoBack = hardwareMap.get(Servo.class, "servo2");

        elapsedTime = new ElapsedTime();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        int goldSoundID   = hardwareMap.appContext.getResources().getIdentifier("gold",   "raw", hardwareMap.appContext.getPackageName());

        // Reset timer
        elapsedTime.reset();



        // ----- Main  Loop -----
        while (opModeIsActive()) {

            //Geting all variables:
            //-----------------------------------------------------


            // Get state of dpad buttons
            dpadUpPressed = this.gamepad1.dpad_up;
            dpadDownPressed = this.gamepad1.dpad_down;
            isDpadLeftPressed = this.gamepad1.dpad_left;
            isDpadRightPressed = this.gamepad1.dpad_right;

            // Get state of sticks
            leftStickX = -this.gamepad1.left_stick_x;
            leftStickY = this.gamepad1.left_stick_y;
            rightStickX = -this.gamepad1.right_stick_x;
            rightStickY = this.gamepad1.right_stick_y;

            // Get state of color buttons
            yPressed = this.gamepad1.y;

            // Get state of servo controller
            isLeftBumbperPressed = this.gamepad1.left_bumper;
            //-----------------------------------------------------

            // Omni wheels control:
            if(isDpadLeftPressed == true) {
                //set front wheels power to -1
            }
            if(isDpadRightPressed == true) {
                //set back wheels power to 1
            }

            // Light sensor work:
            //if(WheelLightSensor)



            LeftWheelPower = leftStickY;
            RightWheelPower = rightStickY;

            /*double v = ((100-Math.abs(leftStickX))*(leftStickY/100)+leftStickY);
            double w = ((100-Math.abs(leftStickY))*(leftStickX/100)+leftStickX);
            LeftWheelPower =Range.clip((v+w)/2,-1,1);
            RightWheelPower =Range.clip((-(v-w)/2),-1,1);

            LeftWheelPower =(v+w)/2;
            RightWheelPower =-(v-w)/2;*/



            motorWheel1.setPower(-RightWheelPower);
            motorWheel2.setPower(LeftWheelPower);

            /*motorWheel1.setPower(Range.clip(RightWheelPower,-1.0, 1.0));
            motorWheel2.setPower(Range.clip(LeftWheelPower,-1.0, 1.0));*/

            //Back srevo code:
            if(this.gamepad1.right_bumper) {
                servoBack.setPosition(0);
            }
            else {
                servoBack.setPosition(0.6);
            }


            /*firstWheelPower = -rightStickY;
            motorWheel1.setPower(firstWheelPower);
            secondWheelPower = -leftStickY;
            motorWheel2.setPower(-secondWheelPower);*/








            // Check if arm buttons pressed and set power
            /*
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
            */

            // Get state of arm buttons but by triggers
            motorArm.setPower((gamepad1.right_trigger - gamepad1.left_trigger)/2);




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
            }

            /*if(dead){
                threadRuns = true;

                DoSmth dsmth = new DoSmth();
                dsmth.start();
            }*/


            telemetry.addData("Dead", dead);
            telemetry.addData("Is \"y\" pressed: ", yPressed);
            telemetry.addData("threadRuns: ", threadRuns);

            //motorArm.setPower((gamepad1.right_trigger - gamepad1.left_trigger)/3);

            telemetry.addData("Rotor Arm: ", motorArm.getPower());
            telemetry.addData("Right trigger: ", gamepad1.right_trigger);
            telemetry.addData("Left trigger: ", gamepad1.left_trigger);

            telemetry.addData("dpad up: ", dpadUpPressed);
            telemetry.addData("dpad down: ", dpadDownPressed);
            telemetry.addData("dpad left: ", isDpadLeftPressed);
            telemetry.addData("dpad right: ", isDpadRightPressed);

            telemetry.addData("Servo Back: ", servoBack.getPosition());
            telemetry.addData("Timer: ", elapsedTime.time());
            telemetry.addData("Status: ", "Running");

            telemetry.addData("Left motor power: ", LeftWheelPower);
            telemetry.addData("Right motor power: ", RightWheelPower);
            telemetry.addData("LeftStickX: ", leftStickX);
            telemetry.addData("LeftStickY: ", leftStickY);
            telemetry.addData("RightStickX: ", rightStickX);
            telemetry.addData("RightStickY: ", rightStickY);

            //temp light sensor debug:
            telemetry.addData("getLightDetected: ", WheelLightSensor.red());

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