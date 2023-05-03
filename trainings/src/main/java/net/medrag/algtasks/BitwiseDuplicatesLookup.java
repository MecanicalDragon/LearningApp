package net.medrag.algtasks;

/**
 * Given an array of shuffled consequential integers. Find the duplicate.
 * <p>
 * Step 1 : Find “min” and “max” value in the given array. It will take O(n).
 * Step 2 : Find XOR of all integers from range “min” to “max” ( inclusive ).
 * Step 3 : Find XOR of all elements of the given array.
 * Step 4 : XOR of Step 2 and Step 3 will give the required duplicate number.
 * <p>
 * This algorithm works for O(n) of runtime and (1) of memory.
 * But it works only with an array of consequential elements or, in case of chars, with a fixed-length (n + 1) string.
 * In other cases it's better to use counting sort, that can sort an array for O(n) for both runtime and memory.
 *
 * @author Stanislav Tretyakov
 * 01.05.2023
 */
public class BitwiseDuplicatesLookup {
    private static int[] SOURCE_1 = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 17}; // 17
    private static String SOURCE_2 = "qxwertyuioplkjhgfdsazxcvbnm"; // x

    public static void main(String[] args) {
        System.out.println(findDuplicate(SOURCE_1));
        System.out.println(findDuplicate(SOURCE_2));
    }

    private static int findDuplicate(int[] source) {
        int min = source[0];
        int max = source[0];
        int xor = 0;
        int xor2 = 0;
        for (int i : source) {
            if (min > i) {
                min = i;
            }
            if (max < i) {
                max = i;
            }
            xor ^= i;
        }
        for (int i = min; i <= max; i++) {
            xor2 ^= i;
        }
        return xor ^ xor2;
    }

    private static char findDuplicate(String source) {
        final var chars = source.toCharArray();
        int min = chars[0];
        int max = chars[0];
        int xor = 0;
        int xor2 = 0;
        for (char i : chars) {
            if (min > i) {
                min = i;
            }
            if (max < i) {
                max = i;
            }
            xor ^= i;
        }
        for (int i = min; i <= max; i++) {
            xor2 ^= i;
        }
        return (char) (xor ^ xor2);
    }
}

