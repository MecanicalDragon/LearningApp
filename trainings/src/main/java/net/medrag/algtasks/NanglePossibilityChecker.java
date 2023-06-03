package net.medrag.algtasks;

/**
 * Given an array of integers. These integers are lengths of edges of a poly-angle figure. Return a boolean if this figure exists.
 * Figure exists if each edge is less than a sum of all other edges.
 *
 * @author Stanislav Tretiakov
 * 07.05.2023
 */
public class NanglePossibilityChecker {

    private static final int[][] SOURCE = new int[][]{
            new int[]{2, 3, 4},
            new int[]{2, 3, 5},
            new int[]{2, 3, 4, 5, 9},
            new int[]{2, 3, 4, 5, 19},
    };

    public static void main(String[] args) {
        for (int[] ints : SOURCE) {
            System.out.println(isExist(ints));
        }
    }

    private static boolean isExist(int[] edges) {
        int sum = 0;
        int largest = 0;
        for (int edge : edges) {
            sum += edge;
            if (largest < edge) {
                largest = edge;
            }
        }
        return sum - largest > largest;
    }
}
