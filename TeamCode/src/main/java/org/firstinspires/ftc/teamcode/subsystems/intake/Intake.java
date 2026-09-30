package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.pedropathing.ivy.Command;
import com.pedropathing.revhub.drivetrains.CachedMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.robot.Robot;

public class Intake {
    private final CachedMotor intakeFront;

    public Intake(Robot robot) {
        intakeFront = new CachedMotor(robot.hardwareMap.get(DcMotorEx.class, "leftintake"), 0.1);
    }

    public Command intake() {
        return Command.build()
                .setExecute(() -> intakeFront.setPower(1))
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public Command outtake() {
        return Command.build()
                .setExecute(() -> intakeFront.setPower(-1))
                .setEnd((endCondition) -> stop())
                .requiring(this);
    }

    public void stop() {
        intakeFront.setPower(0);
    }
}
