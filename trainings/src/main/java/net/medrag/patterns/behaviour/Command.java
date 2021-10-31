package net.medrag.patterns.behaviour;

import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows to turn requests into objects, so you can pass them as arguments, enqueue them, log and even undo.
 * <p>
 * Use it when:
 * > You want to be able to adjust object's actions
 * > You want to stash or schedule operations
 * > You need undo option
 * <p>
 * + Removes dependency between command and it's invoker
 * + Allows to implement undo operation
 * + Allows to implement postponed command invocation
 * + Allows to collect simple commands to complex
 * + Open-closed principle implementation
 * <p>
 * - Makes program code more difficult
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте общий интерфейс команд и определите в нём метод запуска.
 * <p>
 * Один за другим создайте классы конкретных команд. В каждом классе должно быть поле для хранения ссылки на один или
 * несколько объектов-получателей, которым команда будет перенаправлять основную работу.
 * <p>
 * Кроме этого, команда должна иметь поля для хранения параметров, которые нужны при вызове методов получателя.
 * Значения всех этих полей команда должна получать через конструктор.
 * <p>
 * И, наконец, реализуйте основной метод команды, вызывая в нём те или иные методы получателя.
 * <p>
 * Добавьте в классы отправителей поля для хранения команд. Обычно объекты-отправители принимают готовые объекты команд
 * извне — через конструктор либо через сеттер поля команды.
 * <p>
 * Измените основной код отправителей так, чтобы они делегировали выполнение действия команде.
 * <p>
 * Порядок инициализации объектов должен выглядеть так:
 * <p>
 * Создаём объекты получателей.
 * Создаём объекты команд, связав их с получателями.
 * Создаём объекты отправителей, связав их с командами.
 */
public class Command {
    public static void main(String[] args) {

        SomeService service = new SomeService();

        Commandable save = new SaveCommand();
        Commandable delete = new DeleteCommand();

        ColorButton redDelete = new RedButton(service, delete);
        ColorButton redSave = new RedButton(service, save);
        ColorButton greenDelete = new GreenButton(service, delete);
        ColorButton greenSave = new GreenButton(service, save);

        Gui gui = new Gui(redDelete, redSave, greenDelete, greenSave);
        gui.greenDelete.click();
        gui.greenSave.click();
        gui.redDelete.click();
        gui.redSave.click();

        service.printStory();
    }
}

class Gui {
    ColorButton redDelete;
    ColorButton redSave;
    ColorButton greenDelete;
    ColorButton greenSave;

    Gui(ColorButton redDelete, ColorButton redSave, ColorButton greenDelete, ColorButton greenSave) {
        this.redDelete = redDelete;
        this.redSave = redSave;
        this.greenDelete = greenDelete;
        this.greenSave = greenSave;
    }
}

class SomeService {

    private List<Commandable> commands = new ArrayList<>();

    void saveData(Commandable commandable) {
        System.out.println("Some abstract data have been saved");
        saveCommand(commandable);
    }

    void deleteData(Commandable commandable) {
        System.out.println("Some abstract data have been deleted");
        saveCommand(commandable);
    }

    private void saveCommand(Commandable commandable) {
        commands.add(commandable);
    }

    void printStory() {
        commands.forEach(System.out::println);
    }
}

interface ColorButton {
    void click();
}

class GreenButton implements ColorButton {

    private SomeService service;
    private Commandable command;

    GreenButton(SomeService service, Commandable command) {
        this.service = service;
        this.command = command;
    }

    public void click() {
        System.out.println("Green button has been clicked!");
        this.command.execute(service);
    }
}

class RedButton implements ColorButton {

    private SomeService service;
    private Commandable command;

    RedButton(SomeService service, Commandable command) {
        this.service = service;
        this.command = command;
    }

    public void click() {
        System.out.println("Red button has been clicked!");
        this.command.execute(service);
    }
}

interface Commandable {
    void execute(SomeService service);
}

class SaveCommand implements Commandable {
    @Override
    public void execute(SomeService service) {
        service.saveData(this);
    }
}

class DeleteCommand implements Commandable {
    @Override
    public void execute(SomeService service) {
        service.deleteData(this);
    }
}
