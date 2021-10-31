package net.medrag.patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows to reduce coupling of a lot of objects by replacing relationships of these objects in a single mediator class
 * <p>
 * Useful when:
 * > you want to make classes more clear, remove excess relationships from them
 * > You want to make class independent from other classes
 * > You want to reuse once developed components
 * <p>
 * + clears dependencies
 * + increases reusability
 * + simplifies interaction
 * + centralizes control
 * <p>
 * - mediator can be very big
 * <p>
 * IMPLEMENTATION
 * <p>
 * Найдите группу тесно переплетённых классов, отвязав которые друг от друга, можно получить некоторую пользу.
 * Например, чтобы повторно использовать их код в другой программе.
 * <p>
 * Создайте общий интерфейс посредников и опишите в нём методы для взаимодействия с компонентами. В простейшем случае
 * достаточно одного метода для получения оповещений от компонентов.
 * <p>
 * Этот интерфейс необходим, если вы хотите повторно использовать классы компонентов для других задач. В этом случае
 * всё, что нужно сделать — это создать новый класс конкретного посредника.
 * <p>
 * Реализуйте этот интерфейс в классе конкретного посредника. Поместите в него поля, которые будут содержать ссылки
 * на все объекты компонентов.
 * <p>
 * Вы можете пойти дальше и переместить код создания компонентов в класс посредника, после чего он может напоминать
 * фабрику или фасад.
 * <p>
 * Компоненты тоже должны иметь ссылку на объект посредника. Связь между ними удобнее всего установить, подавая
 * посредника в параметры конструктора компонентов.
 * <p>
 * Измените код компонентов так, чтобы они вызывали метод оповещения посредника, вместо методов других компонентов.
 * С противоположной стороны, посредник должен вызывать методы нужного компонента, когда получает оповещение от компонента.
 */
public class Mediator {
    public static void main(String[] args) {

        SomeForm mediator = new SomeForm();
        mediator.textField.click();
        mediator.button.click();

        CompletelyAnotherForm mediator2 = new CompletelyAnotherForm();
        mediator2.textField.click();
        mediator2.button.click();
    }
}

interface MediatorInterface {
    void trigger(Component component);
}

class SomeForm implements MediatorInterface {

    Button button;
    TextField textField;

    private String data;

    SomeForm() {
        button = new Button(this);
        textField = new TextField(this);
    }

    @Override
    public void trigger(Component component) {
        if (component == button) {
            if (button.clicked)
                sendData();
        } else if (component == textField) {
            data = textField.text;
            System.out.println(String.format("The data has been set to mediator: %s", data));
        }
    }

    private void sendData() {
        System.out.println(String.format("The data has been sent to service: %s", data));
    }
}

class CompletelyAnotherForm implements MediatorInterface {

    Button button;
    TextField textField;

    private String data;

    CompletelyAnotherForm() {
        button = new Button(this);
        textField = new TextField(this);
    }

    @Override
    public void trigger(Component component) {
        if (component == button) {
            if (button.clicked)
                sendData();
        } else if (component == textField) {
            data = textField.text;
            System.out.println(String.format("The data has been set to completely another mediator: %s", data));
        }
    }

    private void sendData() {
        System.out.println(String.format("The data has been sent to completely another service: %s", data));
    }
}

abstract class Component {
    MediatorInterface mediator;

    Component(MediatorInterface mediator) {
        this.mediator = mediator;
    }

    void click() {
        mediator.trigger(this);
    }
}

class TextField extends Component {

    String text;

    public TextField(MediatorInterface mediator) {
        super(mediator);
    }

    @Override
    void click() {
        text = "some data input";
        super.click();
    }
}

class Button extends Component {

    Boolean clicked = false;

    public Button(MediatorInterface mediator) {
        super(mediator);
    }

    @Override
    void click() {
        clicked = true;
        super.click();
    }
}