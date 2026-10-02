package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

public class Robot {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public Drivetrain drivetrain;
    public Intake intake;

    private double _deltaTime = 0;
    private final ElapsedTime deltaTimeTimer = new ElapsedTime();
    public double deltaTime() {
        return _deltaTime;
    }

    public Robot(HardwareMap _hardwareMap, Telemetry _telemetry) {
        hardwareMap = _hardwareMap;
        telemetry = _telemetry;

        deltaTimeTimer.reset();
        Scheduler.schedule(Commands.infinite(() -> {
            _deltaTime = deltaTimeTimer.seconds();
            deltaTimeTimer.reset();
        }));

        drivetrain = new Drivetrain(this);
        intake = new Intake(this);
    }
}
