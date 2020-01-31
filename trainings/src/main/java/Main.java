import net.medrag.trainings.model.Student;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * {@author} Stanislav Tretyakov
 * 18.03.2019
 */


public class Main extends Student {

    public static void main(String[] args) {

//        System.out.println(tryAndCatch());
//        System.out.println(tryAndCatch2());
//        System.out.println(tryAndCatch3().getName());
//        innerClasses();
//        extendStudent();
//        dateAndTime();
//        interfaceInheritance();
//        variablesInMethods();
//        ancestorsAndDescendants();
//        overrideAndOverload();
//        interfacesAndAbstracts();
//        testArrays();

        circleTask(15);
//        matrixTask(20);

    }

    private static String tryAndCatch() {
        try {
            return "try";
        } finally {
            return "finally"; // finally returned
        }
    }

    private static String tryAndCatch2() {
        String returned;
        try {
            returned = "try";
            return returned;
        } finally {
            returned = "finally";   // try returned
        }
    }

    private static Student tryAndCatch3() {
        Student student = new Student();
        try {
            student.setName("Ingiborg");
            return student;
        } finally {
            student.setName("Abram");
        }
    }

    private static void innerClasses() {
        Outer.StaticInner staticInner = new Outer.StaticInner();
        Outer.Inner inner = new Outer().new Inner();
        new Outer().observe();
    }

    private static void extendStudent() {
        Main m = new Main();
        System.out.println("protected value of Main: " + m.protectedString);
        System.out.print("protected method of Main: ");
        m.protectedString();

//        Does not work:
        Student s = new Student();
//        System.out.println("protected value of Student: " + s.protectedString);
//        System.out.print("protected method of Student: ");
//        s.protectedString();
    }

    private void bitOperator() {
        for (int i = 0; i < 100; i++) {
            int y = new Random().nextInt();
            int z = y ^ y >>> 16;
            int q = z & 15;
            System.out.println(q);
        }
    }

