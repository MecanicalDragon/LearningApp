package net.medrag.patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows to pass requests in chain from one handler to another.
 * Use when:
 * > code have to handle different requests with different ways, but you don't know, which requests will take place.
 * > order of handlers is important
 * > array of handlers should change dinamically
 * <p>
 * + decrease coupling between client and handler
 * + single responsibility principle implemented
 * + open-closed principle implemented
 * <p>
 * - request can be left unhandled
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте интерфейс обработчика и опишите в нём основной метод обработки.
 * <p>
 * Продумайте, в каком виде клиент должен передавать данные запроса в обработчик. Самый гибкий способ — превратить
 * данные запроса в объект и передавать его целиком через параметры метода обработчика.
 * <p>
 * Имеет смысл создать абстрактный базовый класс обработчиков, чтобы не дублировать реализацию метода получения
 * следующего обработчика во всех конкретных обработчиках.
 * <p>
 * Добавьте в базовый обработчик поле для хранения ссылки на следующий объект цепочки. Устанавливайте начальное
 * значение этого поля через конструктор. Это сделает объекты обработчиков неизменяемыми. Но если программа предполагает
 * динамическую перестройку цепочек, можете добавить и сеттер для поля.
 * <p>
 * Реализуйте базовый метод обработки так, чтобы он перенаправлял запрос следующему объекту, проверив его наличие.
 * Это позволит полностью скрыть поле-ссылку от подклассов, дав им возможность передавать запросы дальше по цепи,
 * обращаясь к родительской реализации метода.
 * <p>
 * Один за другим создайте классы конкретных обработчиков и реализуйте в них методы обработки запросов. При получении
 * запроса каждый обработчик должен решить:
 * <p>
 * Может ли он обработать запрос или нет?
 * Следует ли передать запрос следующему обработчику или нет?
 * Клиент может собирать цепочку обработчиков самостоятельно, опираясь на свою бизнес-логику, либо получать уже готовые
 * цепочки извне. В последнем случае цепочки собираются фабричными объектами, опираясь на конфигурацию приложения или
 * параметры окружения.
 * <p>
 * Клиент может посылать запросы любому обработчику в цепи, а не только первому. Запрос будет передаваться по цепочке
 * до тех пор, пока какой-то обработчик не откажется передавать его дальше, либо когда будет достигнут конец цепи.
 * <p>
 * Клиент должен знать о динамической природе цепочки и быть готов к таким случаям:
 * <p>
 * Цепочка может состоять из единственного объекта.
 * Запросы могут не достигать конца цепи.
 * Запросы могут достигать конца, оставаясь необработанными.
 */
public class ChainOfResponsibilityV2 {
    public static void main(String[] args) {

        Integer request = 56;
        String request2 = "request";

        AbstractHandler2 stringHandler = new StringHandler();
        AbstractHandler2 booleanHandler = new BooleanHandler();
        AbstractHandler2 integerHandler = new IntegerHandler();
        stringHandler.setNext(booleanHandler);
        booleanHandler.setNext(integerHandler);

        RequestReceiver2 requestReceiver2 = new RequestReceiver2(stringHandler);
        requestReceiver2.receiveRequest(request);
        requestReceiver2.receiveRequest(request2);
    }
}

class RequestReceiver2 {
    private Handler2 handler;

    RequestReceiver2(Handler2 handler) {
        this.handler = handler;
    }

    void receiveRequest(Object request) {
        Object response = handler.handle(request);
        System.out.println(response);
    }
}

interface Handler2 {
    Object handle(Object request);
}

abstract class AbstractHandler2 implements Handler2 {
    protected Handler2 next;

    public void setNext(Handler2 next) {
        this.next = next;
    }

}

class StringHandler extends AbstractHandler2 {
    public Object handle(Object request) {
        if (request instanceof String)
            return ((String) request).toUpperCase();
        if (next != null) {
            return next.handle(request);
        } else throw new RuntimeException("There is no handlers for this type of request!");
    }
}

class IntegerHandler extends AbstractHandler2 {
    public Object handle(Object request) {
        if (request instanceof Integer)
            return (Integer) request * 2;
        if (next != null) {
            return next.handle(request);
        } else throw new RuntimeException("There is no handlers for this type of request!");
    }
}

class BooleanHandler extends AbstractHandler2 {
    public Object handle(Object request) {
        if (request instanceof Boolean)
            return !((Boolean) request);
        if (next != null) {
            return next.handle(request);
        } else throw new RuntimeException("There is no handlers for this type of request!");
    }
}
