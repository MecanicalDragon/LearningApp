package patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Pattern defines basic algorithm, delegating it's particular steps to subclasses.
 * Use it when:
 * > subclasses should extend basic algorithm, not changing it's structure.
 * > if you've got some classes, that do the same with little differences.
 * <p>
 * + makes code reusing easier.
 * <p>
 * - you're limited with basic algorithm skeleton
 * - You can break Liskov principle
 * - With a lot of steps it's difficult to maintain template method
 * <p>
 * IMPLEMENTATION
 * <p>
 * Изучите алгоритм и подумайте, можно ли его разбить на шаги. Прикиньте, какие шаги будут стандартными для всех
 * вариаций алгоритма, а какие — изменяющимися.
 * <p>
 * Создайте абстрактный базовый класс. Определите в нём шаблонный метод. Этот метод должен состоять из вызовов шагов
 * алгоритма. Имеет смысл сделать шаблонный метод финальным, чтобы подклассы не могли переопределить его
 * (если ваш язык программирования это позволяет).
 * <p>
 * Добавьте в абстрактный класс методы для каждого из шагов алгоритма. Вы можете сделать эти методы абстрактными или
 * добавить какую-то реализацию по умолчанию. В первом случае все подклассы должны будут реализовать эти методы, а во
 * втором — только если реализация шага в подклассе отличается от стандартной версии.
 * <p>
 * Подумайте о введении в алгоритм хуков. Чаще всего, хуки располагают между основными шагами алгоритма, а также до и после всех шагов.
 * <p>
 * Создайте конкретные классы, унаследовав их от абстрактного класса. Реализуйте в них все недостающие шаги и хуки.
 */
public class TemplateMethod {
    public static void main(String[] args) {

        UnitFactory humanFactory = new HumanFactory();
        UnitFactory elfFactory = new ElfFactory();
        UnitFactory orcFactory = new OrcFactory();

        Unit orc = orcFactory.createUnit();
        Unit elf = elfFactory.createUnit();
        Unit human = humanFactory.createUnit();

        System.out.println(human);
        System.out.println(elf);
        System.out.println(orc);
    }
}

class Unit {
    String name;
    int attack;
    int defence;
    int initiaive;
    int accuracy;

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defence=" + defence +
                ", initiaive=" + initiaive +
                ", accuracy=" + accuracy +
                '}';
    }
}

abstract class UnitFactory {

    Unit createUnit() {
        Unit unit = new Unit();
        unit = setName(unit);
        unit = setAttack(unit);
        unit = setAccuracy(unit);
        unit = setDefence(unit);
        unit = setInitiative(unit);
        return unit;
    }

    abstract Unit setName(Unit unit);

    Unit setAttack(Unit unit) {
        unit.attack = 5;
        return unit;
    }

    Unit setDefence(Unit unit) {
        unit.defence = 5;
        return unit;
    }

    Unit setInitiative(Unit unit) {
        unit.initiaive = 5;
        return unit;
    }

    Unit setAccuracy(Unit unit) {
        unit.accuracy = 5;
        return unit;
    }

}

class OrcFactory extends UnitFactory{

    @Override
    Unit setName(Unit unit) {
        unit.name = "Orc";
        return unit;
    }

    @Override
    Unit setAttack(Unit unit) {
        unit.attack = 8;
        return unit;
    }
}

class ElfFactory extends UnitFactory{

    @Override
    Unit setName(Unit unit) {
        unit.name = "Elf";
        return unit;
    }

    @Override
    Unit setInitiative(Unit unit) {
        unit.initiaive = 6;
        return unit;
    }

    @Override
    Unit setAccuracy(Unit unit) {
        unit.accuracy = 7;
        return unit;
    }
}

class HumanFactory extends UnitFactory{

    @Override
    Unit setName(Unit unit) {
        unit.name = "Human";
        return unit;
    }

    @Override
    Unit setAttack(Unit unit) {
        unit.attack = 6;
        return unit;
    }

    @Override
    Unit setDefence(Unit unit) {
        unit.defence = 7;
        return unit;
    }
}
