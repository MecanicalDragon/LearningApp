package net.medrag.patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows objects to change their behaviour, depending on their inner state. It implies, that we have several classes,
 * each of them matches the different state of an object and contains business logic of this state.
 * The main difference with Strategy pattern is that state objects know about each other and can init state switching.
 * <p>
 * Use it when:
 * > you have an object, which behaviour is different in different states.
 * > you want to replace extensive if-else conditions.
 * <p>
 * + gets rid of a lot of if-else conditions
 * + concentrates state-bounded code in one place
 * + simplifies context code
 * <p>
 * - complecates the code, if there are few conditions
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определитесь с классом, который будет играть роль контекста. Это может быть как существующий класс, в котором уже есть
 * зависимость от состояния, так и новый класс, если код состояний размазан по нескольким классам.
 * <p>
 * Создайте общий интерфейс состояний. Он должен описывать методы, общие для всех состояний, обнаруженных в контексте.
 * Заметьте, что не всё поведение контекста нужно переносить в состояние, а только то, которое зависит от состояний.
 * <p>
 * Для каждого фактического состояния создайте класс, реализующий интерфейс состояния. Переместите код, связанный с
 * конкретными состояниями в нужные классы. В конце концов, все методы интерфейса состояния должны быть реализованы во
 * всех классах состояний.
 * <p>
 * При переносе поведения из контекста вы можете столкнуться с тем, что это поведение зависит от приватных полей или
 * методов контекста, к которым нет доступа из объекта состояния. Существует парочка способов обойти эту проблему.
 * <p>
 * Самый простой — оставить поведение внутри контекста, вызывая его из объекта состояния. С другой стороны, вы можете
 * сделать классы состояний вложенными в класс контекста, и тогда они получат доступ ко всем приватным частям контекста.
 * Но последний способ доступен только в некоторых языках программирования (например, Java, C#).
 * <p>
 * Создайте в контексте поле для хранения объектов-состояний, а также публичный метод для изменения значения этого поля.
 * <p>
 * Старые методы контекста, в которых находился зависимый от состояния код, замените на вызовы соответствующих методов
 * объекта-состояния.
 * <p>
 * В зависимости от бизнес-логики, разместите код, который переключает состояние контекста либо внутри контекста, либо
 * внутри классов конкретных состояний.
 */
public class State {

    public static void main(String[] args) {

        Phone phone = new Phone();
        phone.call();
        phone.lock();
        phone.call();
        phone.emergencyCall();

    }
}

class Phone {
    PhoneState state;

    public Phone() {
        this.state = new TurnedOnState(this);
    }

    void setState(PhoneState state) {
        this.state = state;
    }

    void call() {
        this.state.call();
    }

    void surfInternet() {
        this.state.surfInternet();
    }

    void emergencyCall() {
        this.state.emergencyCall();
    }

    void lock() {
        this.setState(new LockedState(this));
    }

}

abstract class PhoneState {
    protected Phone phone;

    public PhoneState(Phone phone) {
        this.phone = phone;
    }

    abstract void call();

    abstract void surfInternet();

    abstract void emergencyCall();

    abstract void lock();

    abstract void unlock();
}

class TurnedOnState extends PhoneState {

    public TurnedOnState(Phone phone) {
        super(phone);
    }

    @Override
    void call() {
        System.out.println("calling...");
    }

    @Override
    void surfInternet() {
        System.out.println("internet surfing...");
    }

    @Override
    void emergencyCall() {
        System.out.println("EMERGENCY CALL!!!");
    }

    @Override
    void lock() {
        this.phone.setState(new LockedState(this.phone));
        System.out.println("LOCKED!");
    }

    @Override
    void unlock() {
        this.phone.setState(new TurnedOnState(this.phone));
        System.out.println("UNLOCKED!");
    }
}

class LockedState extends PhoneState {

    public LockedState(Phone phone) {
        super(phone);
    }

    @Override
    void call() {
        System.out.println("LOCKED!");
    }

    @Override
    void surfInternet() {
        System.out.println("LOCKED!");
    }

    @Override
    void emergencyCall() {
        System.out.println("phone is locked but anyway performs an EMERGENCY CALL!!!");
    }

    @Override
    void lock() {
        System.out.println("already locked");
    }

    @Override
    void unlock() {
        this.phone.setState(new TurnedOnState(this.phone));
        System.out.println("UNLOCKED!");
    }
}

class No3GSignalState extends PhoneState {

    public No3GSignalState(Phone phone) {
        super(phone);
    }

    @Override
    void call() {
        System.out.println("calling...");
    }

    @Override
    void surfInternet() {
        System.out.println("No stable signal for surfing.");
    }

    @Override
    void emergencyCall() {
        System.out.println("EMERGENCY CALL!!!");
    }

    @Override
    void lock() {
        this.phone.setState(new LockedState(this.phone));
        System.out.println("LOCKED!");
    }

    @Override
    void unlock() {
        System.out.println("already unlocked");
    }
}
