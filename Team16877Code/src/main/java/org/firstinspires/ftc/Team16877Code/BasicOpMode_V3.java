package org.firstinspires.ftc.Team16877Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import static java.lang.Math.abs;

@Autonomous(name="Stupod infinity loop")
public class BasicOpMode_V3 extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private final double MOTOR_TO_REV_TICKS = 537.6;
    private final double DIAMETR_WHEEL = 3.1415 * 0.075;
    private final double DIAMETR_WHEEL_SIDE = 0.22;
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private Servo getServo;
    private Servo foundation1;
    private Servo foundation2;
    private static final String VUFORIA_KEY =
            "AULfDAr/////AAABmaG14Stlx0KUk/z+97VHrwMN8kDjuautG+bQKLP9tZX7zsvifrrH9k9MKp59TqnQiDVsqScUuN6BYbTMB/GsFGXvBVsZJlXXZJaOEvfzDLsYecFQYyOIhvfTnge/La2VdXQ35tTpxcjDPm30aQk0SdAaraeJ2VnIfB/iFikUiS5SgNeLBVKLRJexSJjcYaA9nWe0sobMH9VWVj2LGE/Wy8WQ7uZ80VX3/GtqZODwXq4Cy/cSAevox1CEPjSwbZBJ2n0qaAmJZVjTnpxjtzZbkEJNWLq5oqYG9qG+AT+Crkua2hqqPnH9xoITB171SfDUqxITlhL1eev7+NJhk11zn1uyaOAfd8617DHjeCPXM3z3";
    @Override
    public void runOpMode() {

        boolean i = true;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront  = hardwareMap.get(DcMotor.class, "MotorFrontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "MotorFrontRight");
        leftBack   = hardwareMap.get(DcMotor.class, "MotorBackLeft");
        rightBack  = hardwareMap.get(DcMotor.class, "MotorBackRight");

        getServo = hardwareMap.get(Servo.class, "getServo");
        foundation1 = hardwareMap.get(Servo.class, "servoFoundation1");
        foundation2 = hardwareMap.get(Servo.class, "servoFoundation2");


        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            if(i){
                runForward(0.57,0.5);
                runLeftDetectSkyStone(3,0.1);
                sleep(10);
                runRight(0.3,0.5);
                runBack(0.05,0.5);
                runLeft(0.3,0.5);
                i = !i;
            }
        }
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
    private void runRightPos(int pos,double power){
        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        leftFront.setTargetPosition(pos);
        leftBack.setTargetPosition(-pos);
        rightFront.setTargetPosition(pos);
        rightBack.setTargetPosition(-pos);

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
    private void runLeftDetectSkyStone(double meters,double power){
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
            if(skyStoneDetect()){

                rightBack.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(0);
                leftFront.setPower(0);

                int posSkyStone = abs(leftFront.getCurrentPosition());

                leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                getSkyStone();
                runRightPos(posSkyStone,0.5);
                gettingServo(false);

            }
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
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    private boolean skyStoneDetect(){
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            for (Recognition recognition : updatedRecognitions) {
                if(recognition.getLabel().equals(LABEL_SECOND_ELEMENT)) {
                    if (abs((int) ((recognition.getTop() + recognition.getBottom()) / 2) - 627) < 40) {
                        return true;
                    } else if (((recognition.getTop() + recognition.getBottom()) / 2) - 627 >= 40) {
                        return false;
                    } else if (((recognition.getTop() + recognition.getBottom()) / 2) - 627 <= -40) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    private void gettingServo(boolean i){
        if(i) getServo.setPosition(0.75);
        else getServo.setPosition(0);
    }
    private void setFoundation(boolean i){
        if(i){
            foundation1.setPosition(0);
            foundation2.setPosition(1);
        }else{
            foundation1.setPosition(1);
            foundation2.setPosition(0);
        }
    }
    private void getSkyStone(){
        runForward(0.65,0.5);
        sleep(100);
        gettingServo(true);
        sleep(250);
        runBack(0.65,0.5);
    }
}
