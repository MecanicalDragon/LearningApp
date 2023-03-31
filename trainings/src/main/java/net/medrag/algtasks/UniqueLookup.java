package net.medrag.algtasks;

/**
 * @author Stanislav Tretyakov
 * 30.03.2023
 */
public class UniqueLookup {
    private static int[] SOURCE_1 = new int[]{1, 0, 3, 42, 7, 8, 16, 845, 0, 845, 8, 16, 7, 3, 1}; // 42
    private static String SOURCE_2 = "abxceqxcqxaeb"; // x
    private static String[] SOURCE_3 = new String[]{"abba", "cat", "chunk", "cat", "bolt", "chunk", "abba"};

    public static void main(String[] args) {
        System.out.println(unique(SOURCE_1));
        System.out.println(unique(SOURCE_2));
        System.out.println(unique(SOURCE_3));
    }

    private static int unique(int[] source) {
        int temp = source[0];
        for (int i = 1; i < source.length; i++) {
            temp = temp ^ source[i];
        }
        return temp;
    }

    private static char unique(String string) {
        final var source = string.toCharArray();
        int temp = source[0];
        for (int i = 1; i < source.length; i++) {
            temp = temp ^ source[i];
        }
        return (char) temp;
    }

    /*
    For strings this approach is useless: it takes O(n*k) of Runtime complexity. It is much easier to brute force this array for O(n^2).
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
