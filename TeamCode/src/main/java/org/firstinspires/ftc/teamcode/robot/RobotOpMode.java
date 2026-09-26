package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class RobotOpMode extends LinearOpMode {
    protected Robot robot;

    protected abstract void opModeInit();

    protected abstract void opModeLoop();

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        opModeInit();
        waitForStart();
        while (opModeIsActive()) {
            opModeLoop();
            telemetry.update();
            Scheduler.execute();
        }
    }
}
