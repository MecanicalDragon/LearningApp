package net.medrag.tasks;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализовать структуру данных, которая бы имела соответствующие следующим параметрам методы:
 * retrieve() - вернуть элемент, который был добавлен раньше всех остальных, и удалить его из структуры.
 * add(x) - добавить элемент. Если элемент уже добавлен, добавить новый элемент сразу после него.
 * Операции должны выполняться за O(1).
 *
 * @author Stanislav Tretiakov
 * 09.05.2023
 */
public interface CountingQueue<T> {
    void add(T element);
    T retrieve();
}

class SimpleCountingQueue<T> implements CountingQueue<T> {

    private static class Node<T> {
        final T element;
        int counter = 1;
        Node<T> next;

        private Node(T element){
            this.element = element;
        }
    }

    private final Map<T, Node<T>> nodes = new HashMap<>();
    private Node<T> head;
    private Node<T> tail;

    public void add(T element) {
        var newNode = new Node<T>(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
            nodes.put(element, newNode);
        } else {
            var node = nodes.get(element);
            if (node == null) {
                tail.next = newNode;
                tail = newNode;
                nodes.put(element, newNode);
            } else {
                node.counter++;
            }
        }
    }

    public T retrieve(){
        if (head == null) {
            return null;
        }
        if (head.counter == 1){
            var element = head.element;
            nodes.remove(element);
            if (head.next == null) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
            return element;
        } else {
            head.counter--;
            return head.element;
        }
    }
}
