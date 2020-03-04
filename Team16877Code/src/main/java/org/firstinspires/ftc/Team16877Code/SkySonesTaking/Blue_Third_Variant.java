package org.firstinspires.ftc.Team16877Code.SkySonesTaking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

import static java.lang.Math.abs;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="BXXY")
public class Blue_Third_Variant extends AutonautsAPI {

    @Override
    public void runOpMode() {

        boolean i = true;
        INIT();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (opModeIsActive()) {
            if(i){
                liftServo(false);
                runRight(0.90, 0.5);
                runForward(0.9, 0.5);
                liftServo(true);
                sleep(200);
                runBack(0.8, 0.5);
                runLeft(1.47, 0.5);
                liftServo(false);
                sleep(200);
                runBack(0.10, 0.5);
                runRight(0.33, 0.5);
                i = !i;
            }
        }
    }
}
