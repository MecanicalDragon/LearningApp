package net.medrag.something.unsafe;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author Stanislav Tretyakov
 * 01.11.2021
 */
public class VarHandleExample {

    public static final String SEPARATOR = "_____________________";

    @SneakyThrows
    public static void main(String[] args) {
        instantiateHolder();
        instantiateVarHandle();
    }

    /**
     * VarHandle methods allow access to variables under specific memory ordering effects.
     * <p>
     * For most of the methods there are 4 memory ordering effects:
     * <p>
     * Plain reads and writes guarantee bitwise atomicity for references and primitives under 32 bits.
     * Also, they impose no ordering constraints with respect to the other traits.
     * Opaque operations are bitwise atomic and coherently ordered with respect to access to the same variable.
     * Acquire and Release operations obey Opaque properties. Also, Acquire reads will be ordered only after matching Release mode writes.
     * Volatile operations are fully ordered with respect to each other.
     * <p>
     * It's very important to remember that access modes will override previous memory ordering effects.
     * This means that, for example, if we use get(), it will be a plain read operation, even if we declared our variable as volatile.
     */
    private static void instantiateVarHandle() throws NoSuchFieldException, IllegalAccessException {

        VarHandle pu = MethodHandles
                .lookup()
                .in(VarHandleVoodooDoll.class)
                .findVarHandle(VarHandleVoodooDoll.class, "puVar", int.class);

        VarHandle pr = MethodHandles
                .privateLookupIn(VarHandleVoodooDoll.class, MethodHandles.lookup())
                .findVarHandle(VarHandleVoodooDoll.class, "priVar", int.class);

        VarHandle b = MethodHandles
                .lookup()
                .in(VarHandleVoodooDoll.class)
                .findVarHandle(VarHandleVoodooDoll.class, "bVar", byte.class);

        VarHandleVoodooDoll doll = new VarHandleVoodooDoll();

        pu.set(doll, 3);
        System.out.println(pu.get(doll));

        pr.set(doll, 13);
        System.out.println(pr.get(doll));

        byte before = (byte) b.getAndBitwiseOr(doll, (byte) 127);
        System.out.printf("was %s, became %s%n", before, b.get(doll));

        VarHandle arrayVarHandle = MethodHandles.arrayElementVarHandle(int[].class);
        System.out.println(arrayVarHandle.coordinateTypes().size());
        System.out.println(arrayVarHandle.coordinateTypes());
        System.out.println(arrayVarHandle.coordinateTypes().get(0));
        System.out.println(arrayVarHandle.coordinateTypes().get(1));
    }

    private static void instantiateHolder() throws NoSuchFieldException, IllegalAccessException {
        VarHandleHolder<String> holder = new VarHandleHolder<>();

        VarHandle vh = MethodHandles
                .privateLookupIn(VarHandleHolder.class, MethodHandles.lookup())
                .findVarHandle(VarHandleHolder.class, "value", Object.class);

        System.out.println(holder.get());
        vh.set(holder, "FIRST");
        System.out.println(holder.getAndSet("SECOND"));
        System.out.println(vh.get(holder));
        System.out.println(holder.get());

        System.out.println(SEPARATOR);
    }
}
