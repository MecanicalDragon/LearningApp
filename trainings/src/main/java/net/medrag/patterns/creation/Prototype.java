package net.medrag.patterns.creation;

import java.util.ArrayList;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 28.01.2020
 * <p>
 * in fact, it's just cloneable
 * we need it cause sometimes we can't copy object from outside (because private fields for ex.)
 * <p>
 * It's useful when:
 * > when our code works with objects from outside through some interface. So, we don't depend on all types of these
 *      objects, only on common interface (or superclass)
 * > when we have a lot of subclasses, that differ with field values.
 * <p>
 * + Allows to clone objects, independently of their concrete classes
 * + Code reusability
 * + Speeds up object creation
 * <p>
 * - Difficult to clone complex objects with other objects references
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте интерфейс прототипов с единственным методом clone. Если у вас уже есть иерархия продуктов, метод
 * клонирования можно объявить непосредственно в каждом из её классов.
 * <p>
 * Добавьте в классы будущих прототипов альтернативный конструктор, принимающий в качестве аргумента объект текущего
 * класса. Этот конструктор должен скопировать из поданного объекта значения всех полей, объявленных в рамках текущего
 * класса, а затем передать выполнение родительскому конструктору, чтобы тот позаботился о полях, объявленных в суперклассе.
 * <p>
 * Если ваш язык программирования не поддерживает перегрузку методов, то вам не удастся создать несколько версий
 * конструктора. В этом случае копирование значений можно проводить и в другом методе, специально созданном для этих
 * целей. Конструктор удобнее тем, что позволяет клонировать объект за один вызов.
 * <p>
 * Метод клонирования обычно состоит всего из одной строки: вызова оператора new с конструктором прототипа. Все классы,
 * поддерживающие клонирование, должны явно определить метод clone, чтобы использовать собственный класс с
 * оператором new. В обратном случае результатом клонирования станет объект родительского класса.
 * <p>
 * Опционально, создайте центральное хранилище прототипов. В нём удобно хранить вариации объектов, возможно, даже
 * одного класса, но по-разному настроенных.
 * <p>
 * Вы можете разместить это хранилище либо в новом фабричном классе, либо в фабричном методе базового класса прототипов.
 * Такой фабричный метод должен на основании входящих аргументов искать в хранилище прототипов подходящий экземпляр,
 * а затем вызывать его метод клонирования и возвращать полученный объект.
 * <p>
 * Наконец, нужно избавиться от прямых вызовов конструкторов объектов, заменив их вызовами фабричного метода хранилища прототипов.
 */
public class Prototype {
    public static void main(String[] args) {

        List<Shape> list = new ArrayList<>();
        list.add(new Rectangle());
        list.add(new Circle());

        List<Shape> cloned = new ArrayList<>();
        list.forEach(it -> cloned.add(it.clone()));
    }
}

abstract class Shape {
    int x;
    int y;

    Shape() {
    }

    Shape(Shape source) {
        this.x = source.x;
        this.y = source.y;
    }

    public abstract Shape clone();
}

class Rectangle extends Shape {
    private int height;
    private int width;

    Rectangle() {
    }

    Rectangle(Rectangle source) {
        super(source);
        this.height = source.height;
        this.width = source.width;
    }

    @Override
    public Rectangle clone() {
        return new Rectangle(this);
    }
}

class Circle extends Shape {
    private int radius;

    Circle() {
    }

    Circle(Circle source) {
        super(source);
        this.radius = source.radius;
    }

    @Override
    public Circle clone() {
        return new Circle(this);
    }
}