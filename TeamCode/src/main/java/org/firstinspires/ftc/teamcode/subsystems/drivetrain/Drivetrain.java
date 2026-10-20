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
import org.firstinspires.ftc.teamcode.utils.ShortestAngle;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Drivetrain {
    private final Follower follower;

    public Drivetrain(RobotOpMode robot) {
        follower = Constants.create(robot.hardwareMap);
        Scheduler.schedule(Commands.infinite(follower::update));
    }

    /// Resets the robot's heading
    public void resetHeading() {
        follower.localizer.setHeading(0);
        follower.manual(0, 0, 0);
    }

    /// The robot's position on the field
    public Pose pose() {
        return follower.pose();
    }

    /// Sets the current robot's position on the field to a pose
    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public CommandBuilder follow(Path path) {
        return PedroCommands.follow(follower, path);
    }

    /// Sets the current robot's position on the field to a pose, clears its hold state, and maintains its heading
    public Command updatePositionForSure(Vector2D position) {
        return Commands.instant(() -> {
            follower.setPose(position.toPose(follower.localizer.pose().heading()));
            follower.manual(0, 0, 0); // clears hold state
        });
    }

    /// Gets the angle needed to turn by to point to a cell
    public double turnAngleToCell(Cell cell) {
        Pose currentPose = pose();
        Vector2D cellPosition = cell.cellPosition();
        Vector2D robotPosition = currentPose.toVector2D();
        double requiredAngle = cellPosition.minus(robotPosition).theta();
        double currentAngle = currentPose.heading();
        return ShortestAngle.betweenRadians(currentAngle, requiredAngle);
    }

    /// Drive
    public Command drive(DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier turn, Supplier<Cell> whatToAutotargetTo) {
        return Commands.infinite(() -> {
            Cell cell = whatToAutotargetTo.get();
            double turnPower = (cell != null)
                    ? turnAngleToCell(cell) / Math.PI
                    : turn.getAsDouble();

            ManualDrive.driveOrHold(
                    follower,
                    ManualDrive.fieldCentric(
                            forward.getAsDouble(),
                            strafe.getAsDouble(),
                            turnPower,
                            follower.pose().heading()
                    )
            );


        }).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }
}