    private static void dateAndTime() {

        String pattern = "\\d{4}-[0-1]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-6]\\d.\\d{3}Z";
        LocalDateTime t = LocalDateTime.now();

        TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
        System.out.println(tz.getRawOffset());
        System.out.println(tz.getOffset(System.currentTimeMillis()));
        System.out.println(tz.useDaylightTime());

        System.out.println(t);
        System.out.println(t.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        System.out.println(ZonedDateTime.of(t, ZoneId.of("Australia/Sydney")));
        System.out.println(ZoneId.SHORT_IDS);

    }

    /**
     * If one class inherited same name instance methods from interface and parent class, parent class has precedence.
     * But if class inherited same name instance methods from two interfaces, you must override that method explicitly.
     * But if method, inherited from class is static and interface method is not static, there will be a conflict, and the code will not compile.
     * If both methods are static, everything gonna be good.
     */
    private static void interfaceInheritance() {
        Clazz c = new Clazz();
        c.go();
    }

    private static void circleTask(int side) {
        int limit = side - 1;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (i == 0 || i == limit) {
                    System.out.print("# ");
                } else if (j == 0 || j == limit) {
                    System.out.print("# ");
                } else if (i == j) {
                    System.out.print("# ");
                } else if (i + j == limit) {
                    System.out.print("# ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }

    private static void matrixTask(int side) {
        int maxL = side * side;
        int l = String.valueOf(maxL).length();
        String space = " ";
        for (int i = 0; i <= l; i++) {
            space += " ";
        }

        for (int i = 0; i < side + 1; i++) {
            for (int j = 0; j < side + 1; j++) {

                String s = "";
                if (j == 0 || j == side ||
                        i == 0 || i == side ||
                        i == j || i + j == side) {
                    s += (i * j);
                    for (int k = 0; k < space.length(); k++) {
                        if (s.length() < space.length()) {
                            s += " ";
                        } else {
                            break;
                        }
                    }
                } else {
                    s = space;
                }
                System.out.print(s);
            }
            System.out.println("\n");
        }
    }

    private static void testArrays() {
        int[][][] array = new int[2][5][8];
        System.out.println(array.length);

        System.out.println(array[0].length);
        System.out.println(array[1].length);

        System.out.println(array[0][1].length);
        System.out.println(array[1][4].length);

        int[][] twoD = {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}, {10}};

    }

    /**
     * Fields defined in an interface are ALWAYS considered as public, static, and final.
     * Also, fields, defined in interfaces, become a static value, and a inner field of an instance of the class, that implements interface, at the same time.
     */
    private static void interfacesAndAbstracts() {

        System.out.println(Implementer.integer);
        System.out.println(new Implementer().integer);
    }

    /**
     * Overriding method, you can extend it's returned value, and, independently, extend visibility. For ex:
     * <p>
     * Number getValue() - in ancestor.
     * public Integer getValue() - in descendant.
     * <p>
     * Static methods can not be overridden by instance methods, and vise versa.
     * <p>
     * Overloading method, you can change amount and order of arguments,
     * and ONLY THEN you can specify absolutely another type of returned value. For ex:
     * <p>
     * public int getValue(int x){...};
     * public String getValue(float x){...};
     */
    private static void overrideAndOverload() {

    }

    private static void variablesInMethods() {
        int i = 5;
        Integer x = 10;
        String s = "string";
        int[] array = {1, 2};
        String[] strings = {"str1", "str2"};

        calculate(i, x, s, array, strings);

        System.out.println("int: " + i);
        System.out.println("Integer: " + x);
        System.out.println("String: " + s);
        System.out.println("Array: " + Arrays.toString(array));
        System.out.println("Strings: " + Arrays.toString(strings));
        System.out.println();
    }

    /**
     * Which variable (or static method) will be used depends on the class that the variable is declared of.
     * Which instance method will be used depends on the actual class of the object that is referenced by the variable.
     * <p>
     * Variable can be casted to class only if it refers to an instance of that class.
     */
    private static void ancestorsAndDescendants() {

        Ancestor a = new Descendant1();

        System.out.println("a.value = " + a.value); // Fields, being called, taken from variable type.
        System.out.println("a.getValue = " + a.getValue()); // Methods, being called, invoked from real object type.
        System.out.println("a.getPrivateValue = " + a.getPrivateValue()); // Private methods, being called, called from variable type (later bounding doesn't work with private methods)

        System.out.println("(Descendant1) a).value = " + ((Descendant1) a).value);  // Fields, being called, taken from variable type.
        System.out.println("(Descendant1) a).getValue = " + ((Descendant1) a).getValue());  // Methods, being called, invoked from real object type.
        System.out.println("(Descendant1) a).getPrivateValue = " + ((Descendant1) a).getPrivateValue());    // CASTING MATTERS!! If this private method would not be overridden in descendant, it could not be invoked!

        System.out.println("=============");
        Descendant1 d = new Descendant1();

        System.out.println("d.value = " + d.value);
        System.out.println("d.getValue = " + d.getValue());
        System.out.println("d.getPrivateValue = " + d.getPrivateValue());

        System.out.println("(Ancestor) d).value = " + ((Ancestor) d).value);  // Fields, being called, taken from variable type.
        System.out.println("(Ancestor) d).getValue = " + ((Ancestor) d).getValue());  // Methods, being called, invoked from real object type.
        System.out.println("(Ancestor) d).getPrivateValue = " + ((Ancestor) d).getPrivateValue());    // CASTING MATTERS!! Here we invoke ancestor's private method.

        try {
            Ancestor a2 = new Ancestor();
            System.out.println(((Descendant1) a2).value);
        } catch (Exception e) {
            System.out.println("Exception occurred, cause variable a2 does not refer to Descendant1-object, in fact it's an Ancestor-object.");
        }
    }

    private static void calculate(int i, Integer x, String s, int[] array, String[] strings) {
        i *= 2;             //value
        x = x * 2;          //immutable
        s = s + s;          //immutable
        array[0] = 666;     //extends Object, uses references
        array[1] = 999;     //extends Object, uses references
        strings[0] = "new String";
        strings = new String[2];    //reference to a new object.
        strings[1] = "new String too";
    }

    static class Outer {
        private String string = "outerString";
        private Inner inner = new Inner();

        void observe() {
            System.out.println(this.inner.inner);
            System.out.println(new StaticInner().inner);
        }

        static class StaticInner {
            private String inner = "static inner String";
        }

        class Inner {
            private String inner = "inner String";
        }
    }


    static class Ancestor {
        int value = 5;
        String string = "Ancestor";

        public static void go() {
            System.out.println("go");
        }

        public Ancestor(int value, String string) {
            this.value = value;
            this.string = string;
        }

        public Ancestor() {
        }

        Number getValue() {
            return value;
        }

        private int getPrivateValue() {
            return value;
        }

    }

    static class Descendant1 extends Ancestor {
        int value = 10;
        String string = "Descendant";

        public static void go() {
            System.out.println("go");
        }

        public Descendant1(int value, String string) {
            this.value = value;
            this.string = string;
        }

        public Descendant1() {
        }

        @Override
        protected Integer getValue() {
            return value;
        }

        //        Overloaded method
        public String getValue(int x) {
            return String.valueOf(x);
        }

        public int getPrivateValue() {
            return value;
        }
    }

    interface Inter1 {
        int integer = 5;
    }

    static class Implementer implements Inter1 {
    }

}

interface Int1 {
    default void go() {
        System.out.println("go1");
    }
}

interface Int2 {
    default void go() {
        System.out.println("go2");
    }
}

class Loh {
    public void go() {
        System.out.println("pow");
    }
}

class Clazz implements Int1, Int2 {
    public void go() {
        System.out.println("really go");
    }
}