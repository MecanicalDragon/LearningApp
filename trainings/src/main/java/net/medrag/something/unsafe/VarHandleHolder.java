package net.medrag.something.unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * We'll use this template if we want to handle varHandles from inside the class.
 *
 * @author Stanislav Tretyakov
 * 01.11.2021
 */
public class VarHandleHolder<V> {

    private static final VarHandle VALUE;

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            VALUE = l.findVarHandle(VarHandleHolder.class, "value", Object.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private volatile V value;

    public final V getAndSet(V newValue) {
        return (V) VALUE.getAndSet(this, newValue);
    }

    public V get() {
        return value;
    }
}
