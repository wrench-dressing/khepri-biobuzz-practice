package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Alliance;

public abstract class AutoOpMode extends RobotOpMode {
    protected PoseFactory poseFactory = PoseFactory.degrees();

    abstract Pose startPose();

    abstract Command autoRoutine();

    @FunctionalInterface
    protected interface LazyPose {
        Pose get();
    }

    @Override
    protected void opModeInit() {
        drivetrain.follower.update();

        controller.dpadLeft.onTrue(setAlliance(Alliance.Red));
        controller.dpadRight.onTrue(setAlliance(Alliance.Blue));
    }

    @Override
    protected void opModeInitLoop() {
        telemetry.addData("selected alliance", alliance.allianceName());
    }

    @Override
    protected void opModeStart() {
        if (alliance == Alliance.Blue) {
            poseFactory = poseFactory.mirrorX(72);
        }

        drivetrain.follower.setPose(startPose());
        Scheduler.schedule(autoRoutine());
    }

    @Override
    protected void opModeLoop() {
        telemetry.addData("X", drivetrain.follower.pose().x());
        telemetry.addData("Y", drivetrain.follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(drivetrain.follower.pose().heading()));
        telemetry.addData("Follower Mode", drivetrain.follower.mode());
    }
}
