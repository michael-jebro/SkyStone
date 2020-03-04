package org.firstinspires.ftc.Team16877Code.SkySonesTaking;

//import

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

import static java.lang.Math.abs;

@Autonomous(name="BYXX")
public class Blue_First_Variant extends AutonautsAPI {

    @Override
    public void runOpMode() {

        boolean i = true;
        INIT();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (opModeIsActive()) {
            if(i){
                liftServo(false);
                runRight(0.40,0.85);
                runForward(0.9,0.85);
                liftServo(true);
                sleep(200);
                runBack(0.8,0.85);
                runLeft(0.95,0.85);
                liftServo(false);
                sleep(200);
                runBack(0.10,0.85);

                runRight(1.65,0.85);

                runForward(0.9, 0.85);
                liftServo(true);
                sleep(200);
                runBack(0.8, 0.85);
                runLeft(1.85, 0.85);
                liftServo(false);
                sleep(200);
                runBack(0.10, 0.85);
                runRight(0.35, 0.85);

                i = !i;
            }
        }
    }
}
