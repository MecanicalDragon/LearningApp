import java.util.Optional;

/**
 * {@author} Stanislav Tretyakov
 * 24.12.2019
 */
public class Optionals {

    static String nullString;
    static String notNullString = "Not null String";

    public static void main(String[] args) {
        nullOp();
        nullableOp();
        or();
        filter();
        map();
        flatMap();
    }

    private static void nullOp() {

        System.out.println("nullOp function invoked");

        try {
            Optional<String> nullOp = Optional.of(nullString);
            System.out.println(nullOp.get());
        } catch (Exception e) {
            System.out.println("Optional of(null) throws NPE.");
        }

        System.out.println();
    }

    private static void nullableOp() {

        System.out.println("nullableOp function invoked");

        Optional<String> nullableOp = Optional.ofNullable(nullString);
        if (nullableOp.isPresent()) {
            System.out.println(nullableOp);
        } else {
            System.out.println("Nullable optional is empty.");
        }
        nullableOp.ifPresent(System.out::println);

        System.out.println();
    }

    private static void or() {

        System.out.println("or function invoked");

        String orElse = Optional.ofNullable(notNullString).orElse(orElse());
        System.out.println(orElse);

        // if value is not null, orElseGet will not be invoked.
        String orElseGet = Optional.ofNullable(notNullString).orElseGet(Optionals::orElseGet);
        System.out.println(orElseGet);

        System.out.println();
    }

    private static String orElse() {
        System.out.println("orElse function invoked");
        return "Or Else";
    }

    private static String orElseGet() {
        System.out.println("orElseGet function invoked");
        return "Or Else Get";
    }

    private static void filter() {
        System.out.println("filter function invoked");

        Optional<String> filter = Optional.of(notNullString);
        System.out.println("Is filter-optional value longer than 10 characters but shorter than 20?");
        boolean b = filter.filter(s -> s.length() > 10).filter(s -> s.length() < 20).isPresent();
        System.out.println(b);

        System.out.println();
    }

    private static void map() {

        System.out.println("map function invoked");

        Optional<String> map = Optional.ofNullable(nullString);
        System.out.println("Is filter-optional value longer than 10 characters but shorter than 20?");
        Integer length = map.map(String::length).orElse(0);
        System.out.println("Map-optional value's length is " + length);

        System.out.println();
    }

    //    The difference is that map transforms values only when they are unwrapped
    //    whereas flatMap takes a wrapped value and unwraps it before transforming it.
    private static void flatMap() {

        System.out.println("flatMap function invoked");

        Person person = new Person("Vasiliy", 30);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper = personOptional.map(Person::getName);
        Optional<String> nameOptional = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("null");

        // map(), flatMap() and filter() can be chained
        String name2 = personOptional.filter(p -> p.age < 30).flatMap(Person::getName).orElse("null");
        System.out.println(name1);
        System.out.println(name2);

        System.out.println();
    }

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Integer> getAge() {
            return Optional.ofNullable(age);
        }
    }
}
