package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.ivy.groups.Groups;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Negative To Positive")
public class NegativeToPositive extends AutoOpMode {
    private boolean didTipFirst = false;

    @Override
    LazyPose startPose() {
        return positions.negativeStart;
    }

    @Override
    Command autoRoutine() {
        return Groups.sequential(
                drivetrain.follow(
                        linear(
                                startPose().get(),
                                positions.negativeShootFar.get())),
                fakeShoot(),
                drivetrain.follow(
                        curved(
                                positions.negativeShootFar.get(),
                                positions.gardenPickUpStart.get(),
                                positions.gardenPickUpEnd.get())),
                Commands.waitMs(250),
                drivetrain.follow(
                        linear(
                                positions.gardenPickUpEnd.get(),
                                positions.gardenLookAtGoal.get())),
                Commands.waitMs(500),
                Commands.instant(() -> didTipFirst = !canSeeTargetCell()),
                Commands.conditional(
                        () -> didTipFirst,
                        directlyToPositiveShoot(),
                        fallbackNegativeShoot()
                ),
                // at this point, we are at positive shoot
                fakeShoot(),
                drivetrain.follow(
                        curved(
                                positions.positiveShoot.get(),
                                positions.farFlowerPickupStart.get(),
                                positions.farFlowerPickingUp.get())),
                Commands.waitMs(1500),
                drivetrain.follow(
                        curved(
                                positions.farFlowerPickingUp.get(),
                                positions.farFlowerPickupStart.get(),
                                positions.positiveShoot.get()
                        ).linear(positions.farFlowerPickingUp.get(), positions.positiveShoot.get())),
                fakeShoot(),
                Commands.conditional(
                        () -> didTipFirst,
                        Groups.sequential(
                                drivetrain.follow(
                                        curved(
                                                positions.positiveShoot.get(),
                                                positions.park.get(),
                                                positions.sideFlowerPickupStart.get(),
                                                positions.sideFlowerPickingUp.get())),
                                Commands.waitMs(1500),
                                drivetrain.follow(
                                        curved(
                                                positions.sideFlowerPickingUp.get(),
                                                positions.sideFlowerPickupStart.get(),
                                                positions.park.get()))
                        ),
                        Commands.instant(() -> {
                        })
                ),
                drivetrain.follow(
                        linear(
                                positions.positiveShoot.get(),
                                positions.park.get()))
        );
    }

    Command directlyToPositiveShoot() {
        return drivetrain.follow(
                Paths.curve(
                        positions.gardenLookAtGoal.get(),
                        positions.negativeShootFar.get(),
                        positions.throughCenterNegative.get(),
                        positions.throughCenterPositive.get(),
                        positions.positiveShoot.get()
                ).linear(positions.gardenLookAtGoal.get(), positions.positiveShoot.get())
        );
    }

    Command fallbackNegativeShoot() {
        return Groups.sequential(
                // shoot from garden
                drivetrain.follow(
                        linear(
                                positions.gardenLookAtGoal.get(),
                                positions.negativeShootFar.get())),
                fakeShoot(),
                drivetrain.follow(
                        curved(
                                positions.negativeShootFar.get(),
                                positions.sideFlowerPickupStart.get(),
                                positions.sideFlowerPickingUp.get())),
                Commands.waitMs(1500),
                drivetrain.follow(
                        curved(
                                positions.sideFlowerPickingUp.get(),
                                positions.sideFlowerPickupStart.get(),
                                positions.park.get(),
                                positions.positiveShoot.get()
                        ).linear(positions.sideFlowerPickingUp.get(), positions.positiveShoot.get()))
        );
    }
}
