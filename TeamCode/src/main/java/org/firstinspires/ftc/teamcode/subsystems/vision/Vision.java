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
    public static class SeenCell {
        public Cell cell;
        public double tx;

        private SeenCell(Cell _cell, double _tx) {
            cell = _cell;
            tx = _tx;
        }

        @NonNull
        public String toString() {
            return cell + "(tx: " + Math.round(tx * 10) / 10 + ")";
        }

        private static SeenCell fromFiducial(LLResultTypes.FiducialResult fiducialResult) {
            Cell cell = Cell.fromFiducialId(fiducialResult.getFiducialId());
            if (cell == null) return null;
            return new SeenCell(cell, fiducialResult.getTargetXDegrees());
        }
    }

    private final Limelight3A limelight;
    private final Telemetry telemetry;

    public Vision(RobotOpMode robot) {
        limelight = robot.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(20);
        limelight.start();
        telemetry = robot.telemetry;
    }

    public List<SeenCell> cellsICanSee() {
        LLResult result = limelight.getLatestResult();
        return result.getFiducialResults()
                .stream()
                .map(SeenCell::fromFiducial)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
