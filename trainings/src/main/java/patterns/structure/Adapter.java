package patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows objects with incompatible interfaces work with each other
 * Use it when you:
 * > want to make two different interfaces compatible
 * > need to use several subclasses, but their functionality is not enough, also you can't extend superclass.
 * <p>
 * IMPLEMENTATION
 * <p>
 * Убедитесь, что у вас есть два класса с несовместимыми интерфейсами:
 * <p>
 * полезный сервис — служебный класс, который вы не можете изменять (он либо сторонний, либо от него зависит другой код);
 * один или несколько клиентов — существующих классов приложения, несовместимых с сервисом из-за неудобного или несовпадающего интерфейса.
 * Опишите клиентский интерфейс, через который классы приложения смогли бы использовать класс сервиса.
 * <p>
 * Создайте класс адаптера, реализовав этот интерфейс.
 * <p>
 * Поместите в адаптер поле, которое будет хранить ссылку на объект сервиса. Обычно это поле заполняют объектом,
 * переданным в конструктор адаптера. В случае простой адаптации этот объект можно передавать через параметры методов адаптера.
 * <p>
 * Реализуйте все методы клиентского интерфейса в адаптере. Адаптер должен делегировать основную работу сервису.
 * <p>
 * Приложение должно использовать адаптер только через клиентский интерфейс. Это позволит легко изменять и добавлять адаптеры в будущем.
 */
public class Adapter {

    public static void main(String[] args) {
        RoundHole hole = new RoundHole(50);
        RoundPeg rPeg = new RoundPeg(40);
        SquarePeg sPeg1 = new SquarePeg(35);
        SquarePeg sPeg2 = new SquarePeg(40);
        System.out.println(hole.fit(rPeg));
        System.out.println(hole.fit(new SquarePegAdapter(sPeg1)));
        System.out.println(hole.fit(new SquarePegAdapter(sPeg2)));
    }
}

class RoundHole {
    int radius;

    RoundHole(int radius) {
        this.radius = radius;
    }

    boolean fit(RoundPeg peg) {
        return peg.radius <= this.radius;
    }

}

class RoundPeg {
    int radius;

    RoundPeg() {
    }

    RoundPeg(int radius) {
        this.radius = radius;
    }
}

class SquarePeg {
    int width;

    SquarePeg(int width) {
        this.width = width;
    }
}

class SquarePegAdapter extends RoundPeg {

    private SquarePeg peg;

    SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
        this.radius = (int) (Math.sqrt((Math.pow(peg.width, 2)) * 2));
    }

}
