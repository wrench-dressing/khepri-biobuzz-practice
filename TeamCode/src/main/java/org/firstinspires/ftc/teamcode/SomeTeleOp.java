package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

@TeleOp(name = "Some TeleOp")
public class SomeTeleOp extends RobotOpMode {
    protected void opModeInit() {

    }

    protected void opModeLoop() {
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        robot.drivetrain.drive(forward, strafe, turn);
        if (gamepad1.aWasPressed()) {
            robot.intake.startIntake();
        } else if (gamepad1.aWasReleased()) {
            robot.intake.stopIntake();
        }
    }

    protected void opModeStart() {

    }
}
