package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Timer;
import java.util.TimerTask;

@Autonomous(name="Stupid infinite loop for robot to turn around")

public class Auto_Run extends LinearOpMode {
    private DcMotor motorTest;
    private DcMotor motorTest2;

    Timer timer = new Timer();

    class TimerAction extends TimerTask{
        public boolean isTime=false;
        @Override
        public void run() {
            this.isTime = true;
            timer.cancel();
        }
    }

    TimerAction timerAction = new TimerAction();



    @Override
    public void runOpMode() {

        motorTest = hardwareMap.get(DcMotor.class, "motor");
        motorTest2 = hardwareMap.get(DcMotor.class, "motor2");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        timer.schedule(timerAction, 3*1000);
        while (opModeIsActive()) {

            if(timerAction.isTime){
                motorTest.setPower(-1);
            }else{
                motorTest.setPower(1);
            }

            telemetry.addData("Current action", "Turning \'round without any purpose");
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}

