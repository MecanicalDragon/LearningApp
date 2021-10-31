package net.medrag.patterns.behaviour;

/**
 * {@author} Stanislav Tretyakov
 * 30.01.2020
 * <p>
 * Allows to iterate over collection, not revealing it's inner mechanism
 * <p>
 * Use it if:
 * > you have a complex data structure, yet you want to hide it's details from client
 * > you need several ways to iterate your data structure
 * > you want to have single interface of data iteration.
 * <p>
 * + Simplifies data storing classes
 * + Allows to implement different iteration ways
 * + Allows to iterate collection in different directions
 * <p>
 * IMPLEMENTATION
 * <p>
 * Создайте общий интерфейс итераторов. Обязательный минимум — это операция получения следующего элемента коллекции.
 * Но для удобства можно предусмотреть и другое. Например, методы для получения предыдущего элемента, текущей позиции,
 * проверки окончания обхода и прочие.
 * <p>
 * Создайте интерфейс коллекции и опишите в нём метод получения итератора. Важно, чтобы сигнатура метода возвращала
 * общий интерфейс итераторов, а не один из конкретных итераторов.
 * <p>
 * Создайте классы конкретных итераторов для тех коллекций, которые нужно обходить с помощью паттерна. Итератор должен
 * быть привязан только к одному объекту коллекции. Обычно эта связь устанавливается через конструктор.
 * <p>
 * Реализуйте методы получения итератора в конкретных классах коллекций. Они должны создавать новый итератор того класса,
 * который способен работать с данным типом коллекции. Коллекция должна передавать ссылку на собственный объект в конструктор итератора.
 * <p>
 * В клиентском коде и в классах коллекций не должно остаться кода обхода элементов. Клиент должен получать новый
 * итератор из объекта коллекции каждый раз, когда ему нужно перебрать её элементы.
 */
public class Iterator {
    public static void main(String[] args) {
        SomeCollection<String> collection = new SomeCollection<>(5);

        for (int i = 0; i < 5; i++) {
            collection.set(i, String.format("%s%s-String-%s%s", i, i, i, i));
        }

        Iter<String> iterator = collection.getIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }

        Iter<String> reverseIterator = collection.getReverseIterator();
        while (reverseIterator.hasNext()) {
            String next = reverseIterator.next();
            System.out.println(next);
        }
    }
}

interface Iter<I> {
    boolean hasNext();

    I next();
}

interface Iterable {
    Iter getIterator();
}

interface ReverseIterable {
    Iter getReverseIterator();
}


class SomeCollection<I> implements Iterable, ReverseIterable {

    static class SomeCollectionIterator<I> implements Iter<I> {

        private SomeCollection<I> collection;
        private int iter = 0;

        SomeCollectionIterator(SomeCollection<I> collection) {
            this.collection = collection;
        }

        @Override
        public boolean hasNext() {
            return iter < collection.innerArray.length;
        }

        @Override
        public I next() {
            return (I) collection.innerArray[iter++];
        }
    }

    static class ReverseIterator<I> implements Iter<I> {

        private SomeCollection<I> collection;
        private int iter;

        ReverseIterator(SomeCollection<I> collection) {
            this.collection = collection;
            this.iter = collection.innerArray.length;
        }

        @Override
        public boolean hasNext() {
            return iter > 0;
        }

        @Override
        public I next() {
            return (I) collection.innerArray[--iter];
        }
    }

    private Object[] innerArray;

    SomeCollection(int size) {
        this.innerArray = new Object[size];
    }

    I get(int index) {
        return index < innerArray.length && index >= 0 ? (I) innerArray[index] : null;
    }

    void set(int index, I item) {
        if (index >= 0 && index < innerArray.length) {
            innerArray[index] = item;
        }
    }

    @Override
    public Iter<I> getIterator() {
        return new SomeCollectionIterator<I>(this);
    }

    @Override
    public Iter<I> getReverseIterator() {
        return new ReverseIterator<I>(this);
    }
}


