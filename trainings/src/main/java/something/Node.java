package something;

import java.util.*;

/**
 * @author Stanislav Tretyakov
 * 24.06.2020
 */
public class Node {

    private static int counter;

    private String value;
    private List<Node> children;

    private Node() {
        value = "NODE-" + ++counter;
        children = new ArrayList<>();
    }

    private void printTree(boolean byLevels) {
        System.out.println(this.value);
        Deque<Node> deque = new ArrayDeque<>(children);
        while (!deque.isEmpty()) {
            Node n = deque.poll();
            if (n != null) {
                System.out.println(n.value);
                if (n.children != null && !n.children.isEmpty()) {
                    if (byLevels) {
                        deque.addAll(n.children);
                    } else {
                        for (Node e : n.children) {
                            deque.addFirst(e);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();
        Node n7 = new Node();
        Node n8 = new Node();
        Node n9 = new Node();
        n1.children.addAll(List.of(n2, n3));
        n2.children.addAll(List.of(n4, n5));
        n3.children.addAll(List.of(n6, n7));
        n6.children.addAll(List.of(n8, n9));
        n1.printTree(false);
        System.out.println("=====");
        n1.printTree(true);
    }
}
