package org.firstinspires.ftc.Team16877Code.SkySonesTaking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

import static java.lang.Math.abs;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="BXYX")
public class Blue_Second_Variant extends AutonautsAPI {

    @Override
    public void runOpMode() {

        boolean i = true;
        INIT();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (opModeIsActive()) {
            if(i){
                liftServo(false);
                runRight(0.65, 0.75);
                runForward(0.9, 0.75);
                liftServo(true);
                sleep(200);
                runBack(0.8, 0.85);
                runLeft(1.20, 0.85);
                liftServo(false);
                sleep(200);
                runBack(0.10, 0.85);

                runRight(1.82, 0.85);
                runForward(0.9, 0.85);
                liftServo(true);
                sleep(200);
                runBack(0.8, 0.85);
                runLeft(1.98, 0.85);
                liftServo(false);
                sleep(200);
                runBack(0.10, 0.85);
                runRight(0.35, 0.85);

                i = !i;
            }
        }
    }
}
