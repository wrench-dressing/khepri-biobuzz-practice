package org.firstinspires.ftc.teamcode;

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
        controller.back.onTrue(resetHeading());

        controller.leftStickButton.onTrue(setAlliance(Alliance.Red));
        controller.rightStickButton.onTrue(setAlliance(Alliance.Blue));
    }

    protected void opModeLoop() {
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        drivetrain.driveRobotCentric(forward, strafe, turn);

        telemetry.addData("Loop time (ms)", deltaTime() * 1000);
    }

    protected void opModeStart() {

    }
}
