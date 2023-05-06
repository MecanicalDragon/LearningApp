package net.medrag.tasks;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Реализовать структуру данных MaxStack, в которой есть методы:
 * pop() – удаляет и возвращает последний добавленный элемент за O(1), кидает исключение или возвращает ошибку, если стек пустой.
 * push(value) – добавляет элемент в стек за O(1).
 * max() – возвращает максимальное значение среди всех элементов стека за O(1), кидает исключение или возвращает ошибку, если стек пустой.
 *
 * @author Stanislav Tretiakov
 * 05.05.2023
 */
public final class MaxStack<T extends Comparable<T>> {

    private static class Node<T> {
        private final T element;
        private final Node<T> nextInOrder;
        private final Node<T> nextMax;

        public Node(T element, Node<T> nextInOrder, Node<T> nextMax) {
            this.element = element;
            this.nextInOrder = nextInOrder;
            this.nextMax = nextMax;
        }
    }

    private Node<T> last = null;
    private Node<T> max = null;

    public T pop() {
        if (last == null) {
            throw new NoSuchElementException();
        } else {
            var toReturn = last;
            last = last.nextInOrder;
            if (max == toReturn) {
                max = max.nextMax;
            }
            return toReturn.element;
        }
    }

    public void push(T element) {
        if (max == null || max.element.compareTo(element) < 0) { // if current max is less
            last = new Node<>(element, last, max);
            max = last;
        } else {
            last = new Node<>(element, last, null);
        }
    }

    public T max() {
        if (max == null) {
            throw new NoSuchElementException();
        } else {
            return max.element;
        }
    }
}

final class ConcurrentMaxStack<T extends Comparable<T>> {

    private static class Node<T> {
        private final T element;
        private final Node<T> next;
        private final T max;

        public Node(T element, Node<T> next, T max) {
            this.element = element;
            this.next = next;
            this.max = max;
        }
    }

    private final AtomicReference<Node<T>> stack = new AtomicReference<>(null);

    public T pop() {
        final var popped = stack.getAndUpdate(node -> {
            if (node == null || node.next == null) {
                return null;
            } else {
                return node.next;
            }
        });
        if (popped == null) {
            throw new NoSuchElementException();
        } else {
            return popped.element;
        }
    }

    public void push(T element) {
        stack.accumulateAndGet(new Node<>(element, null, element), (pushed, current) -> {
            if (current == null) {
                return pushed;
            } else if (pushed.element.compareTo(current.max) > 0) {
                return new Node<>(pushed.element, current, pushed.element);
            } else {
                return new Node<>(pushed.element, current, current.max);
            }
        });
    }

    public T max() {
        final var node = stack.get();
        if (node == null) {
            throw new NoSuchElementException();
        } else {
            return node.element;
        }
    }
}