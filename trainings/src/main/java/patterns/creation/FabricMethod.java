package patterns.creation;

/**
 * {@author} Stanislav Tretyakov
 * 28.01.2020
 * <p>
 * allows to define common interface in superclass, leaving to subclasses the ability to change the type of objects.
 * Use it, when:
 * > you need to process objects of a single interface by different ways, depending on object type
 * > you don't know types and dependencies of all objects, you need to interact with in future.
 * > you want to allow to extend functionality in future
 * > you want to save resources,using already created objects repeatably
 * <p>
 * + Allows not to bound class to concrete products
 * + Separates creation code
 * + Simplifies new product creation
 * + Open-closed principle implementation
 * <p>
 * - Can be reason of huge class hierarchy, cause for every product you need it's own creator
 * <p>
 * IMPLEMENTATION
 * <p>
 * Приведите все создаваемые продукты к общему интерфейсу.
 * <p>
 * В классе, который производит продукты, создайте пустой фабричный метод. В качестве возвращаемого типа укажите общий
 * интерфейс продукта.
 * <p>
 * Затем пройдитесь по коду класса и найдите все участки, создающие продукты. Поочерёдно замените эти участки вызовами
 * фабричного метода, перенося в него код создания различных продуктов.
 * <p>
 * В фабричный метод, возможно, придётся добавить несколько параметров, контролирующих, какой из продуктов нужно создать.
 * <p>
 * На этом этапе фабричный метод, скорее всего, будет выглядеть удручающе. В нём будет жить большой условный оператор,
 * выбирающий класс создаваемого продукта. Но не волнуйтесь, мы вот-вот исправим это.
 * <p>
 * Для каждого типа продуктов заведите подкласс и переопределите в нём фабричный метод. Переместите туда код создания
 * соответствующего продукта из суперкласса.
 * <p>
 * Если создаваемых продуктов слишком много для существующих подклассов создателя, вы можете подумать о введении
 * параметров в фабричный метод, которые позволят возвращать различные продукты в пределах одного подкласса.
 * <p>
 * Например, у вас есть класс Почта с подклассами АвиаПочта и НаземнаяПочта, а также классы продуктов Самолёт, Грузовик
 * и Поезд. Авиа соответствует Самолётам, но для НаземнойПочты есть сразу два продукта. Вы могли бы создать новый
 * подкласс почты для поездов, но проблему можно решить и по-другому. Клиентский код может передавать в фабричный метод
 * НаземнойПочты аргумент, контролирующий тип создаваемого продукта.
 * <p>
 * Если после всех перемещений фабричный метод стал пустым, можете сделать его абстрактным. Если в нём что-то осталось
 * — не беда, это будет его реализацией по умолчанию.
 */
public class FabricMethod {
    public static void main(String[] args) {

        LogisticPoint point;

        Delivery delivery = Delivery.SEA_DELIVERY;
        if (delivery == Delivery.LAND_DELIVERY) {
            point = new Parking();
        } else {
            point = new Port();
        }
        point.getUnitAndDeliver(delivery);

    }
}

enum Delivery {
    LAND_DELIVERY,
    SEA_DELIVERY
}

interface Deliverer {
    void deliver(Delivery delivery);
}

class Truck implements Deliverer {
    @Override
    public void deliver(Delivery delivery) {
        System.out.println(delivery + " delivered by the land");
    }
}

class Ship implements Deliverer {
    @Override
    public void deliver(Delivery delivery) {
        System.out.println(delivery + " delivered by the sea");
    }
}

abstract class LogisticPoint {

    void getUnitAndDeliver(Delivery delivery) {
        Deliverer deliverer = getDeliveringUnit();
        deliverer.deliver(delivery);
    }

    // this is fabric method itself
    abstract Deliverer getDeliveringUnit();
}

class Port extends LogisticPoint {
    @Override
    Deliverer getDeliveringUnit() {
        return new Ship();
    }
}

class Parking extends LogisticPoint {
    @Override
    Deliverer getDeliveringUnit() {
        return new Truck();
    }
}
