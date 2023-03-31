package net.medrag.algtasks;

/**
 * Есть строка, в которой через пробел записаны слова.
 * Нужно без использования дополнительной памяти (in-place) переставить слова в обратном порядке.
 * Пример: "apple bounce fish possess" -> "possess fish bounce apple".
 *
 * @author Stanislav Tretyakov
 * 28.03.2023
 */
public class StringReverser {

    private static final String[] SOURCE = new String[]{
            "apple bounce fish possess",
            "good god demon barbershop",
            "pick color regular tank saint",
    };

    //Задача решается 2 указателями в 2 этапа:
    // 1. Идти по массиву и переворачивать слова, определяя границы пробелами.
    // 2. Перевернуть всю строку.
    public static void main(String[] args) {
        for (String s : SOURCE) {
            System.out.println(revert(s));
        }
    }

    private static String revert(String source) {
        final var chars = source.toCharArray();
        revert(chars, 0, chars.length);
        for (int i = 0, j = 0; i <= chars.length; i++) {
            if (i == chars.length || chars[i] == ' ') {
                revert(chars, j, i);
                j = ++i;
            }
        }
        return String.valueOf(chars);
    }

    private static void revert(char[] chars, int start, int end) {
        for (int left = start, right = end - 1; left < right; left++, right--) {
            final var temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
    }
}
