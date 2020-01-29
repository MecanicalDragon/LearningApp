package patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to evolve object functionality, wrapping it in different wrappers
 * Use it, when:
 * > you need to add functionality 'on flight', silently from execution code.
 * > you can't extend target object
 * <p>
 * + Flexibility higher than extensions'
 * + 'on flight'
 * + segregated commitments
 * <p>
 * - difficult to configure object, wrapped several times
 * - a lot of tiny classes
 * <p>
 * IMPLEMENTATION
 * <p>
 * Убедитесь, что в вашей задаче есть один основной компонент и несколько опциональных дополнений или надстроек над ним.
 * <p>
 * Создайте интерфейс компонента, который описывал бы общие методы как для основного компонента, так и для его дополнений.
 * <p>
 * Создайте класс конкретного компонента и поместите в него основную бизнес-логику.
 * <p>
 * Создайте базовый класс декораторов. Он должен иметь поле для хранения ссылки на вложенный объект-компонент.
 * Все методы базового декоратора должны делегировать действие вложенному объекту.
 * <p>
 * И конкретный компонент, и базовый декоратор должны следовать одному и тому же интерфейсу компонента.
 * <p>
 * Теперь создайте классы конкретных декораторов, наследуя их от базового декоратора. Конкретный декоратор должен
 * выполнять свою добавочную функцию, а затем (или перед этим) вызывать эту же операцию обёрнутого объекта.
 * <p>
 * Клиент берёт на себя ответственность за конфигурацию и порядок обёртывания объектов.
 */
public class Decorator {
    public static void main(String[] args) {
        String message = "I love Math";
        Notifier sms = new SmsSender();
        sms.notify(message);
        Notifier email = new EmailSender();
        email.notify(message);
        Notifier both = new EmailSmsSender(sms, email);
        both.notify(message);

    }
}

interface Notifier {
    void notify(String msg);
}

class EmailSender implements Notifier {
    @Override
    public void notify(String msg) {
        System.out.println("sending by email: " + msg);
    }
}

class SmsSender implements Notifier {
    @Override
    public void notify(String msg) {
        System.out.println("sending by sms: " + msg);
    }
}

class EmailSmsSender implements Notifier {
    private Notifier smsSender;
    private Notifier emailSender;

    EmailSmsSender(Notifier smsSender, Notifier emailSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }

    @Override
    public void notify(String msg) {
        emailSender.notify(msg);
        smsSender.notify(msg);
    }

}