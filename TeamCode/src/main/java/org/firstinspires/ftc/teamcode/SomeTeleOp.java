package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

@TeleOp(name = "Some TeleOp")
public class SomeTeleOp extends RobotOpMode {
    protected void opModeInit() {
        controller.rightTrigger.whileTrue(robot.intake.intakeFront());
        controller.leftTrigger.whileTrue(robot.intake.intakeBack());
        controller.rightBumper.whileTrue(robot.intake.outtakeThroughFront());
        controller.leftBumper.whileTrue(robot.intake.outtakeThroughBack());
        controller.back.onTrue(resetHeading());
    }

    protected void opModeLoop() {
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        robot.drivetrain.driveRobotCentric(forward, strafe, turn);

        telemetry.addData("Loop time (ms)", robot.deltaTime() * 1000);
    }

    protected void opModeStart() {

    }
}
