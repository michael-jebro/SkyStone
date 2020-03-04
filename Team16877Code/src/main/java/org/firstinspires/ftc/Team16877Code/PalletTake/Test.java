package org.firstinspires.ftc.Team16877Code.PalletTake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

@Autonomous(name = "!Tests")

public class Test extends AutonautsAPI {

    @Override
    public void runOpMode()
    {
        INIT();

        boolean i = true;

        while (opModeIsActive()) {
            if(i){
                turnTo(-90,0.5);
                sleep(2000);
                turnTo(90,0.5);
                sleep(2000);
                turnTo(0,0.5);
                i = !i;
            }
        }
    }
}
