package net.medrag.patterns.behaviour.actual;

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
public class Strategy {

    public static void main(String[] args) {
        Navigator navigator = new Navigator();
        navigator.findTheWay("Piter", "Moscow", new RailRoadPathFinder());
    }
}

class Navigator {
    void findTheWay(String departure, String destination, PathFinder strategy) {
        Path path = strategy.find();
        path.travel(departure, destination);
    }
}

interface Path {
    void travel(String departure, String destination);
}

class SeaPath implements Path {
    @Override
    public void travel(String departure, String destination) {
        System.out.println(String.format("Travelling form %s to %s by the sea", departure, destination));
    }
}

class RoadPath implements Path {
    @Override
    public void travel(String departure, String destination) {
        System.out.println(String.format("Travelling form %s to %s by the roads", departure, destination));
    }
}

class RailRoadPath implements Path {
    @Override
    public void travel(String departure, String destination) {
        System.out.println(String.format("Travelling form %s to %s by the railroads", departure, destination));
    }
}

/**
 * Strategy interface
 */
interface PathFinder {
    Path find();
}

class SeaPathFinder implements PathFinder {
    @Override
    public Path find() {
        return new SeaPath();
    }
}

class RoadPathFinder implements PathFinder {
    @Override
    public Path find() {
        return new RoadPath();
    }
}

class RailRoadPathFinder implements PathFinder {
    @Override
    public Path find() {
        return new RailRoadPath();
    }
}


