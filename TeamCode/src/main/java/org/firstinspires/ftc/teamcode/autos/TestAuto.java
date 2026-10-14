package org.firstinspires.ftc.teamcode.autos;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Test Auto")
public class TestAuto extends AutoOpMode {
    private final LazyPose endPose = () -> poseFactory.of(0, 10, 0);

    @Override
    Pose startPose() {
        return poseFactory.of(0, 0, 0);
    }

    @Override
    Command autoRoutine() {
        return race(
                follow(drivetrain.follower, line(startPose(), endPose.get()).linear(startPose(), endPose.get())),
                intake.intakeFront()
        );
    }
}
