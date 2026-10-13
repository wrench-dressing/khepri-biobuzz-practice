package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Vector2D;

import java.util.Arrays;

public enum Cell {
    RedPositive(Vector2D.cartesian(58, 90), new int[]{30, 31, 32, 33}),
    RedNegative(Vector2D.cartesian(58, 50), new int[]{34, 35, 36, 37}),
    BluePositive(Vector2D.cartesian(86, 90), new int[]{42, 43, 44, 45}),
    BlueNegative(Vector2D.cartesian(86, 50), new int[]{38, 39, 40, 41});

    private final Vector2D cellPosition;
    private final int[] fiducials;

    public Vector2D cellPosition() {
        return cellPosition;
    }

    Cell(Vector2D cellPosition, int[] fiducials) {
        this.cellPosition = cellPosition;
        this.fiducials = fiducials;
    }

    public static Cell fromFiducialId(int id) {
        for (Cell cell : Cell.values()) {
            if (Arrays.binarySearch(cell.fiducials, id) >= 0) {
                return cell;
            }
        }

        return null;
    }
}
