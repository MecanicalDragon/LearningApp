package net.medrag.tasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 24.06.2020
 */
public class TreeWalker {

    /*
     *                                  N0
     *                                /   \
     *                              N1     N2_________
     *                            / | \          \    \
     *                          N3 N4  N5        N6   N7
     *                        /  \      \          \    \
     *                       N8   N9    N10        N11   N12
     *                                                  /  \
     *                                               N13   N14
     */
    public static void main(String[] args) {

        Node rootNode = new TreeBuilder(15)
                .nextChild(0, 1, 2)
                .nextChild(1, 3, 4, 5)
                .nextChild(2, 6, 7)
                .nextChild(3, 8, 9)
                .nextChild(5, 10)
                .nextChild(6, 11)
                .nextChild(7, 12)
                .nextChild(12, 13, 14)
                .getRoot();

        System.out.println("Print Node by branches:");
        rootNode.printTree(PrintMode.DEPTH_FIRST);
        System.out.println();
        System.out.println("Print Node by branches reversed:");
        rootNode.printTree(PrintMode.DEPTH_FIRST_REVERSE);
        System.out.println();
        System.out.println("Print Node by levels:");
        rootNode.printTree(PrintMode.BREADTH_FIRST);
        System.out.println();
        System.out.println("Print Node by levels reversed:");
        rootNode.printTree(PrintMode.BREADTH_FIRST_REVERSE);
        System.out.println();
    }

    private static class Node {

        private static int counter;

        private final String value;
        private final List<Node> children;

        private Node() {
            value = "NODE-" + counter++;
            children = new ArrayList<>();
        }

        private void printTree(PrintMode printMode) {
            Deque<Node> deque = new ArrayDeque<>();
            deque.add(this);
            while (!deque.isEmpty()) {
                Node n = deque.poll();
                if (n != null) {
                    System.out.println(n.value);
                    if (!n.children.isEmpty()) {
                        switch (printMode) {
                            case BREADTH_FIRST -> deque.addAll(n.children);
                            case BREADTH_FIRST_REVERSE -> {
                                for (int i = n.children.size() - 1; i >= 0; i--) {
                                    deque.add(n.children.get(i));
                                }
                            }
                            case DEPTH_FIRST -> {
                                for (int i = n.children.size() - 1; i >= 0; i--) {
                                    deque.addFirst(n.children.get(i));
                                }
                            }
                            case DEPTH_FIRST_REVERSE -> {
                                for (Node e : n.children) {
                                    deque.addFirst(e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private enum PrintMode {
        DEPTH_FIRST,
        DEPTH_FIRST_REVERSE,
        BREADTH_FIRST,
        BREADTH_FIRST_REVERSE
    }

    private static class TreeBuilder {
        private final List<Node> nodeTree;

        private TreeBuilder(int amount) {
            this.nodeTree = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                nodeTree.add(new Node());
            }
        }

        Node getRoot() {
            return nodeTree.get(0);
        }

        TreeBuilder nextChild(int node, int... children) {
            Node node1 = nodeTree.get(node);
            for (int child : children) {
                node1.children.add(nodeTree.get(child));
            }
            return this;
        }
    }
}
