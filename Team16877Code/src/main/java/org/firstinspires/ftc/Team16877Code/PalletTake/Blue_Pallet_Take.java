package org.firstinspires.ftc.Team16877Code.PalletTake;

import android.webkit.WebHistoryItem;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.Team16877Code.AutonautsAPI;

@Autonomous(name = "Blue pallet take")
public class Blue_Pallet_Take extends AutonautsAPI {

    @Override
    public void runOpMode()
    {
        INIT();

        while (opModeIsActive())
        {
            runLeft(0.3,0.75);
            runForward(1,0.75);
            setFoundation(false);
            sleep(500);
            turnTo(-90,0.75);
            runForward(0.3,075);
            runLeft(0.75,0.75);
        }
    }

}
