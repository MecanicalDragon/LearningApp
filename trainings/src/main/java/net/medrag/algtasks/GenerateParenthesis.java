package net.medrag.algtasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate all valid permutations for specified number of parenthesis.
 *
 * @author Stanislav Tretyakov
 * 09.04.2023
 */
public class GenerateParenthesis {

    public static void main(String[] args) {
        int n = 5;
        var results = new ArrayList<String>();
        generate(results, "", n, n);
        for (String result : results) {
            System.out.println(result);
        }
        System.out.println(results.size());
        System.out.println();

        var results2 = new ArrayList<String>();
        generate2(results2, "", n, n);
        for (String result : results2) {
            System.out.println(result);
        }
        System.out.println(results2.size());
        System.out.println();

        generate3("", n, n);
    }

    /**
     * Using backtracking.
     * <p>
     * Runtime complexity: O(2^n).
     * Memory consumption: O(2^n).
     */
    private static void generate(List<String> results, String current, int left, int right) {
        if (right > left) {
            generate(results, current + ")", left, right - 1);
        }
        if (left > 0) {
            generate(results, current + "(", left - 1, right);
        } else if (right == 1) {
            results.add(current + ")");
        }
    }

    /**
     * Using backtracking with different attempts order.
     * <p>
     * Runtime complexity: O(2^n).
     * Memory consumption: O(2^n).
     */
    private static void generate2(List<String> results, String current, int openingUnused, int closingUnused) {
        if (openingUnused > 0) {
            generate2(results, current + "(", openingUnused - 1, closingUnused);
        }
        if (openingUnused == 0 && closingUnused == 1) {
            results.add(current + ")");
        } else if (closingUnused > openingUnused) {
            generate2(results, current + ")", openingUnused, closingUnused - 1);
        }
    }

    /**
     * Using backtracking with direct output.
     * <p>
     * Runtime complexity: O(2^n).
     * Memory consumption: O(n).
     */
    private static void generate3(String current, int openingUnused, int closingUnused) {
        if (openingUnused > 0) {
            generate3(current + "(", openingUnused - 1, closingUnused);
        }
        if (openingUnused == 0 && closingUnused == 1) {
            System.out.println(current + ")");
        } else if (closingUnused > openingUnused) {
            generate3(current + ")", openingUnused, closingUnused - 1);
        }
    }
}
