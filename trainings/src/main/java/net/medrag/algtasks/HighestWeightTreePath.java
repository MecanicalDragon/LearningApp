package net.medrag.algtasks;

import java.util.HashMap;
import java.util.Map;

/**
 * The objective is to find a path with the highest weight from the root to a leaf in a tree.
 *
 * @author Stanislav Tretiakov
 * 04.05.2023
 */
public class HighestWeightTreePath {

    private static final Map<Character, Character> PATH = new HashMap<>();

    public static void main(String[] args) {
        final var root = buildTree();
        final var maxWeight = getMaxWeight(root);
        System.out.println(maxWeight);
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
        if (left > right) {
            PATH.put(root.name, root.left == null ? '\u0000' : root.left.name);
            max = left;
        } else {
            PATH.put(root.name, root.right == null ? '\u0000' : root.right.name);
            max = right;
        }
        return max + root.weight;
    }

    private static TreeNode buildTree() {
        var a = new TreeNode(10, 'a');
        var b = new TreeNode(16, 'b');
        var c = new TreeNode(7, 'c');
        var d = new TreeNode(3, 'd');
        var e = new TreeNode(9, 'e');
        var f = new TreeNode(1, 'f');
        var g = new TreeNode(9, 'g');
        var h = new TreeNode(5, 'h');
        var i = new TreeNode(6, 'i');
        var j = new TreeNode(7, 'j');

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        f.right = g;
        d.left = h;
        d.right = i;
        e.right = j;

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
