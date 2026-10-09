package org.firstinspires.ftc.teamcode.utils;

public enum Alliance {
    Blue(Math.PI),
    Red(0);

    private double angleOffset; // for field relative
    public double angleOffset() {
        return angleOffset;
    }

    Alliance(double angleOffset) {
        this.angleOffset = angleOffset;
    }

}
