package net.medrag.algtasks;

/**
 * Find an integer in a sorted matrix.
 *
 * @author Stanislav Tretiakov
 * 08.05.2023
 */
public class SortedMatrixSearch {
    private static final int[][] SOURCE = new int[][]{
            new int[]{1, 3, 5, 7, 8, 9, 12, 18, 25, 26, 28, 31, 35, 38, 40},
            new int[]{2, 5, 8, 13, 15, 18, 22, 25, 26, 33, 35, 41, 42, 46, 48},
            new int[]{4, 8, 12, 15, 18, 25, 26, 29, 35, 38, 39, 45, 46, 49, 52},
            new int[]{5, 10, 13, 16, 19, 25, 26, 29, 36, 39, 39, 45, 49, 52, 56},
            new int[]{7, 13, 16, 19, 24, 26, 29, 34, 36, 39, 45, 49, 51, 55, 59},
            new int[]{10, 13, 19, 21, 25, 29, 31, 36, 39, 42, 49, 54, 56, 59, 64},
            new int[]{15, 17, 25, 26, 29, 35, 36, 37, 42, 42, 49, 55, 59, 62, 66},
            new int[]{25, 25, 26, 29, 30, 36, 38, 39, 44, 45, 50, 56, 59, 66, 67},
            new int[]{34, 29, 29, 34, 35, 39, 41, 42, 46, 49, 55, 57, 61, 68, 70},
            new int[]{38, 32, 35, 36, 39, 45, 49, 51, 52, 56, 58, 65, 68, 71, 75},
    };
    private static final int[] INTS = new int[]{29, 45, 56, 57, 6, 23, 60, 90};

    public static void main(String[] args) {
        for (int anInt : INTS) {
            System.out.println(isPresent(SOURCE, anInt));
        }
    }

    /**
     * Runtime complexity: O(m+n).
     * Memory consumption: O(1).
     */
    private static boolean isPresent(int[][] source, int toFind) {
        int x = source[0].length - 1;
        int y = 0;
        int r = source[y][x];
        while (r != toFind && y < source.length && x >= 0) {
            r = source[y][x];
            if (toFind == r) {
                break;
            } else if (toFind < r) {
                x--;
            } else {
                y++;
            }
        }
        return r == toFind;
    }
}
