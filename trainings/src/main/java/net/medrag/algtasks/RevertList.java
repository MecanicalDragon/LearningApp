package net.medrag.algtasks;

/**
 * The task here is revert a one-direction list.
 *
 * @author Stanislav Tretiakov
 * 03.05.2023
 */
public class RevertList {

    public static void main(String[] args) {
        Node head = createList();
        System.out.println(head);
        System.out.println(revert(head));
    }

    /**
     * Runtime complexity: O(n).
     * Memory consumption: O(1).
     */
    private static Node revert(Node head) {
        Node prev = null;
        Node current = head;
        while (current != null) {
            var temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }

    private static Node createList() {
        char start = 'a';
        Node head = new Node(start);

        Node temp = head;
        for (int i = 1; i < 26; i++) {
            final var node = new Node((char) (start + i));
            temp.next = node;
            temp = node;
        }
        return head;
    }

    private static final class Node {
        final char index;
        Node next;

        public Node(char index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return index + " " + (next == null ? "" : next.toString());
        }
    }
}
