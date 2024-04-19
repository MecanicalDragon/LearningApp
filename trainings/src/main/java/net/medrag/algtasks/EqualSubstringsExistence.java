package net.medrag.algtasks;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Two strings are given. The task is to find a substring of a required length that exists in both of these strings if any are presented.
 *
 * @author Stanislav Tretiakov
 * 09.05.2023
 */
public class EqualSubstringsExistence {
    private static final int LENGTH = 3;
    private static final String[][][] TESTS = new String[][][]{
        {{"asd", "asd"}, {"asd"}}, // checking equal strings
        {{"asd", "ads"}, {null}}, // checking non-equal strings with the same hash
        {{"asdx", "xasd"}, {"asd"}}, // checking start and end
        {{"wasdx", "asdqw"}, {"asd"}}, // checking middle and start
        {{"asqxq", "xasq"}, {"asq"}}, // checking start and end
        {{"asqxq", "asq"}, {"asq"}}, // checking start and full string
        {{"asq", "xasqq"}, {"asq"}}, // checking full string and end
        {{"qwertyuio", "asdfghtyuklpouu"}, {"tyu"}}, // checking in the middle
        {{"dfgdtreolk", "fgtezxc6olkolk"}, {"olk"}}, // checking end and end
        {{"ujnfghytrfg", "xerfdujnikl"}, {"ujn"}}, // checking start and middle
        {{"ujnfghytrfg", "xerfdjnikl"}, {null}}, // checking none
        {{"ulkijfghiklfg", "xerfdjnikl"}, {"ikl"}}, // checking two indices for a hash
    };

    public static void main(String[] args) {
        for (String[][] test : TESTS) {
            String[] strings = test[0];
            String[] answers = test[1];
            final var answer = findIt(strings[0], strings[1], LENGTH);
            if (Objects.equals(answer, answers[0])) {
                System.out.println(answer);
            } else {
                throw new RuntimeException(String.format("Expected: <%s>. Result: <%s>.", answers[0], answer));
            }
        }
        System.out.println("\nOK!");
    }

    /**
     * Solution implies the sliding window pattern.
     * First, we iterate over the shortest string and compute hashes of all substrings of the required length, and stash them all to a map
     * of hashes against index of the first substring character. We use sliding window to achieve O(n) of computations here.
     * Then we do the same thing with the second string, but on every hash computation we check if this hash is presented in the map.
     * If it is, we iterate over indices of this hash and compare substrings. If we find equal substrings, we return it.
     * Runtime complexity: O(n+m)
     * Memory complexity: O(n)
     */
    private static String findIt(String a, String b, int length) {
        // Return null if strings are shorter than required length of the substring. Check if strings are equal.
        if (a == null || b == null || a.length() < length || b.length() < length) {
            return null;
        }
        if (a.equals(b)) { // do we really need this?
            return a.substring(0, length);
        }

        // Determine the shortest string to save complexity of memory and runtime.
        String shorterString = a.length() < b.length() ? a : b;
        char[] shorter = shorterString.toCharArray();
        char[] longer = (shorterString == a ? b : a).toCharArray();

        // Map of hashes to indices.
        Map<Integer, List<Integer>> hashes = new HashMap<>();

        // Initialize the first substring hash.
        int currentSubstringHash = 0;
        for (int i = 0; i < length; i++) {
            currentSubstringHash += shorter[i];
        }
        hashes.put(currentSubstringHash, new ArrayList<>());
        hashes.get(currentSubstringHash).add(0);

        // Filling the hashes map.
        int lastPossibleSubstringStartIndex = shorter.length - length;
        for (int i = 1; i <= lastPossibleSubstringStartIndex; i++) {
            currentSubstringHash = currentSubstringHash - shorter[i - 1] + shorter[i - 1 + length];
            Integer newStart = i;
            hashes.compute(currentSubstringHash, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(newStart);
                return v;
            });
        }

        // Computing the longer string first substring hash.
        currentSubstringHash = 0;
        for (int i = 0; i < length; i++) {
            currentSubstringHash += longer[i];
        }

        // Getting the starting indices of substring with this hash from the shortest string.
        var candidates = hashes.get(currentSubstringHash);
        String result = checkCandidates(shorter, longer, length, candidates, 0);
        if (result != null) return result;
        lastPossibleSubstringStartIndex = longer.length - length;
        for (int i = 1; i <= lastPossibleSubstringStartIndex; i++) {
            currentSubstringHash = currentSubstringHash - longer[i - 1] + longer[i - 1 + length];
            candidates = hashes.get(currentSubstringHash);
            String result2 = checkCandidates(shorter, longer, length, candidates, i);
            if (result2 != null) return result2;
        }
        return null;
    }

    @Nullable
    private static String checkCandidates(char[] a, char[] b, int length, List<Integer> candidates, int i) {
        if (candidates != null) {
            for (Integer candidate : candidates) {
                String result = compareSubstrings(a, b, candidate, i, length);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private static String compareSubstrings(char[] aa, char[] bb, int startA, int startB, int length) {
        for (int i = startA, j = startB; i < startA + length; i++, j++) {
            if (aa[i] != bb[j]) {
                return null;
            }
        }
        char[] result = new char[length];
        for (int i = 0, k = startA; i < length; i++, k++) {
            result[i] = aa[k];
        }
        return new String(result);
    }
}
