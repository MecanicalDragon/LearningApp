package net.medrag.algtasks;

/**
 * Implement the function that calculates nth Fibonacci number.
 * <p>
 * f(n) = f(n-1) + f(n-2)
 * f(0) = 0; f(1) = 1; f(2) = 1; f(3) = 2 ... f(10) = 55
 * Fibonacci row:
 * 0,1,1,2,3,5,8,13,21,34,55...
 *
 * @author Stanislav Tretyakov
 * 05.04.2022
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 15;
        System.out.println(fibonacciRecursive(n));
        System.out.println(fibonacciInt(n));
    }

    /**
     * Using brute force recursive approach.
     * <p>
     * Runtime complexity: O(2^n).
     * Since leaves of each tree work for O(1), the real runtime complexity is O(1.6^2)
     * Memory consumption: O(n).
     */
    static int fibonacciRecursive(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
        }
    }

    /**
     * Using dynamic programming (caching).
     * <p>
     * Runtime complexity: O(n).
     * Memory consumption: O(1).
     */
    static int fibonacciInt(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            int cache1 = 0;
            int cache2 = 1;
            int result = 0;

            for (int i = 2; i <= n; i++) {
                result = cache1 + cache2;
                cache1 = cache2;
                cache2 = result;
            }

            return result;
        }
    }
}


