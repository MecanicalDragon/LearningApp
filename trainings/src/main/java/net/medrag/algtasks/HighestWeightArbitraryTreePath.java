package net.medrag.algtasks;

import java.util.HashMap;
import java.util.Map;

/**
 * The objective is to find a path with the highest weight in a tree.
 * Additional challenge:
 * - nodes can have negative weight.
 * - path may be arbitrary: from any node to any node.
 *
 * @author Stanislav Tretiakov
 * 04.05.2023
 */
public class HighestWeightArbitraryTreePath {

    private static final Map<Character, Character> PATH = new HashMap<>();
    private static char WINNER;
    private static int BEST = Integer.MIN_VALUE;

    public static void main(String[] args) {
        final var root = buildTree();
        getMaxWeight(root);
        System.out.println(WINNER + ": " + BEST);
        System.out.println(PATH);
    }

    /**
     * Using DFT.
     * <p>
     * Runtime complexity: O(n).
     * Memory consumption: O(n).
     */
    private static int getMaxWeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getMaxWeight(root.left);
        int right = getMaxWeight(root.right);

        int max;
        char child;
        if (left > right) {
            max = left;
            child = root.left == null ? '\u0000' : root.left.name;
        } else {
            child = root.right == null ? '\u0000' : root.right.name;
            max = right;
        }
        final var current = root.weight >= max ? root.weight : max + root.weight;
        PATH.put(root.name, root.weight >= max ? '\u0000' : child);
        if (current > BEST) {
            BEST = current;
            WINNER = root.name;
        }
        return current;
    }

    private static TreeNode buildTree() {
        var a = new TreeNode(-1, 'a');
        var b = new TreeNode(9, 'b');
        var c = new TreeNode(7, 'c');
        var d = new TreeNode(3, 'd');
        var e = new TreeNode(-6, 'e');
        var f = new TreeNode(1, 'f');
        var g = new TreeNode(9, 'g');
        var h = new TreeNode(-5, 'h');
        var i = new TreeNode(8, 'i');
        var j = new TreeNode(7, 'j');
        var k = new TreeNode(11, 'k');

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        f.right = g;
        d.left = h;
        d.right = i;
        e.right = j;
        j.left = k;

        return a;
    }

    private static final class TreeNode {
        final int weight;
        final char name;

        TreeNode left;
        TreeNode right;

        public TreeNode(int weight, char name) {
            this.weight = weight;
            this.name = name;
        }
    }
}
