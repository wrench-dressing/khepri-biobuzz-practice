package org.firstinspires.ftc.teamcode.subsystems.vision;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Cell;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Vision {
    private final Limelight3A limelight;
    private final Telemetry telemetry;

    public Vision(RobotOpMode robot) {
        limelight = robot.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(20);
        limelight.start();
        telemetry = robot.telemetry;
    }

    public List<Cell> cellsICanSee() {
        LLResult result = limelight.getLatestResult();
        return result.getFiducialResults()
                .stream()
                .map(aprilTag -> Cell.fromFiducialId(aprilTag.getFiducialId()))
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
