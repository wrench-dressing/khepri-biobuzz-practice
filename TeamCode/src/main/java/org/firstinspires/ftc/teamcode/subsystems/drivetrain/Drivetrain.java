package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Velocity;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

public class Drivetrain {
    public final Follower follower;
    public Drivetrain(RobotOpMode robot) {
        follower = Constants.create(robot.hardwareMap);
    }

    public void driveRobotCentric(double forward, double strafe, double turn) {
        ManualDrive.driveOrHold(follower, forward, strafe, turn);
    }

    public void driveFieldCentric(double forward, double strafe, double turn) {
        DrivePowers powers = ManualDrive.fieldCentric(forward, strafe, turn, follower.pose().heading());
        ManualDrive.driveOrHold(follower, powers);
    }
}
