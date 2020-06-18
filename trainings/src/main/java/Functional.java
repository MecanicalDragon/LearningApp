import java.util.function.*;

/**
 * {@author} Stanislav Tretyakov
 * 03.09.2019
 */

public class Functional {
    public static void main(String[] args) {

//        StringBuilder.accept(something) - just cunsumes value and does smth with it.
        Consumer<StringBuilder> consumer = (s) -> s.append("_APPENDED");
        StringBuilder consumerResult = new StringBuilder("ConsumerResult");
        consumer.andThen(x -> x.append("_APPENDED_TOO")).accept(consumerResult);
        System.out.println(consumerResult);

//        StringBuilder.accept(Integer) - consumes two values and returns no result.
        BiConsumer<StringBuilder, Integer> biConsumer = StringBuilder::append;
        StringBuilder biConsumerResult = new StringBuilder("String");
        biConsumer.accept(biConsumerResult, 999);
        System.out.println(biConsumerResult);


//        Turns object of first-arg-type to the object of second-arg-type.
        Function<Integer, String> function = Object::toString;
        System.out.println(function.apply(999));

//        Extends Function. Produces a result of the same type as its operand.
        UnaryOperator<Integer> unaryOperator = (i) -> i * 2;
        System.out.println(unaryOperator.apply(5));


//        String = Integer + Boolean. Represents a function that accepts two arguments and produces a result.
        BiFunction<Integer, Boolean, String> biFunction = (i, b) -> "".concat(i.toString()).concat(b.toString());
        String biFunctionResult = biFunction.apply(999, true);
        System.out.println(biFunctionResult);

//        Extends BiFunction, Integer = Integer + Integer.
//        Represents an operation upon two operands of the same type, producing a result of this type.
        BinaryOperator<Integer> binaryOperator = (x, y) -> x + y;
        Integer binaryOperatorResult = binaryOperator.apply(5, 10);
        System.out.println(binaryOperatorResult);


//        Returns boolean, calculated from Integer
        Predicate<Integer> predicate = (i) -> i > 0;
        System.out.println(predicate.test(5));

//        Boolean = String && Integer
        BiPredicate<String, Integer> biPredicate = (s, i) -> s.equals(i.toString());
        System.out.println(biPredicate.test("999", 999));


//        Just returns String - why? Dunno.
        Supplier<String> supplier = () -> "supply";
        System.out.println(supplier.get());

    }
}
