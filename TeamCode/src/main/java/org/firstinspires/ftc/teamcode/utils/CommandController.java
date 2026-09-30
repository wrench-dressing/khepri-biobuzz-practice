package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class CommandController {
    public static class Trigger {
        private final ArrayList<Runnable> functions;

        // passed to functions
        private boolean current = false;
        private boolean previous = false;

        public Trigger(BooleanSupplier get) {
            functions = new ArrayList<>();

            Scheduler.schedule(Commands.infinite(() -> {
                previous = current;
                current = get.getAsBoolean();

                for (Runnable f : functions) {
                    f.run();
                }
            }));
        }

        public void whileTrue(Command command) {
            functions.add(() -> {
                if (current && !previous) {
                    Scheduler.schedule(command.until(() -> !current));
                }
            });
        }
    }

    private Gamepad gamepad;
    public Trigger a = new Trigger(() -> gamepad.a);
    public Trigger b = new Trigger(() -> gamepad.b);
    public Trigger x = new Trigger(() -> gamepad.x);
    public Trigger y = new Trigger(() -> gamepad.y);
    public Trigger leftTrigger = new Trigger(() -> gamepad.left_trigger_pressed);
    public Trigger rightTrigger = new Trigger(() -> gamepad.right_trigger_pressed);
    public Trigger leftBumper = new Trigger(() -> gamepad.left_bumper);
    public Trigger rightBumper = new Trigger(() -> gamepad.right_bumper);
    public Trigger select = new Trigger(() -> gamepad.back);
    public Trigger start = new Trigger(() -> gamepad.options);

    public CommandController(Gamepad _gamepad) {
        gamepad = _gamepad;
    }
}
