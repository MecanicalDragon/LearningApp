package net.medrag.algtasks;

import java.util.HashSet;
import java.util.Set;

/**
 * The objective is to find numbers that give specified value being summarized.
 *
 * @author Stanislav Tretyakov
 * 09.02.2022
 */
public class Addenda {

    public static void main(String[] args) {
        int[] array = new int[]{6, 2, -2, 6, 3, 8, 4, 3};
        int[] sorted = new int[]{-19, -15, -10, -7, -4, -2, -1, 0, 1, 3, 4, 8, 12, 18, 20};
        int[] closest = new int[]{-19, -15, -12, -7, -4, -2, 1, 3, 5, 7, 12, 18, 21};
        findAddenda(array, 6);
        System.out.println("__________");
        findAddendaInSortedArray(sorted, 5);
        System.out.println("__________");
        findClosestAddendaInSortedArray(closest, 7);
    }

    /**
     * O(n) runtime complexity
     * O(n) memory complexity
     */
    static void findAddenda(int[] source, int sum) {
        Set<Integer> addendums = new HashSet<>();
        for (int first : source) {
            int second = sum - first;
            if (addendums.contains(second)) {
                System.out.printf("Pair: %s, %s\n", second, first);
            } else {
                addendums.add(first);
            }
        }
    }

    /**
     * Works for sorted array only.
     * <p>
     * O(n*log_n) runtime complexity
     * O(1) memory complexity
     */
    static void findAddendaInSortedArray(int[] source, int sum) {
        for (int i = 0; i < source.length; i++) {
            int second = sum - source[i];
            int start = i + 1;
            int end = source.length - 1;
            while (start <= end) {
                int mid = start + (end - start) / 2;
                if (source[mid] > second) {
                    end = mid - 1;
                } else if (source[mid] < second) {
                    start = mid + 1;
                } else {
                    System.out.printf("Pair: %s, %s\n", source[i], second);
                    break;
                }
            }
        }
    }

    /**
     * Works for sorted array only.
     * <p>
     * O(n) runtime complexity
     * O(1) memory complexity
     */
    static void findClosestAddendaInSortedArray(int[] source, int sum) {
        int start = 0, end = source.length - 1;
        int best1 = source[0], best2 = source[end];
        while (start != end) {
            int currentPairSum = source[start] + source[end];
            if (currentPairSum == sum) {
                best1 = source[start];
                best2 = source[end];
                break;
            } else {
                int lowestDiscrepancy = Math.abs(best1 + best2 - sum);
                int currentDiscrepancy = Math.abs(currentPairSum - sum);
                if (lowestDiscrepancy >= currentDiscrepancy) {
                    best1 = source[start];
                    best2 = source[end];
                }
                if (currentPairSum > sum) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        System.out.printf("Best matching pair: %s, %s\n", best1, best2);
    }
}
