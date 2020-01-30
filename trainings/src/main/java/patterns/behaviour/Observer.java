package patterns.behaviour;

import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Subscribes on the changes in observable object
 * Use it, when:
 * > objects need to track other object's state, but which objects - it depends on situation.
 * <p>
 * + Publishers don't depend on subscribers.
 * + You can subscribe and unsubscribe on fly
 * + Open-closed principle implemented
 * <p>
 * - Subscribers are notified in random order.
 * <p>
 * IMPLEMENTATION
 * <p>
 * Разбейте вашу функциональность на две части: независимое ядро и опциональные зависимые части. Независимое ядро
 * станет издателем. Зависимые части станут подписчиками.
 * <p>
 * Создайте интерфейс подписчиков. Обычно в нём достаточно определить единственный метод оповещения.
 * <p>
 * Создайте интерфейс издателей и опишите в нём операции управления подпиской. Помните, что издатель должен работать
 * только с общим интерфейсом подписчиков.
 * <p>
 * Вам нужно решить, куда поместить код ведения подписки, ведь он обычно бывает одинаков для всех типов издателей.
 * Самый очевидный способ — вынести этот код в промежуточный абстрактный класс, от которого будут наследоваться все издатели.
 * <p>
 * Но если вы интегрируете паттерн в существующие классы, то создать новый базовый класс может быть затруднительно.
 * В этом случае вы можете поместить логику подписки во вспомогательный объект и делегировать ему работу из издателей.
 * <p>
 * Создайте классы конкретных издателей. Реализуйте их так, чтобы после каждого изменения состояния они отправляли
 * оповещения всем своим подписчикам.
 * <p>
 * Реализуйте метод оповещения в конкретных подписчиках. Не забудьте предусмотреть параметры, через которые издатель
 * мог бы отправлять какие-то данные, связанные с происшедшим событием.
 * <p>
 * Возможен и другой вариант, когда подписчик, получив оповещение, сам возьмёт из объекта издателя нужные данные. Но в
 * этом случае вы будете вынуждены привязать класс подписчика к конкретному классу издателя.
 * <p>
 * Клиент должен создавать необходимое количество объектов подписчиков и подписывать их у издателей.
 */
public class Observer {
    public static void main(String[] args) {

        EventManager manager = new EventManager();
        Shop shop = new Shop(manager);

        Client client = new Client();
        CorpClient corpClient = new CorpClient();
        FormerClient formerClient = new FormerClient();

        manager.subscribe(formerClient);
        manager.subscribe(client);
        manager.subscribe(corpClient);
        manager.unsubscribe(formerClient);

        shop.receiveNewGoods();
    }
}

class Shop {
    private EventManager manager;

    Shop(EventManager manager) {
        this.manager = manager;
    }

    void receiveNewGoods() {
        manager.notifySubscribers();
    }
}

class EventManager {
    private List<Subscriber> subscribers = new ArrayList<>();

    void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.notifyIt();
        }
    }
}

interface Subscriber {
    void notifyIt();
}

class Client implements Subscriber {
    @Override
    public void notifyIt() {
        System.out.println("I'm notified");
    }
}

class CorpClient implements Subscriber {

    @Override
    public void notifyIt() {
        System.out.println("We all notified too");
    }
}

class FormerClient implements Subscriber {

    @Override
    public void notifyIt() {
        System.out.println("I don't care");
    }
}
