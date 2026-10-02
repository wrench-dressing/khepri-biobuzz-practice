package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.ivy.groups.Groups;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class CommandController {
    public static class Trigger {
        private boolean current = false;
        private boolean previous = false;
        private boolean rising = false;
        private boolean falling = false;

        private final ArrayList<Runnable> bindings;

        public Trigger(BooleanSupplier buttonState) {
            bindings = new ArrayList<>();

            Scheduler.schedule(Commands.infinite(() -> {
                previous = current;
                current = buttonState.getAsBoolean();

                rising = current && !previous;
                falling = previous && !current;

                for (Runnable fun : bindings) {
                    fun.run();
                }
            }));
        }

        public void whileTrue(Command command) {
            bindings.add(() -> {
                if (rising) {
                    Scheduler.schedule(Groups.loop(command).until(() -> falling));
                }
            });
        }

        public void onTrue(Command command) {
            bindings.add(() -> {
                if (rising) {
                    Scheduler.schedule(command);
                }
            });
        }

        public void whileFalse(Command command) {
            bindings.add(() -> {
                if (falling) {
                    Scheduler.schedule(Groups.loop(command).until(() -> rising));
                }
            });

            if (!previous && !current) {
                Scheduler.schedule(Groups.loop(command).until(() -> rising));
            }
        }

        public void onFalse(Command command) {
            bindings.add(() -> {
                if (falling) {
                    Scheduler.schedule(command);
                }
            });
        }
    }

    private Gamepad gamepad;

    // these lambdas aren't called until the scheduler executes next, which can't happen until
    // after the constructor is done (which will initialize this.gamepad) so it's not a problem.
    public Trigger south = new Trigger(() -> gamepad.a);
    public Trigger east = new Trigger(() -> gamepad.b);
    public Trigger west = new Trigger(() -> gamepad.x);
    public Trigger north = new Trigger(() -> gamepad.y);

    public Trigger dpadLeft = new Trigger(() -> gamepad.dpad_left);
    public Trigger dpadRight = new Trigger(() -> gamepad.dpad_right);
    public Trigger dpadUp = new Trigger(() -> gamepad.dpad_up);
    public Trigger dpadDown = new Trigger(() -> gamepad.dpad_down);

    public Trigger leftTrigger = new Trigger(() -> gamepad.left_trigger_pressed);
    public Trigger rightTrigger = new Trigger(() -> gamepad.right_trigger_pressed);

    public Trigger rightBumper = new Trigger(() -> gamepad.right_bumper);
    public Trigger leftBumper = new Trigger(() -> gamepad.left_bumper);

    public Trigger start = new Trigger(() -> gamepad.start);
    public Trigger back = new Trigger(() -> gamepad.back);


    public CommandController(Gamepad gamepad) {
        this.gamepad = gamepad;
    }
}
