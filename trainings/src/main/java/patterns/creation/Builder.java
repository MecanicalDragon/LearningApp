package patterns.creation;

/**
 * {@author} Stanislav Tretyakov
 * 28.01.2020
 * <p>
 * Useful when:
 * > you need to construct objects with a lot of parameters, and most of them are default.
 * <p>
 * + allows to create objects step by step
 * + allows to reuse the same code for creating of different objects
 * + if creation of object is difficult, isolates this project from business logic
 * <p>
 * - creates additional classes, making programm more complicated.
 * - if there are no 'getResult' method in the builder interface, client is bounded to concrete builder classes
 * <p>
 * IMPLEMENTATION
 * <p>
 * Убедитесь в том, что создание разных представлений объекта можно свести к общим шагам.
 * <p>
 * Опишите эти шаги в общем интерфейсе строителей.
 * <p>
 * Для каждого из представлений объекта-продукта создайте по одному классу-строителю и реализуйте их методы строительства.
 * <p>
 * Не забудьте про метод получения результата. Обычно конкретные строители определяют собственные методы получения
 * результата строительства. Вы не можете описать эти методы в интерфейсе строителей, поскольку продукты не обязательно
 * должны иметь общий базовый класс или интерфейс. Но вы всегда сможете добавить метод получения результата в общий
 * интерфейс, если ваши строители производят однородные продукты с общим предком.
 * <p>
 * Подумайте о создании класса директора. Его методы будут создавать различные конфигурации продуктов, вызывая разные
 * шаги одного и того же строителя.
 * <p>
 * Клиентский код должен будет создавать и объекты строителей, и объект директора. Перед началом строительства клиент
 * должен связать определённого строителя с директором. Это можно сделать либо через конструктор, либо через сеттер,
 * либо подав строителя напрямую в строительный метод директора.
 * <p>
 * Результат строительства можно вернуть из директора, но только если метод возврата продукта удалось поместить в общий
 * интерфейс строителей. Иначе вы жёстко привяжете директора к конкретным классам строителей.
 */
public class Builder {
    public static void main(String[] args) {
        Director director = new Director();
        Car truck = director.getCar(new TruckBuilder());
        Car sportCar = director.getCar(new SportCarBuilder());
    }
}

class Car {
    int maxSpeed = 160;
    String type = "Minivan";
    int horsePower = 50;
    //a lot of other default parameters
}

interface CarBuilder {
    void reset();

    void setMaxSpeed();

    void setType();

    void setHP();

    Car getResult();
}

class TruckBuilder implements CarBuilder {

    private Car car;

    @Override
    public void reset() {
        car = new Car();
    }

    @Override
    public void setMaxSpeed() {
        car.maxSpeed = 100;
    }

    @Override
    public void setType() {
        car.type = "Truck";
    }

    @Override
    public void setHP() {
        car.horsePower = 400;
    }

    @Override
    public Car getResult() {
        return car;
    }
}

class SportCarBuilder implements CarBuilder {

    private Car car;

    @Override
    public void reset() {
        car = new Car();
    }

    @Override
    public void setMaxSpeed() {
        car.maxSpeed = 350;
    }

    @Override
    public void setType() {
        car.type = "SportCar";
    }

    @Override
    public void setHP() {
        car.horsePower = 250;
    }

    @Override
    public Car getResult() {
        return car;
    }
}

class Director {
    public Car getCar(CarBuilder builder) {
        builder.reset();
        builder.setHP();
        builder.setMaxSpeed();
        builder.setType();
        return builder.getResult();
    }
}
