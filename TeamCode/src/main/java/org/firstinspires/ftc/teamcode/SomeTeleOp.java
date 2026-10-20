package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Vector2D;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Alliance;

@TeleOp(name = "Some TeleOp")
public class SomeTeleOp extends RobotOpMode {

    @Override
    protected void opModeInit() {
        controller.rightTrigger.whileTrue(intake.intakeFront());
        controller.leftTrigger.whileTrue(intake.intakeBack());
        controller.rightBumper.whileTrue(intake.outtakeThroughFront());
        controller.leftBumper.whileTrue(intake.outtakeThroughBack());
        controller.back.onTrue(resetHeading());

        controller.start.onTrue(drivetrain.updatePositionForSure(Vector2D.cartesian(7, 137)));

        controller.leftStickButton.onTrue(setAlliance(Alliance.Red));
        controller.rightStickButton.onTrue(setAlliance(Alliance.Blue));
    }

    @Override
    protected void opModeInitLoop() {

    }

    @Override
    protected void opModeStart() {
        Scheduler.schedule(
                drivetrain.drive(
                        () -> -gamepad1.left_stick_y,
                        () -> -gamepad1.left_stick_x,
                        () -> -gamepad1.right_stick_x,
                        () -> gamepad1.x ? getTargetCell() : null
                )
        );
    }

    @Override
    protected void opModeLoop() {
        telemetry.addData("target", getTargetCell());
        telemetry.addData("Pose X", Math.round(drivetrain.pose().x()));
        telemetry.addData("Pose Y", Math.round(drivetrain.pose().y()));
        telemetry.addData("Heading", Math.round(drivetrain.pose().heading()));
        telemetry.addData("Loop time (ms)", deltaTime() * 1000);
    }
}
