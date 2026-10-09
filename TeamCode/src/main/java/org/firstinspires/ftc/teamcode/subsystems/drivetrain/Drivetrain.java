package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.pedropathing.controllers.Controller;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector2D;
import com.pedropathing.math.Velocity;
import com.pedropathing.revhub.drivetrains.Mecanum;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Drivetrain {
    private static Controller pfControl(double kP, double kF) {
        return Controller.sum(Controller.proportionalFeedforward(kF), Controller.proportional(kP));
    }

    public final Follower follower;
    private final Mecanum mecanum;
    private final Robot robot;

    private final Controller forwardController = pfControl(0.03, 0.85);
    private final Controller strafeController = pfControl(0.025, 0.8);
    private final Controller headingController = pfControl(0.25, 0.6);

    public Drivetrain(Robot _robot) {
        robot = _robot;
        follower = Constants.create(robot.hardwareMap);
        mecanum = new Mecanum(robot.hardwareMap, Constants.drivetrainConfig);
    }

    public void driveRobotCentric(double forward, double strafe, double turn) {
        Velocity desired = new Velocity(
                forward * Constants.foresightConfig.maxAchievableForwardVelocity.get(),
                strafe * Constants.foresightConfig.maxAchievableStrafeVelocity.get(),
                turn * 14 * Math.PI); // this isnt the ACTUAL max heading - its super high so that it can rotate while driving

        Velocity current = robotCentricVelocity();
        Velocity errors = desired.minus(current);

        mecanum.drive(new DrivePowers(
                forwardController.calculate(desired.vx, errors.vx),
                strafeController.calculate(desired.vy, errors.vy),
                headingController.calculate(desired.omega, errors.omega)
        ), true);
    }

    private Velocity robotCentricVelocity() {
        Velocity fieldVelocity = follower.velocity();
        Vector2D robotCentricDirection = fieldVelocity.toVector2D().rotate(-follower.pose().heading());
        return new Velocity(robotCentricDirection.x(), robotCentricDirection.y(), fieldVelocity.omega);
    }
}
