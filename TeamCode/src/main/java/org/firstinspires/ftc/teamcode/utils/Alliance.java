package org.firstinspires.ftc.teamcode.utils;

public enum Alliance {
    Blue("Blue", Math.PI),
    Red("Red", 0);

    private final String allianceName;
    private final double angleOffset; // for field relative
    public String allianceName() {
        return allianceName;
    }
    public double angleOffset() {
        return angleOffset;
    }

    Alliance(String allianceName, double angleOffset) {
        this.allianceName = allianceName;
        this.angleOffset = angleOffset;
    }
}
