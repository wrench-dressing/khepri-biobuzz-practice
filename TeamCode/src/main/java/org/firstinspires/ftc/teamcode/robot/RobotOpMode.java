package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.vision.Vision;
import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.Cell;
import org.firstinspires.ftc.teamcode.utils.CommandController;

public abstract class RobotOpMode extends LinearOpMode {
    public static Alliance alliance = Alliance.Red;

    public Drivetrain drivetrain;
    public Intake intake;
    public Vision vision;
    public IMU imu;

    private double _deltaTime = 0;
    private final ElapsedTime deltaTimeTimer = new ElapsedTime();

    public double deltaTime() {
        return _deltaTime;
    }

    protected CommandController controller;

    protected abstract void opModeInit();

    protected abstract void opModeInitLoop();

    protected abstract void opModeStart();

    protected abstract void opModeLoop();

    protected Command resetHeading() {
        return Commands.instant(() -> {
            drivetrain.resetHeading();
            imu.resetYaw();
        });
    }

    protected Command setAlliance(Alliance newAlliance) {
        return Commands.instant(() -> alliance = newAlliance);
    }

    /// Gets the current cell we want to aim at based on where the robot is on the field and the
    /// current alliance
    protected Cell getTargetCell() {
        boolean positiveSide = drivetrain.pose().y() > 72;
        if (alliance == Alliance.Red) {
            return positiveSide ? Cell.RedPositive : Cell.RedNegative;
        } else {
            return positiveSide ? Cell.BluePositive : Cell.BlueNegative;
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Scheduler.reset();
        deltaTimeTimer.reset();
        Scheduler.schedule(Commands.infinite(() -> {
            _deltaTime = deltaTimeTimer.seconds();
            deltaTimeTimer.reset();
        }));

        imu = hardwareMap.get(IMU.class, "imu");
        drivetrain = new Drivetrain(this);
        vision = new Vision(this);
        intake = new Intake(this);

        controller = new CommandController(gamepad1);

        opModeInit();

        while (opModeInInit()) {
            opModeInitLoop();
            Scheduler.execute();
            telemetry.update();
        }

        waitForStart();
        opModeStart();

        while (opModeIsActive()) {
            opModeLoop();
            Scheduler.execute();
            telemetry.update();
        }
    }
}
