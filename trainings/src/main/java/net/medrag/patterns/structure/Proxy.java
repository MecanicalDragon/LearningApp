package net.medrag.patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Useful when:
 * > it's relevant to use lazy initialization.
 * > you want to implement security check this way
 * > you use it as remote proxy
 * > you want to log requests
 * > you cache expensive objects
 * <p>
 * + allows to control service object
 * + able to work even if service is even not created
 * + can control lifecycle of proxied object
 * <p>
 * - increases access time
 * - complicates the program code
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определите интерфейс, который бы сделал заместитель и оригинальный объект взаимозаменяемыми.
 * <p>
 * Создайте класс заместителя. Он должен содержать ссылку на сервисный объект. Чаще всего, сервисный объект создаётся
 * самим заместителем. В редких случаях заместитель получает готовый сервисный объект от клиента через конструктор.
 * <p>
 * Реализуйте методы заместителя в зависимости от его предназначения. В большинстве случаев, проделав какую-то полезную
 * работу, методы заместителя должны передать запрос сервисному объекту.
 * <p>
 * Подумайте о введении фабрики, которая решала бы, какой из объектов создавать — заместитель или реальный сервисный
 * объект. Но, с другой стороны, эта логика может быть помещена в создающий метод самого заместителя.
 * <p>
 * Подумайте, не реализовать ли вам ленивую инициализацию сервисного объекта при первом обращении клиента к методам заместителя.
 */
public class Proxy {

    public static void main(String[] args) {
        Data database = new DataBaseProxy(new DataBase());
        System.out.println(database.getData());
        System.out.println(database.getData());
        System.out.println(database.getData());
    }
}

interface Data {
    String getData();
}

class DataBase implements Data {
    String data = "database data";

    @Override
    public String getData() {
        System.out.println("This operation is very expensive!");
        return data;
    }
}

class DataBaseProxy implements Data {
    String cachedData;
    Data realData;

    public DataBaseProxy(Data realData) {
        this.realData = realData;
    }

    @Override
    public String getData() {
        if (cachedData == null) {
            System.out.println("performing remote request...");
            cachedData = realData.getData();
        }
        return cachedData;
    }
}
