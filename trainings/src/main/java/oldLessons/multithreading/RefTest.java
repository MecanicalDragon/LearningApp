package oldLessons.multithreading;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class RefTest {

    public static int st;
    final static String finalString = "This string is final";

    private int a;
    String s;

    public RefTest(int a, String s) {
        this.a = a;
        this.s = s;
    }

    public RefTest() {
    }

    void foo(){
        System.out.println("foo");
    }

    public int x(int x, int y){
        System.out.println(x*=y);
        return x;
    }

    @Override
    public String toString() {
        return "RefTest{" +
                "a=" + a +
                ", s='" + s + '\'' +
                '}';
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class c = RefTest.class;
        System.out.println("Names:");
        System.out.println("");
        System.out.println(c.getName());
        System.out.println(c.getSimpleName());
        RefTest rf = (RefTest) c.newInstance();
        System.out.println(rf);
        System.out.println("");
        System.out.println("Fields:");
        System.out.println("");
        Field[] declaredFields = c.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
        System.out.println("");
        Field f = c.getDeclaredField("a");
        f.setAccessible(true);
        f.setInt(rf, 25);
        f = c.getDeclaredField("s");
        f.set(rf, "new String");
        System.out.println(rf);
        f = c.getDeclaredField("st");
        f.setInt(rf.getClass(), 80);
        System.out.println("static field st = " + RefTest.st);

        System.out.println("");
        System.out.println("Methods:");
        System.out.println("");

        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }
        Method mx = c.getMethod("x", int.class, int.class);
        mx.invoke(rf, 5,4);
        Method m = c.getDeclaredMethod("foo");
        m.invoke(rf);

        System.out.println("");
        System.out.println("Bit Mask of modifiers:");
        System.out.println("");

        Class a = AbstractClass.class;
        int modifiers = a.getModifiers();
        System.out.println(modifiers); //m = 1025
// modifier public == 1; 1025 & 1 == 1;
        System.out.println("Is class public?: " + Modifier.isPublic(modifiers));
// modifier abstract == 1024; 1024 & 1025 == 1;
        System.out.print("Is class abstract?: ");
        System.out.println((Modifier.ABSTRACT & modifiers) != 0);
// modifier static == 8; 1025 & 8 == 0;
        System.out.print("Is class static?: ");
        System.out.println((modifiers & 8) != 0);
        System.out.println(Modifier.toString(modifiers));

    }
}
