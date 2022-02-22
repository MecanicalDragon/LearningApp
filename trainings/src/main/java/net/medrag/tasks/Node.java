package net.medrag.tasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 24.06.2020
 */
public class Node {

    private static class NodeUtils{
        private final List<Node> nodeTree;

        private NodeUtils(int amount) {
            this.nodeTree = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                nodeTree.add(new Node());
            }
        }

        Node getRootNode() {
            return nodeTree.get(0);
        }

        void children(int node, int... children) {
            Node node1 = nodeTree.get(node);
            for (int child : children) {
                node1.children.add(nodeTree.get(child));
            }
        }
    }

    private static int counter;

    private final String value;
    private final List<Node> children;

    private Node() {
        value = "NODE-" + counter++;
        children = new ArrayList<>();
    }

    private void printTree(boolean byLevels) {
        System.out.println(this.value);
        Deque<Node> deque = new ArrayDeque<>(children);
        while (!deque.isEmpty()) {
            Node n = deque.poll();
            if (n != null) {
                System.out.println(n.value);
                if (!n.children.isEmpty()) {
                    if (byLevels) {
                        deque.addAll(n.children);
                    } else {
                        for (int i = n.children.size() - 1; i >= 0; i--) {
                            deque.addFirst(n.children.get(i));
                        }
//                        Reverse branch order
//                        for (Node e : n.children) {
//                            deque.addFirst(e);
//                        }
                    }
                }
            }
        }
    }

    /**
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

        NodeUtils nodeUtils = new NodeUtils(15);
        nodeUtils.children(0, 1, 2);
        nodeUtils.children(1, 3, 4, 5);
        nodeUtils.children(2, 6, 7);
        nodeUtils.children(3, 8, 9);
        nodeUtils.children(5, 10);
        nodeUtils.children(6, 11);
        nodeUtils.children(7, 12);
        nodeUtils.children(12, 13, 14);

        Node rootNode = nodeUtils.getRootNode();

        System.out.println("Print Node by branches:");
        rootNode.printTree(false);
        System.out.println();
        System.out.println("Print Node by levels:");
        rootNode.printTree(true);
    }
}
