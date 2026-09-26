package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.Mecanum;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Drivetrain {
    private final Follower follower;
    private final Mecanum mecanum;

    public Drivetrain(Robot robot) {
        follower = Constants.create(robot.hardwareMap);
        mecanum = new Mecanum(robot.hardwareMap, Constants.drivetrainConfig);
    }

    public void drive(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        DrivePowers powers = new DrivePowers(forwardSpeed, strafeSpeed, turnSpeed);
        mecanum.drive(powers, true);
    }
}
