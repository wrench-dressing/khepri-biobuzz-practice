package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

public class Robot {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public Drivetrain drivetrain;

    public Robot(HardwareMap _hardwareMap, Telemetry _telemetry) {
        hardwareMap = _hardwareMap;
        telemetry = _telemetry;

        drivetrain = new Drivetrain(this);
    }
}
