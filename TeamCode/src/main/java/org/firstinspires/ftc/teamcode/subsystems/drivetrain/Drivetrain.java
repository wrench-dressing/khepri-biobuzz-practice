package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.math.Vector2D;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Cell;

import java.util.function.DoubleSupplier;

public class Drivetrain {
    public final Follower follower;

    public Drivetrain(RobotOpMode robot) {
        follower = Constants.create(robot.hardwareMap);
    }

    public Command updatePositionFosho(Vector2D position) {
        return Commands.instant(() -> {
            follower.setPose(position.toPose(follower.localizer.pose().heading()));
            follower.manual(0, 0, 0); // clears hold state
        });
    }

    public Command autoTarget() {
        return Commands.infinite(() -> {
            Cell cell = Cell.RedPositive;
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
        );
    }
}
