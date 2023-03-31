package net.medrag.tasks;

/**
 * Purpose of this task is to determine if two strings can be made equal with one action of replacing one character,
 * addition or removal of one character in one string.
 *
 * @author Stanislav Tretyakov
 * 10.01.2022
 */
public class StringsEqualityComputer {

    boolean compute(String a, String b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        int difference = Math.abs(a.length() - b.length());
        if (difference > 1) return false;
        if (difference == 0) {
            return computeForEqualLength(a, b);
        } else {
            if (a.length() < b.length()) {
                return computeForNonEqualLength(a, b);
            } else return computeForNonEqualLength(b, a);
        }
    }

    private boolean computeForEqualLength(String a, String b) {
        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();
        boolean mismatchMet = false;
        for (int i = 0; i < a.length(); i++) {
            if (charA[i] != charB[i]) {
                if (!mismatchMet) {
                    mismatchMet = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean computeForNonEqualLength(String shortest, String longest) {
        char[] charA = shortest.toCharArray();
        char[] charB = longest.toCharArray();
        boolean mismatchMet = false;
        for (int i = 0, j = 0; i < shortest.length(); i++, j++) {
            if (charA[i] != charB[j]) {
                if (!mismatchMet) {
                    j++;
                    mismatchMet = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
