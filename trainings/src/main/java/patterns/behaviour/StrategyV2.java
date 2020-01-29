package patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to do some action with different ways, basing on implementor class.
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

        int a = 30, b = 6;

        int result1 = new CalculatorClass().calculate(a, b,new Adder());
        int result2 = new CalculatorClass().calculate(a, b,new Divider());

        System.out.println(result1);
        System.out.println(result2);
    }
}

class CalculatorClass {
    int calculate(int x, int y, Calculator calculator) {
        return calculator.doTheCalculation(x, y);
    }
}

interface Calculator {
    int doTheCalculation(int x, int y);
}

class Adder implements Calculator {
    @Override
    public int doTheCalculation(int x, int y) {
        return x + y;
    }
}

class Divider implements Calculator {
    @Override
    public int doTheCalculation(int x, int y) {
        return x / y;
    }
}

class Multiplier implements Calculator {
    @Override
    public int doTheCalculation(int x, int y) {
        return x + y;
    }
}

class Substractor implements Calculator {
    @Override
    public int doTheCalculation(int x, int y) {
        return x - y;
    }
}
