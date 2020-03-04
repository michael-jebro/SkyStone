package org.firstinspires.ftc.Team16877Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Foundation", group="Linear OpMode")
//@Disabled
public class BasicOpMode_Linear extends LinearOpMode {
    private final double MOTOR_TO_REV_TICKS = 537.6;
    private final ElapsedTime runtime = new ElapsedTime();
    private final double DIAMETR_WHEEL = 3.1415 * 0.075;
    private final double DIAMETR_WHEEL_SIDE = 0.22;
    private Servo foundation1;
    private Servo foundation2;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private void turn90(){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS * -1));
        leftBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS* -1));
        rightFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS*-4.5));
        rightBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS* -4.5));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(1);
        rightBack.setPower(1);
        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){

        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);

    }
    private void turnAroundCenter(double angle){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS * angle * 0.025));
        leftBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS * angle * 0.025));
        rightFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS* angle * -0.025));
        rightBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS* angle * -0.025));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
        rightBack.setPower(0.5);
        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){

        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);

    }
    private void runBack(double meters,double power){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        leftFront.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        leftBack.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        rightFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        rightBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);

        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){
            telemetry.addData("WAITING","GOING");
            telemetry.addData("LF",  leftFront.getCurrentPosition());
            telemetry.addData("RF",  rightFront.getCurrentPosition());
            telemetry.addData("LB",  leftBack.getCurrentPosition());
            telemetry.addData("RB",  rightBack.getCurrentPosition());
            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    private void runLeft(double meters,double power){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        leftFront.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        leftBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        rightFront.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        rightBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(-power);

        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){
            telemetry.addData("WAITING","GOING");
            telemetry.addData("LF",  leftFront.getCurrentPosition());
            telemetry.addData("RF",  rightFront.getCurrentPosition());
            telemetry.addData("LB",  leftBack.getCurrentPosition());
            telemetry.addData("RB",  rightBack.getCurrentPosition());
            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    private void runForward(double meters,double power){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        leftBack.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        rightFront.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));
        rightBack.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL)));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(power);

        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){
            telemetry.addData("WAITING","GOING");
            telemetry.addData("LF",  leftFront.getCurrentPosition());
            telemetry.addData("RF",  rightFront.getCurrentPosition());
            telemetry.addData("LB",  leftBack.getCurrentPosition());
            telemetry.addData("RB",  rightBack.getCurrentPosition());
            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    private void runRight(double meters,double power){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        leftFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        leftBack.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        rightFront.setTargetPosition((int)(MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));
        rightBack.setTargetPosition((int)(-MOTOR_TO_REV_TICKS*(meters/DIAMETR_WHEEL_SIDE)));

        leftFront.setMode  (DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode   (DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode (DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode  (DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(-power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(power);

        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()){
            telemetry.addData("WAITING","GOING");
            telemetry.addData("LF",  leftFront.getCurrentPosition());
            telemetry.addData("RF",  rightFront.getCurrentPosition());
            telemetry.addData("LB",  leftBack.getCurrentPosition());
            telemetry.addData("RB",  rightBack.getCurrentPosition());
            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    private void setFoundation(boolean i){
        if(i){
            foundation1.setPosition(0.05);
            foundation2.setPosition(0.95);
        }else{
            foundation1.setPosition(1);
            foundation2.setPosition(0);
        }
    }

    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "MotorFrontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "MotorFrontRight");
        leftBack = hardwareMap.get(DcMotor.class, "MotorBackLeft");
        rightBack = hardwareMap.get(DcMotor.class, "MotorBackRight");

        foundation1 = hardwareMap.get(Servo.class, "servoFoundation1");
        foundation2 = hardwareMap.get(Servo.class, "servoFoundation2");

        int i = 0;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            if(i == 0){
                runRight(0.5,0.5);
                runBack(0.8,0.5);
                setFoundation(true);
                sleep(200);
                runForward(0.3,0.4);
                turn90();
                setFoundation(false);
                runForward(0.7,0.5);
                i++;
            }
        }
    }
}
