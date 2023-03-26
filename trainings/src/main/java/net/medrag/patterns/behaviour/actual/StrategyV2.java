package net.medrag.patterns.behaviour.actual;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to do some action with different ways, basing on implementor class.
 * It especially makes sense it different strategies depend on different parameters.
 * Use it when:
 * > you need to apply different algorithm implementations within single object.
 * > you've got a lot of similar classes, that differ with some behaviour.
 * > you want to hide details of algorithm implementation for other classes.
 * > different algorithm variations can be implemented as extensive if-else.
 * <p>
 * + Swithing algorithms 'on flight'
 * + Implementation isolation
 * + Shifting from extensions to delegating
 * + open-closed implementation.
 * <p>
 * - Makes programm more complicated
 * - Client shoud know difference between strategies
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определите алгоритм, который подвержен частым изменениям. Также подойдёт алгоритм, имеющий несколько вариаций,
 * которые выбираются во время выполнения программы.
 * <p>
 * Создайте интерфейс стратегий, описывающий этот алгоритм. Он должен быть общим для всех вариантов алгоритма.
 * <p>
 * Поместите вариации алгоритма в собственные классы, которые реализуют этот интерфейс.
 * <p>
 * В классе контекста создайте поле для хранения ссылки на текущий объект-стратегию, а также метод для её изменения.
 * Убедитесь в том, что контекст работает с этим объектом только через общий интерфейс стратегий.
 * <p>
 * Клиенты контекста должны подавать в него соответствующий объект-стратегию, когда хотят, чтобы контекст вёл себя
 * определённым образом.
 */
public class StrategyV2 {
    public static void main(String[] args) {

        int result1 = new CalculatorClass().calculate(new Adder(30, 6));
        int result2 = new CalculatorClass().calculate(new FactorialFinder(5));

        System.out.println(result1);
        System.out.println(result2);
    }
}

class CalculatorClass {
    int calculate(Calculator calculator) {
        return calculator.doTheCalculation();
    }
}

interface Calculator {
    int doTheCalculation();
}

class Adder implements Calculator {
    int addendum1;
    int addendum2;

    public Adder(int addendum1, int addendum2) {
        this.addendum1 = addendum1;
        this.addendum2 = addendum2;
    }

    @Override
    public int doTheCalculation() {
        return addendum1 + addendum2;
    }
}

class Divider implements Calculator {
    int dividend;
    int divider;

    public Divider(int dividend, int divider) {
        this.dividend = dividend;
        this.divider = divider;
    }

    @Override
    public int doTheCalculation() {
        return dividend / divider;
    }
}

class FactorialFinder implements Calculator {
    int number;

    public FactorialFinder(int number) {
        this.number = number;
    }

    @Override
    public int doTheCalculation() {
        int result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}
