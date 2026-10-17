package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.Cell;

import java.util.List;

public abstract class AutoOpMode extends RobotOpMode {
    protected PoseFactory poseFactory = PoseFactory.degrees();
    protected Positions positions = new Positions(poseFactory);

    abstract LazyPose startPose();

    abstract Command autoRoutine();

    @FunctionalInterface
    protected interface LazyPose {
        Pose get();
    }

    /// Whether the target cell is currently visible by the limelight
    protected boolean canSeeTargetCell() {
        Cell target = getTargetCell();
        List<Cell> visible = vision.cellsICanSee();
        return visible.contains(target);
    }

    @Override
    protected void opModeInit() {
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
            poseFactory = poseFactory.mirrorY(72);
            positions = new Positions(poseFactory);
        }

        drivetrain.setPose(startPose().get());
        Scheduler.schedule(autoRoutine());
    }

    @Override
    protected void opModeLoop() {
        telemetry.addData("X", drivetrain.pose().x());
        telemetry.addData("Y", drivetrain.pose().y());
        telemetry.addData("Heading", Math.toDegrees(drivetrain.pose().heading()));
    }

    Command fakeShoot() {
        return Commands.waitMs(2500);
    }

    Path linear(Pose start, Pose end) {
        return Paths.line(start, end).linear(start, end);
    }

    Path curved(Pose... poses) {
        return Paths.curve(poses).tangent();
    }
}
