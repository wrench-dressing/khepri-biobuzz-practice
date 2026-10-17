package org.firstinspires.ftc.teamcode.autos;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.groups.Groups.race;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Test Auto")
public class TestAuto extends AutoOpMode {
    private final LazyPose endPose = () -> poseFactory.of(0, 10, 0);

    @Override
    LazyPose startPose() {
        return () -> poseFactory.of(0, 0, 0);
    }

    @Override
    Command autoRoutine() {
        return race(
                drivetrain.follow(line(startPose().get(), endPose.get()).linear(startPose().get(), endPose.get())),
                intake.intakeFront()
        );
    }
}
