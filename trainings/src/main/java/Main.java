import lombok.SneakyThrows;
import org.apache.commons.codec.StringEncoderComparator;
import org.apache.commons.lang3.mutable.MutableLong;
import supportClasses.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@author} Stanislav Tretyakov
 * 18.03.2019
 */


public class Main extends Student {

    public static void main(String[] args) {

//        cast();
//        bigDecimals();
//        testMutableLong();
//        checkStaticFinal();
//        forEachReturn();
//        staticInit();
//        treeMap();
//        instantiateStaticFail();
//        dates();
//        trees();
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
//        duplicates("ollo58896k");
//        listsRuntime();

//        circleTask(15);
//        matrixTask(20);

    }

    private static void cast() {
        List<Number> l1 = new ArrayList<>();
        l1.add(1);
        l1.add(0.5);
        List<?> list = l1;
        var list1 = (List<String>) list;
        list1.add("OK");
        var list2 = (List<Integer>) list;
        System.out.println(list2.size());
        final int integer = list2.get(0);
        System.out.println(list2.get(0));
        System.out.println(list2.get(1));
        String ok = ((List<String>) list).get(2);
        System.out.println(list2.get(2));
    }

    private static void bigDecimals() {
        double d = 0.3;
        System.out.println("double: " + d);
        Double d2 = Double.valueOf(d);
        System.out.println("Double: " + d2);
        final var d3 = new BigDecimal(d);
        final var d4 = new BigDecimal(d2);
        final var d5 = new BigDecimal("0.3");
        final var d6 = new BigDecimal(String.valueOf(d));
        final var d7 = new BigDecimal(String.valueOf(d2));
        System.out.println("BigDecimal: " + d3);
        System.out.println("BigDecimal: " + d4);
        System.out.println("BigDecimal: " + d5);
        System.out.println("BigDecimal: " + d6);
        System.out.println("BigDecimal: " + d7);
    }

