package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.CommandController;

public abstract class RobotOpMode extends LinearOpMode {
    public static Alliance alliance = Alliance.Red;

    protected Robot robot;
    protected CommandController controller;

    protected abstract void opModeInit();

    protected abstract void opModeStart();

    protected abstract void opModeLoop();

    protected Command resetHeading() {
        return Commands.instant(() -> {
            robot.imu.resetYaw();
            robot.drivetrain.follower.localizer.setHeading(0);
        });
    }

    protected Command setAlliance(Alliance newAlliance) {
        return Commands.instant(() -> {
            alliance = newAlliance;
        });
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Scheduler.reset();
        robot = new Robot(hardwareMap, telemetry);
        controller = new CommandController(gamepad1);

        opModeInit();
        waitForStart();
        opModeStart();

        while (opModeIsActive()) {
            opModeLoop();
            telemetry.update();
            Scheduler.execute();
            robot.drivetrain.follower.update();
        }
    }
}
