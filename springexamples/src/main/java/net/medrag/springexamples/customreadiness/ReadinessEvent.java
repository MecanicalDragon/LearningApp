package net.medrag.springexamples.customreadiness;

import org.springframework.context.ApplicationEvent;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
public final class ReadinessEvent extends ApplicationEvent {

    private enum Readiness {
        UP,
        DOWN
    }

    private final Readiness readiness;

    public static ReadinessEvent readinessUp(Object source) {
        return new ReadinessEvent(source, Readiness.UP);
    }

    public static ReadinessEvent readinessDown(Object source) {
        return new ReadinessEvent(source, Readiness.DOWN);
    }

    private ReadinessEvent(Object source, Readiness readiness) {
        super(source);
        this.readiness = readiness;
    }

    public boolean isUp() {
        return this.readiness == Readiness.UP;
    }

    public boolean isDown() {
        return this.readiness == Readiness.DOWN;
    }
}
