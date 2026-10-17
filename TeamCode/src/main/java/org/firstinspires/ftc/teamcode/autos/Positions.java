package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.PoseFactory;

import org.firstinspires.ftc.teamcode.autos.AutoOpMode.LazyPose;

public class Positions {
    private PoseFactory poseFactory;

    LazyPose negativeStart = () -> poseFactory.of(54, 7, 90);
    LazyPose negativeShootFar = () -> poseFactory.of(58, 12, 90);

    LazyPose positiveShoot = () -> poseFactory.of(58, 120, -90);

    LazyPose park = () -> poseFactory.of(12, 96, 0);

    LazyPose gardenPickUpStart = () -> poseFactory.of(32, 7, 180);
    LazyPose gardenPickUpEnd = () -> poseFactory.of(7, 7, 180);
    LazyPose gardenLookAtGoal = () -> poseFactory.of(12, 12, 35);

    LazyPose sideFlowerPickupStart = () -> poseFactory.of(34, 47, 180);
    LazyPose sideFlowerPickingUp = () -> poseFactory.of(12, 47, 180);

    LazyPose farFlowerPickupStart = () -> poseFactory.of(47, 110, 90);
    LazyPose farFlowerPickingUp = () -> poseFactory.of(47, 130, 90);

    LazyPose throughCenterPositive = () -> poseFactory.of(66, 90, 90);
    LazyPose throughCenterNegative = () -> poseFactory.of(66, 50, 90);

    public Positions(PoseFactory _poseFactory) {
        poseFactory = _poseFactory;
    }
}
