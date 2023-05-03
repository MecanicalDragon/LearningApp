package net.medrag.algtasks;

/**
 * Нужно проверить, являются ли слова анаграммами.
 *
 * @author Stanislav Tretyakov
 * 28.03.2023
 */
public class AnagramChecker {
    private static final String[] SOURCE = new String[]{
            "abba", "Baba",
            "abba", "baka",
            "source", "soceur",
            "source", "sour",
            "tuberz", "rezbut"
    };

    public static void main(String[] args) {
        for (int i = 0; i < SOURCE.length; i += 2) {
            System.out.println(isAnagram(SOURCE[i], SOURCE[i + 1]));
        }
    }

    /**
     * Counting sort usage.
     * <p>
     * Runtime complexity: O(n)
     * Memory consumption: O(1) (O(k), but since k is constant = 26, O(n))
     */
    private static boolean isAnagram(String a, String b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int[] abc = new int[26];
        final var aChars = a.toCharArray();
        for (char aChar : aChars) {
            char c = aChar;
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 'a' + 'A');
            }
            if (c < 'A' || c > 'Z') {
                return false;
            }
            abc[c - 'A']++;
        }
        final var bChars = b.toCharArray();
        for (char bChar : bChars) {
            char c = bChar;
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 'a' + 'A');
            }
            if (c < 'A' || c > 'Z') {
                return false;
            }
            abc[c - 'A']--;
        }
        for (int j : abc) {
            if (j > 0) {
                return false;
            }
        }
        return true;
    }
}
