package org.firstinspires.ftc.teamcode.subsystems.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.robot.RobotOpMode;
import org.firstinspires.ftc.teamcode.utils.Cell;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Vision {
    private final Limelight3A limelight;

    public Vision(RobotOpMode robot) {
        limelight = robot.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(20);
        limelight.start();
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
