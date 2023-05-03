package net.medrag.algtasks;

/**
 * Find unique number (symbol) in an array of paired numbers (symbols).
 * <p>
 * XOR of number and 0 gives the same number.
 * XOR of number and the same number gives 0.
 * XOR is commutative and associative.
 * <p>
 * A commutative operation is a mathematical operation that can be applied to two operands,
 * and the order of the operands does not affect the final result.
 * An associative operation is a mathematical operation that can be applied to three or more operands,
 * and the order of the grouping of the operands does not affect the final result.
 *
 * @author Stanislav Tretyakov
 * 30.03.2023
 */
public class BitwiseUniqueLookup {
    private static int[] SOURCE_1 = new int[]{1, 0, 3, 42, 7, 8, 16, 845, 0, 845, 8, 16, 7, 3, 1}; // 42
    private static String SOURCE_2 = "abxceqxcqxaeb"; // x
    private static String[] SOURCE_3 = new String[]{"abba", "cat", "chunk", "cat", "bolt", "chunk", "abba"};

    public static void main(String[] args) {
        System.out.println(unique(SOURCE_1));
        System.out.println(unique(SOURCE_2));
        System.out.println(unique(SOURCE_3));
    }

    /**
     * Using XOR approach.
     * <p>
     * Runtime complexity: O(n).
     * Memory consumption: O(1).
     */
    private static int unique(int[] source) {
        int temp = source[0];
        for (int i = 1; i < source.length; i++) {
            temp = temp ^ source[i];
        }
        return temp;
    }

    /**
     * Using XOR approach.
     * <p>
     * Runtime complexity: O(n).
     * Memory consumption: O(1).
     */
    private static char unique(String string) {
        final var source = string.toCharArray();
        int temp = source[0];
        for (int i = 1; i < source.length; i++) {
            temp = temp ^ source[i];
        }
        return (char) temp;
    }

    /**
     * For strings this approach is considerable: it takes O(n*k) of Runtime complexity and O(k) of memory consumption where k is max String length.
     * So, if k is significantly smaller than n, it makes sense to use this approach.
     * If not, hash map usage with O(n) of runtime and memory consumption is preferable.
     */
    private static String unique(String[] source) {
        var cache = source[0].toCharArray();
        for (int i = 1; i < source.length; i++) {
            var current = source[i].toCharArray();
            char[] first;
            char[] second;
            if (current.length == cache.length) {
                first = cache;
                second = current;
            } else {
                char[] smaller;
                char[] larger;
                if (current.length > cache.length) {
                    smaller = cache;
                    larger = current;
                } else {
                    smaller = current;
                    larger = cache;
                }
                char[] temp = new char[larger.length];
                for (int i1 = 0; i1 < smaller.length; i1++) {
                    temp[i1] = smaller[i1];
                }
                first = temp;
                second = larger;
            }
            char[] temp = new char[first.length];
            for (int j = 0; j < first.length; j++) {
                temp[j] = (char) (first[j] ^ second[j]);
            }
            cache = temp;
        }
        int length = -1;
        for (int i = 0; i < cache.length; i++) {
            if (cache[i] == '\u0000') {
                length = i;
                break;
            }
        }
        if (length == -1) {
            return String.valueOf(cache);
        }
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = cache[i];
        }
        return String.valueOf(result);
    }
}
