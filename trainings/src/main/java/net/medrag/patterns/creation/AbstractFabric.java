package net.medrag.patterns.creation;

/**
 * {@author} Stanislav Tretyakov
 * 28.01.2020
 * <p>
 * Allows to create bunches of bounded objects without bounds to concrete classes
 * Use it when:
 * > program business logic should work with different types of products, independently of concrete classes
 * > program already use fabric method, yet it's supposed to create new product types
 * <p>
 * + ensures compatibility of created products
 * + clears client code from concrete classes bounding
 * + separates product creation
 * + open-closed principle implemented
 * <p>
 * - complicates the code
 * - requires all product types in all client variations
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте таблицу соотношений типов продуктов к вариациям семейств продуктов.
 * <p>
 * Сведите все вариации продуктов к общим интерфейсам.
 * <p>
 * Определите интерфейс абстрактной фабрики. Он должен иметь фабричные методы для создания каждого из типов продуктов.
 * <p>
 * Создайте классы конкретных фабрик, реализовав интерфейс абстрактной фабрики. Этих классов должно быть столько же,
 * сколько и вариаций семейств продуктов.
 * <p>
 * Измените код инициализации программы так, чтобы она создавала определённую фабрику и передавала её в клиентский код.
 * <p>
 * Замените в клиентском коде участки создания продуктов через конструктор вызовами соответствующих методов фабрики.
 */
public class AbstractFabric {
    public static void main(String[] args) {

        GuiFactory factory = new MacOsGuiFactory();
        factory.getButton().click();
        factory.getCheckbox().check();

        factory = new WindowsGuiFactory();
        factory.getButton().click();
        factory.getCheckbox().check();
    }
}

interface Button {
    void click();
}

class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("simple click");
    }
}

class MacOsButton implements Button {
    @Override
    public void click() {
        System.out.println("fancy click");
    }
}

interface Checkbox {
    void check();
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void check() {
        System.out.println("simple check");
    }
}

class MacOsCheckbox implements Checkbox {
    @Override
    public void check() {
        System.out.println("fancy check");
    }
}


/**
 * Abstract fabric knows all types of products
 */
interface GuiFactory {
    Checkbox getCheckbox();

    Button getButton();
}

/**
 * Every factory returns it's own type of products
 */
class WindowsGuiFactory implements GuiFactory {

    @Override
    public Checkbox getCheckbox() {
        return new WindowsCheckbox();
    }

    @Override
    public Button getButton() {
        return new WindowsButton();
    }
}

/**
 * Несмотря на то, что фабрики оперируют конкретными классами,
 * их методы возвращают абстрактные типы продуктов. Благодаря
 * этому фабрики можно взаимозаменять, не изменяя клиентский
 * код.
 */
class MacOsGuiFactory implements GuiFactory {

    @Override
    public Checkbox getCheckbox() {
        return new MacOsCheckbox();
    }

    @Override
    public Button getButton() {
        return new MacOsButton();
    }
}
