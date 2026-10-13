package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Vector2D;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Alliance;

@TeleOp(name = "Some TeleOp")
public class SomeTeleOp extends RobotOpMode {
    protected void opModeInit() {
        controller.rightTrigger.whileTrue(intake.intakeFront());
        controller.leftTrigger.whileTrue(intake.intakeBack());
        controller.rightBumper.whileTrue(intake.outtakeThroughFront());
        controller.leftBumper.whileTrue(intake.outtakeThroughBack());
        controller.west.onTrue(drivetrain.autoTarget());
        controller.back.onTrue(resetHeading());

        controller.start.onTrue(drivetrain.updatePositionFosho(Vector2D.cartesian(7, 7)));

        controller.leftStickButton.onTrue(setAlliance(Alliance.Red));
        controller.rightStickButton.onTrue(setAlliance(Alliance.Blue));
    }

    @Override
    protected void opModeStart() {
        Scheduler.schedule(drivetrain.drive(() -> -gamepad1.left_stick_y, () -> -gamepad1.left_stick_x, () -> -gamepad1.right_stick_x));
    }

    protected void opModeLoop() {
        telemetry.addData("Pose X", Math.round(drivetrain.follower.pose().x()));
        telemetry.addData("Pose Y", Math.round(drivetrain.follower.pose().y()));
        telemetry.addData("Heading", Math.round(drivetrain.follower.pose().heading()));
        telemetry.addData("Loop time (ms)", deltaTime() * 1000);
    }
}
