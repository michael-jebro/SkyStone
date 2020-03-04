package org.firstinspires.ftc.Team16877Code.PalletTake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

@Autonomous(name = "Red pallet take")

public class Red_Pallet_Take extends AutonautsAPI {

    @Override
    public void runOpMode()
    {
        INIT();

        boolean i = true;

        while (opModeIsActive()) {
            if(i){
                runLeft(0.23,0.75);
                runBack(0.9,0.75);
                setFoundation(false);
                sleep(500);
                runForward(0.5,0.75);
                turnTo(-90,0.5);
                runLeft(0.6,0.75);
                setFoundation(true);
                runForward(1.2,0.75);
                i = !i;
            }
        }
    }
}