    @SneakyThrows
    private static void testMutableLong() {
        final MutableLong l = new MutableLong(10_000);
        AtomicLong l2 = new AtomicLong(10_000);
        final var executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                while (l2.getAndDecrement() > 0) {
                    l.decrement();
                }
            });
        }

        while (l2.get() > 0) {
            Thread.sleep(1000);
        }
        Thread.sleep(1000);
        System.out.println(l);
        System.out.println(l2);
        executorService.shutdown();
    }

    @SneakyThrows
    private static void checkStaticFinal() {
        StaticFinal staticFinal = new StaticFinal();
        final var field = StaticFinal.class.getDeclaredField("s");
        final var staticField = StaticFinal.class.getDeclaredField("STATIC");
        field.setAccessible(true);
        staticField.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(staticFinal, "fluent");
        System.out.println(staticFinal.s);

        staticField.set(null, "DYNAMIC");
        System.out.println(StaticFinal.STATIC);
    }

    private static void forEachReturn() {
        var values = List.of(1, 2, 3, 4, 5, 6, 7);
        System.out.println("start");
        values.forEach(n -> {
            if (n % 2==0) {
                return;
            }
            System.out.println(n);
        });
        System.out.println("end");
    }

    @SneakyThrows
    private static void staticInit() {
        System.out.println("main");
        Thread.sleep(2000);
        new StaticInit();
    }

    private static void treeMap() {
        try {
            Map<Object, String> map1 = new TreeMap<>();
            map1.put(new Object(), "String");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Map<String, String> map1 = new TreeMap<>();
            map1.put(null, "String");
            map1.get(null);
        } catch (Exception e) {
            System.out.println(e);
        }

        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1==null || o2==null) {
                    return 0;
                } else {
                    return new StringEncoderComparator().compare(o1, o2);
                }
            }
        });
        map.put(null, "String");
        System.out.println("1");
        map.put(null, "String");
        System.out.println("2");
        map.put(null, "String");
        System.out.println("3");
        System.out.println(map.get(null));
    }

    @SneakyThrows
    private static void instantiateStaticFail() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Caught 1! " + e);
        }

        try {
            StaticFail s;
            try {
                s = new StaticFail();    // java.lang.ExceptionInInitializerError always thrown if exception happens in static init blocks
            } catch (Throwable t) {
                System.out.println("Caught 2! " + t);
            }
            s = new StaticFail();
        } catch (Throwable tt) {
            System.out.println("Caught 3! " + tt);
        }
    }

    private static void dates() {

        //        SimpleDateFormat
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mmXXX");
        String q = "12:12-07:00";
        final OffsetTime offsetTime1 = OffsetTime.parse(q, dateTimeFormatter);
        System.out.println(offsetTime1);
        ZoneOffset jvmTzOffset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();
        final var offsetTime = offsetTime1.withOffsetSameInstant(jvmTzOffset);
        System.out.println(offsetTime.toLocalTime());
        System.out.println("___");

        //        SimpleDateFormat
        final DateTimeFormatter dtfoo = DateTimeFormatter.ofPattern("HH:mm");
        String q2 = "12:12";
        final LocalTime now1 = LocalTime.parse(q2, dtfoo);
        final OffsetTime of = OffsetTime.of(now1, ZonedDateTime.now(ZoneId.systemDefault()).getOffset());
        System.out.println(of);
        System.out.println("___");

        final ZoneOffset offset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();
        System.out.println(offset);

        String s = "15:13";
        final DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("HH:mm");
        final var parsed = LocalTime.parse(s, dtf3);
        System.out.println(offsetTime1);
        final var offsetTimeX = offsetTime1.withOffsetSameInstant(ZoneOffset.UTC);
        System.out.println(offsetTimeX);
        final var x = offsetTimeX.withOffsetSameInstant(ZoneOffset.ofHours(5));
        System.out.println(x);
        final String format1 = x.format(dtf3);
        System.out.println(format1);

        final var parse1 = LocalTime.parse(s, dtf3);
        final OffsetTime offsetTimeY = parse1.atOffset(ZoneOffset.ofHours(3));
        System.out.println(offsetTimeY);
        System.out.println(offsetTimeY.toLocalTime());
        System.out.println(OffsetDateTime.now());

        //  Duration and Period
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.minus(Duration.parse("P2DT3H30M30.5S")));
        System.out.println(now.minus(Period.parse("P1Y2M1W3D")));

        System.out.println();

        System.out.println(ZonedDateTime.now());
        System.out.println(ZonedDateTime.now().withNano(0));
        System.out.println(OffsetDateTime.now());
        System.out.println(OffsetDateTime.now().withNano(0));
        System.out.println(ZoneOffset.getAvailableZoneIds());
        System.out.println(ZoneId.getAvailableZoneIds());

        System.out.println();

        String date = "2010-08-24";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        TemporalAccessor parse = dtf.parse(date);
        System.out.println(LocalDate.from(parse).format(dtf2));
        System.out.println(LocalDate.from(dtf.parse(date)).format(dtf2));

        System.out.println();

        System.out.println("java.util.Date");
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String format = sdf.format(d);
        System.out.println(format);

        System.out.println();

        System.out.println("java.sql.Date");
        java.sql.Date d2 = new java.sql.Date(d.getTime());
        System.out.println(d2);
    }

    private static void trees() {
        Map<Man, String> map = new TreeMap<>(Comparator.comparingInt(Man::getAge));
        map.put(new Man(23, "Vasya", null), "Junior");
        map.put(new Man(32, "Stan", null), "Overlord");
        map.put(new Man(30, "Petya", null), "Senior");
        map.put(new Man(26, "Sasha", null), "Middle");
        map.put(new Man(20, "Artem", null), "Trainee");
        map.forEach((man, string) -> System.out.println(man.getAge()));
    }

    private static void getListRuntime(List<Integer> list, int limit) {
//        ListIterator<Integer> listIterator = list.listIterator();
//        Look javaDocs, this is not a common iterator
        long startTime = System.nanoTime();
        for (int i = 0; i < limit; i++) {
            list.add(i);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(String.format("%s: %s", list.getClass().getSimpleName().substring(0, 9), duration));
    }

    private static void listsRuntime() {
        for (int i = 0; i < 40; i++) {
            List<Integer> list = i % 2==0 ? new ArrayList<>():new LinkedList<>();
            getListRuntime(list, 1000000);
        }
    }

    private static void duplicates(String str) {
        char[] chars = str.toCharArray();
        Deque<Character> deque = new ArrayDeque<>();
        for (Character c : chars) {
            Character character = deque.peekLast();
            if (c.equals(character)) {
                deque.removeLast();
            } else {
                deque.addLast(c);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (char d : deque) {
            stringBuilder.append(d);
        }
        System.out.println(stringBuilder);
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
        Clazz2 c2 = new Clazz2();
        c2.go();
        Int1 i = new Int1() {
        };
        i.go();
    }

    private static void circleTask(int side) {
        int limit = side - 1;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (i==0 || i==limit) {
                    System.out.print("# ");
                } else if (j==0 || j==limit) {
                    System.out.print("# ");
                } else if (i==j) {
                    System.out.print("# ");
                } else if (i + j==limit) {
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
                if (j==0 || j==side ||
                        i==0 || i==side ||
                        i==j || i + j==side) {
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
        System.out.println("a.getPrivateValue = " +
                a.getPrivateValue()); // Private methods, being called, called from variable type (later bounding doesn't work with private methods)

        System.out.println("(Descendant1) a).value = " + ((Descendant1) a).value);  // Fields, being called, taken from variable type.
        System.out.println(
                "(Descendant1) a).getValue = " + ((Descendant1) a).getValue());  // Methods, being called, invoked from real object type.
        System.out.println("(Descendant1) a).getPrivateValue = " +
                ((Descendant1) a).getPrivateValue());    // CASTING MATTERS!! If this private method would not be overridden in descendant, it could not be invoked!

        System.out.println("=============");
        Descendant1 d = new Descendant1();

        System.out.println("d.value = " + d.value);
        System.out.println("d.getValue = " + d.getValue());
        System.out.println("d.getPrivateValue = " + d.getPrivateValue());

        System.out.println("(Ancestor) d).value = " + ((Ancestor) d).value);  // Fields, being called, taken from variable type.
        System.out.println(
                "(Ancestor) d).getValue = " + ((Ancestor) d).getValue());  // Methods, being called, invoked from real object type.
        System.out.println("(Ancestor) d).getPrivateValue = " +
                ((Ancestor) d).getPrivateValue());    // CASTING MATTERS!! Here we invoke ancestor's private method.

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

