package org.firstinspires.ftc.teamcode.autos;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

@Autonomous(name = "Test Auto")
public class TestAuto extends RobotOpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    // Poses
    private final Pose startPose = poseFactory.of(0, 0, 0);
    private final Pose scorePose = poseFactory.of(20, 0, 180);



    // Path methods
    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }

    private Command autoRoutine() {
        return sequential(
                follow(robot.drivetrain.follower, startToScore())
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
