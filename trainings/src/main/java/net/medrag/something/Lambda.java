package net.medrag.something;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
public class Lambda {

    Supplier<Integer> supplier = () -> 777;

    void run() {
        Predicate<String> p = (String x) -> x.length() > 5;
        String s = "Hello";

        System.out.println(test(s, p));
        System.out.println(supplier.get());
    }

    boolean test(String string, Predicate<String> predicate) {
        return predicate.test(string);
    }
}
