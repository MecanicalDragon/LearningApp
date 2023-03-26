package net.medrag.patterns.behaviour.obsolete;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows to preserve object's former states, hiding the details of it's inner realization.
 * <p>
 * Useful when:
 * > you need to save object's state for restoring it later
 * > you need to hide inner state of object
 * <p>
 * + doesn't break incapsulation of object
 * + simplifies target object structure
 * <p>
 * - requires a lot of memory
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определите класс создателя, объекты которого должны создавать снимки своего состояния.
 * <p>
 * Создайте класс снимка и опишите в нём все те же поля, которые имеются в оригинальном классе-создателе.
 * <p>
 * Сделайте объекты снимков неизменяемыми. Они должны получать начальные значения только один раз, через свой конструктор.
 * <p>
 * Если ваш язык программирования это позволяет, сделайте класс снимка вложенным в класс создателя. Если нет, извлеките
 * из класса снимка пустой интерфейс, который будет доступен остальным объектам программы. Впоследствии вы можете
 * добавить в этот интерфейс некоторые вспомогательные методы, дающие доступ к метаданным снимка, однако прямой доступ
 * к данным создателя должен быть исключён.
 * <p>
 * Добавьте в класс создателя метод получения снимков. Создатель должен создавать новые объекты снимков, передавая
 * значения своих полей через конструктор.
 * <p>
 * Сигнатура метода должна возвращать снимки через ограниченный интерфейс, если он у вас есть. Сам класс должен работать
 * с конкретным классом снимка.
 * <p>
 * Добавьте в класс создателя метод восстановления из снимка. Что касается привязки к типам, руководствуйтесь той же
 * логикой, что и в пункте 4.
 * <p>
 * Опекуны, будь то история операций, объекты команд или нечто иное, должны знать о том, когда запрашивать снимки у
 * создателя, где их хранить и когда восстанавливать.
 * <p>
 * Связь опекунов с создателями можно перенести внутрь снимков. В этом случае каждый снимок будет привязан к своему
 * создателю и должен будет сам восстанавливать его состояние. Но это будет работать либо если классы снимков вложены
 * в классы создателей, либо если создатели имеют соответствующие сеттеры для установки значений своих полей.
 */
public class Memento {
    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        for (int i = 0; i < 15; i++) {
            careTaker.updateData();
        }
        System.out.println();
        for (int i = 0; i < 15; i++) {
            careTaker.stepBack();
        }
    }
}

class CareTaker {
    private Deque<SomeGraphData.Snapshot> history = new ArrayDeque<>();
    private SomeGraphData graph = new SomeGraphData();

    void updateData() {
        history.add(graph.backUp());
        graph.revenue = (int) (Math.random() * 100);
        graph.expense = (int) (Math.random() * 100);
        System.out.println(graph);
    }

    void stepBack() {
        SomeGraphData.Snapshot snapshot = history.removeLast();
        if (snapshot != null)
            graph.restore(snapshot);
        System.out.println(graph);
    }
}

class SomeGraphData {

    /**
     * Another implementation:
     * private class Snapshot{
     * private final SomeGraphData graph;
     * private final int revenue;
     * private final int expense;
     * <p>
     * public Snapshot(SomeGraphData graph, int revenue, int expense) {
     * this.graph = graph;
     * this.revenue = revenue;
     * this.expense = expense;
     * }
     * <p>
     * void restore(){
     * this.graph.expense = this.expense;
     * this.graph.revenue = this.revenue;
     * }
     * }
     */
    static class Snapshot {
        private final int revenue;
        private final int expense;

        Snapshot(int revenue, int expense) {
            this.revenue = revenue;
            this.expense = expense;
        }
    }

    int revenue;
    int expense;

    void restore(Snapshot snapshot) {
        this.expense = snapshot.expense;
        this.revenue = snapshot.revenue;
    }

    Snapshot backUp() {
        return new Snapshot(this.revenue, this.expense);
    }

    @Override
    public String toString() {
        return "SomeGraphData{" +
                "revenue=" + revenue +
                ", expense=" + expense +
                '}';
    }
}