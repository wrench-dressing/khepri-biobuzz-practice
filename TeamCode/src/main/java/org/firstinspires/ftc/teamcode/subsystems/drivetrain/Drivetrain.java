package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.ivy.pedro.PedroCommands;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Cell;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Drivetrain {
    private final Follower follower;

    public Drivetrain(RobotOpMode robot) {
        follower = Constants.create(robot.hardwareMap);
        Scheduler.schedule(Commands.infinite(follower::update));
    }

    public void resetHeading() {
        follower.localizer.setHeading(0);
        follower.manual(0, 0, 0);
    }

    public Pose pose() {
        return follower.pose();
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public CommandBuilder follow(Path path) {
        return PedroCommands.follow(follower, path);
    }

    public Command updatePositionForSure(Vector2D position) {
        return Commands.instant(() -> {
            follower.setPose(position.toPose(follower.localizer.pose().heading()));
            follower.manual(0, 0, 0); // clears hold state
        });
    }

    public Command autoTarget(Supplier<Cell> cellSupplier) {
        return Commands.infinite(() -> {
            Cell cell = cellSupplier.get();
            Vector2D cellPosition = cell.cellPosition();
            Vector2D robotPosition = follower.pose().toVector2D();
            double angleFounding = cellPosition.minus(robotPosition).theta();
            follower.hold(follower.pose().withHeading(angleFounding));
        });
    }

    public Command drive(DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier turn) {
        return Commands.infinite(() ->
                ManualDrive.driveOrHold(
                        follower,
                        ManualDrive.fieldCentric(
                                forward.getAsDouble(),
                                strafe.getAsDouble(),
                                turn.getAsDouble(),
                                follower.pose().heading()
                        )
                )
        ).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }
}
