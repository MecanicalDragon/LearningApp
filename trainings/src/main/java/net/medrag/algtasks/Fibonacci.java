package net.medrag.algtasks;

/**
 * //Implement the function that calculates nth Fibonacci number
 * //f(n) = f(n-1) + f(n-2)
 * //f(0) = 0
 * //f(1) = 1
 * //f(2) = 1
 * //f(3) = 2
 * //0,1,1,2,3,5,8,13,...
 *
 * @author Stanislav Tretyakov
 * 05.04.2022
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 30;

        System.out.println(fibonacciRecursive(n));
        System.out.println(fibonacciInt(n));
    }

    //O(2^n) + O(2^n)
    static int fibonacciRecursive(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
        }
    }

    //O(n) + O(1)
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


