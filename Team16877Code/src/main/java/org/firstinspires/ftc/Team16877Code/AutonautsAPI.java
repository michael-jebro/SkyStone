package org.firstinspires.ftc.Team16877Code;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.getExponent;

public class AutonautsAPI extends LinearOpMode {

    // imu init start
    public BNO055IMU imu = null;
    public Orientation lastAngles = new Orientation();
    public double globalAngle, power = .30, correction;

    //imu init end

    static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    static final String LABEL_FIRST_ELEMENT = "Stone";
    static final String LABEL_SECOND_ELEMENT = "Skystone";
    static final double MOTOR_TO_REV_TICKS = 537.6;
    static final double DIAMETR_WHEEL = 3.1415 * 0.075;
    static final double DIAMETR_WHEEL_SIDE = 0.22;
    static final ElapsedTime runtime = new ElapsedTime();
    static DcMotor leftFront;
    static DcMotor rightFront;
    static DcMotor leftBack;
    static DcMotor rightBack;
    static VuforiaLocalizer vuforia;
    static TFObjectDetector tfod;
    static Servo liftservo;
    static Servo foundation1;
    static Servo foundation2;
    static final String VUFORIA_KEY =
            "AULfDAr/////AAABmaG14Stlx0KUk/z+97VHrwMN8kDjuautG+bQKLP9tZX7zsvifrrH9k9MKp59TqnQiDVsqScUuN6BYbTMB/GsFGXvBVsZJlXXZJaOEvfzDLsYecFQYyOIhvfTnge/La2VdXQ35tTpxcjDPm30aQk0SdAaraeJ2VnIfB/iFikUiS5SgNeLBVKLRJexSJjcYaA9nWe0sobMH9VWVj2LGE/Wy8WQ7uZ80VX3/GtqZODwXq4Cy/cSAevox1CEPjSwbZBJ2n0qaAmJZVjTnpxjtzZbkEJNWLq5oqYG9qG+AT+Crkua2hqqPnH9xoITB171SfDUqxITlhL1eev7+NJhk11zn1uyaOAfd8617DHjeCPXM3z3";

    @Override
    public void runOpMode()
    {

    }


    public void INIT(){

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode               = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class,"imu");

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".


        telemetry.addData("1",true);
        telemetry.update();
        imu.initialize(parameters);
        telemetry.addData("2",true);
        telemetry.update();

        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        leftFront  = hardwareMap.get(DcMotor.class, "MotorFrontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "MotorFrontRight");
        leftBack   = hardwareMap.get(DcMotor.class, "MotorBackLeft");
        rightBack  = hardwareMap.get(DcMotor.class, "MotorBackRight");

        liftservo = hardwareMap.get(Servo.class, "liftServo");
        foundation1 = hardwareMap.get(Servo.class, "servoFoundation1");
        foundation2 = hardwareMap.get(Servo.class, "servoFoundation2");

        leftFront.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode (DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftservo.setPosition(0.8);

        foundation1.setPosition(0);
        foundation2.setPosition(1);

        waitForStart();
        runtime.reset();
    }

    public void runForward(double meters,double power){
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
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void runRight(double meters,double power){
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
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void runRightPos(int pos,double power){
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
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void runLeft(double meters,double power){
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
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void runLeftDetectSkyStone(double meters,double power){
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
                liftServo(false);

            }
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void runBack(double meters,double power){
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
//            telemetry.addData("WAITING","GOING");
//            telemetry.addData("LF",  leftFront.getCurrentPosition());
//            telemetry.addData("RF",  rightFront.getCurrentPosition());
//            telemetry.addData("LB",  leftBack.getCurrentPosition());
//            telemetry.addData("RB",  rightBack.getCurrentPosition());
//            telemetry.update();
        }
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);


    }
    public void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    public boolean skyStoneDetect(){
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
    public void liftServo(boolean i){
        if(i) liftservo.setPosition(0.8);
        else liftservo.setPosition(0.3);
    }
    public void setFoundation(boolean i){
        if(i){
            foundation1.setPosition(0);
            foundation2.setPosition(1);
        }else{
            foundation1.setPosition(1);
            foundation2.setPosition(0);
        }
    }
    public void getSkyStone(){
        runForward(0.65,0.5);
        sleep(100);
        liftServo(true);
        sleep(250);
        runBack(0.65,0.5);
    }
    public void turnTo(int wantAngle, double power) {

        leftFront.setMode  (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode   (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode  (DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (Math.abs(Math.abs(wantAngle) - Math.abs(getAngle())) > 3)
        {
            telemetry.addData("Want Angle", wantAngle);
            telemetry.addData("Angle", getAngle());
            telemetry.update();

//            leftFront.setPower(((Math.abs(wantAngle) - Math.abs(getAngle())) / 45)*power + 0.05);
//            rightFront.setPower(((Math.abs(wantAngle) - Math.abs(getAngle())) / 45)*power + 0.05);

            if (wantAngle < getAngle()) {
                if (Math.abs(Math.abs(wantAngle) - Math.abs(getAngle())) > 20){
                    leftFront.setPower(power);
                    rightFront.setPower(power);
                } else {
                    leftFront.setPower(power/2.5);
                    rightFront.setPower(power/2.5);
                }
            } else {
                if (Math.abs(Math.abs(wantAngle) - Math.abs(getAngle())) > 20){
                    leftFront.setPower(-power);
                    rightFront.setPower(-power);
                } else {
                    leftFront.setPower(-power/2.5);
                    rightFront.setPower(-power/2.5);
                }
            }
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }
    public double getAngle() {

        Orientation angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }
}
