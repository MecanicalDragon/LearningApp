package net.medrag.something.unsafe;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author Stanislav Tretyakov
 * 31.10.2021
 */
public class MHandleExample {

    @SneakyThrows
    public static void main(String[] args) {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodType publicType = MethodType.methodType(int.class, int.class, Integer.class);
        MethodType defaultType = MethodType.methodType(void.class, int.class);

        // findVirtual
        // findStatic
        // findConstructor
        // findGetter
        MethodHandle publicHandle = publicLookup.findVirtual(MHandleVoodooDoll.class, "getPublic", publicType);
        MethodHandle defaultHandle = lookup.findVirtual(MHandleVoodooDoll.class, "addToDefault", defaultType);

        Method privateMethod = MHandleVoodooDoll.class.getDeclaredMethod("getPrivate");
        privateMethod.setAccessible(true);
        MethodHandle privateHandle = lookup.unreflect(privateMethod);

        final MHandleVoodooDoll doll = new MHandleVoodooDoll();

        final int publicResult = (int) publicHandle.invoke(doll, 1, 2);
        final int privateResult = (int) privateHandle.invoke(doll);
        defaultHandle.invoke(doll, 5);

        System.out.println(publicResult);
        System.out.println(privateResult);
        System.out.println(doll);
    }
}
