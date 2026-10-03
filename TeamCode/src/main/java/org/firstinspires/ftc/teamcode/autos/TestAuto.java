package org.firstinspires.ftc.teamcode.autos;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

@Autonomous(name = "Test Auto")
public class TestAuto extends RobotOpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    // Poses
    private final Pose startPose = PoseFactory.degrees().of(0,0,0);
    private final Pose endPose = PoseFactory.degrees().of(30,0,0);
    private Command autoRoutine() {
        return race(
                follow(robot.drivetrain.follower, line(startPose, endPose).linear(startPose,endPose)),
                robot.intake.intakeFront()
        );
    }

    @Override
    protected void opModeInit() {
        robot.drivetrain.follower.setPose(startPose);
        robot.drivetrain.follower.update();
    }

    @Override
    protected void opModeStart() {
        schedule(autoRoutine());
    }

    @Override
    protected void opModeLoop() {
        // add your other methods needed in the loop here
        telemetry.addData("X", robot.drivetrain.follower.pose().x());
        telemetry.addData("Y", robot.drivetrain.follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(robot.drivetrain.follower.pose().heading()));
        telemetry.addData("Follower Mode", robot.drivetrain.follower.mode());
    }
}
