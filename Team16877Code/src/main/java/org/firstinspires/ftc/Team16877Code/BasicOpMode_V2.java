package org.firstinspires.ftc.Team16877Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous V.1")
//@Disabled
public class BasicOpMode_V2 extends LinearOpMode {
    private final double MOTOR_TO_REV_TICKS = 537.6;
    private final double DIAMETR_WHEEL = 3.1415 * 0.075;
    private final double DIAMETR_WHEEL_SIDE = 0.22;
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
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
    @Override
    public void runOpMode() {

        int i = 0;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront  = hardwareMap.get(DcMotor.class, "MotorFrontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "MotorFrontRight");
        leftBack   = hardwareMap.get(DcMotor.class, "MotorBackLeft");
        rightBack  = hardwareMap.get(DcMotor.class, "MotorBackRight");

        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            if(i == 0){

            }
            i++;
        }
    }
}
