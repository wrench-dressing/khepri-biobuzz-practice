package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.robot.Robot;

public class Intake {
    DcMotorEx intakeFront;
    public Intake(Robot robot) {
        intakeFront = robot.hardwareMap.get(DcMotorEx.class, "leftintake");
    }
    public void startIntake() {
        intakeFront.setPower(1);
    }
    public void stopIntake() {
        intakeFront.setPower(0);
    }
}
