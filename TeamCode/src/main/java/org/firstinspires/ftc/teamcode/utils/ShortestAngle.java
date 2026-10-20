package org.firstinspires.ftc.teamcode.utils;

public class ShortestAngle {
    public static double betweenRadians(double from, double to) {
        double difference = (to - from) % (Math.PI * 2);
        if (difference > Math.PI) difference -= (Math.PI * 2);
        if (difference < -Math.PI) difference += (Math.PI * 2);
        return difference;
    }
}
