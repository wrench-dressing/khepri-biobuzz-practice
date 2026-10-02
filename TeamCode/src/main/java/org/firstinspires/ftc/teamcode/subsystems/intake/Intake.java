package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.pedropathing.ivy.Command;
import com.pedropathing.revhub.drivetrains.CachedMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robot.Robot;

public class Intake {
    private final CachedMotor intakeFront;
    private final CachedMotor intakeBack;

    public Intake(Robot robot) {
        intakeFront = new CachedMotor(robot.hardwareMap.get(DcMotorEx.class, "leftintake"), 0.1);
        intakeBack = new CachedMotor(robot.hardwareMap.get(DcMotorEx.class, "rightintake"), 0.1);
        intakeBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Command intakeFront() {
        return Command.build()
                .setExecute(() -> intakeFront.setPower(1))
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public Command outtakeThroughFront() {
        return Command.build()
                .setExecute(() -> {
                    intakeFront.setPower(-1);
                    intakeBack.setPower(1);
                })
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public Command intakeBack() {
        return Command.build()
                .setExecute(() -> intakeBack.setPower(1))
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public Command outtakeThroughBack() {
        return Command.build()
                .setExecute(() -> {
                    intakeBack.setPower(-1);
                    intakeFront.setPower(1);
                })
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public void stop() {
        intakeFront.setPower(0);
        intakeBack.setPower(0);
    }
}
