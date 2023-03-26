package net.medrag.algtasks;

import java.util.Arrays;

/**
 * The objective is to build an array of squares in ascending order.
 * The task is solved for O(n) runtime complexity.
 *
 * @author Stanislav Tretyakov
 * 31.03.2022
 */
public class Squares {
    public static void main(String[] args) {
        int[] numbers = {-9, -5, -5, -3, 0, 1, 2, 2, 2, 4, 5, 6, 6, 7, 9};
        System.out.println(Arrays.toString(turnToSquare(numbers)));
    }

    public static long[] turnToSquare(int[] source) {

        if (source == null || source.length == 0) {
            return new long[0];
        }

        int leftPointer = 0, rightPointer = source.length - 1, resultPointer = source.length - 1;
        long[] result = new long[source.length];
        while (leftPointer <= rightPointer) {
            int currentLeft = source[leftPointer];
            int currentRight = source[rightPointer];
            int candidate = Math.abs(currentLeft) > Math.abs(currentRight) ? currentLeft : currentRight;
            result[resultPointer--] = (long) (Math.pow(candidate, 2));
            if (candidate == currentLeft) {
                leftPointer++;
            } else {
                rightPointer--;
            }
        }
        return result;
    }
}
