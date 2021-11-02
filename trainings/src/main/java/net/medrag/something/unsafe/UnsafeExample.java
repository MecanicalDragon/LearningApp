package net.medrag.something.unsafe;

import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Stanislav Tretyakov
 * 31.10.2021
 */
public class UnsafeExample {

    private static Unsafe UNSAFE;
    private static String SEPARATOR = "__________________";

    @SneakyThrows
    public static void main(String[] args) {
        obtainUnsafeInstance();
        createObjectWithUnsafe();
        changePrivateField();
        wrapThrownException();
        allocateOffHeapMemory();
    }

    @SneakyThrows
    private static void allocateOffHeapMemory() {
        long superSize = (long) Integer.MAX_VALUE * 2;
        OffHeapArray array = new OffHeapArray(superSize);
        for (int i = 0; i < 100; i++) {
            array.set((long) Integer.MAX_VALUE + i, (byte) 3);
        }

        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += array.get((long) Integer.MAX_VALUE + i);
        }
        System.out.println(sum);
        System.out.println(SEPARATOR);
    }

    private static void wrapThrownException() {
        try {
            throwCheckedException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(SEPARATOR);
    }

    private static void throwCheckedException() {
        UNSAFE.throwException(new Exception("WOW!!"));
    }

    @SneakyThrows
    private static void changePrivateField() {
        final UnsafeVoodooDoll unsafeVoodooDoll = new UnsafeVoodooDoll();
        final Field field = UnsafeVoodooDoll.class.getDeclaredField("integer");
        field.setAccessible(true);
        field.setInt(unsafeVoodooDoll, 5);
        System.out.println(unsafeVoodooDoll);
        UNSAFE.putInt(unsafeVoodooDoll, UNSAFE.objectFieldOffset(field), 100);
        System.out.println(unsafeVoodooDoll);
        System.out.println(SEPARATOR);
    }

    private static void createObjectWithUnsafe() throws InstantiationException {
        UnsafeVoodooDoll d = new UnsafeVoodooDoll();
        UnsafeVoodooDoll d2 = (UnsafeVoodooDoll) UNSAFE.allocateInstance(UnsafeVoodooDoll.class);
        System.out.println(d);
        System.out.println(d2);
        System.out.println(SEPARATOR);
    }

    private static void obtainUnsafeInstance() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        UNSAFE = (Unsafe) f.get(null);
        System.out.println(UNSAFE);
        System.out.println(SEPARATOR);
    }
}
