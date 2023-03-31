package net.medrag.algtasks;

/**
 * Задача, обратная StringDecompression: дана строка из повторяющихся букв - нужно сжать её.
 * Пример: "АААббККККюзз" -> "3А2б4Кю2з"
 * @author Stanislav Tretyakov
 * 28.03.2023
 */
public class StringCompression {

    private static final String SOURCE = "AAAzzzzTTTTTyyKjjjAxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxUUi"; // 3A4z5T2yK3jA39x2Ui

    public static void main(String[] args) {
        System.out.println(compress(SOURCE));
    }

    private static String compress(String source) {

        // '\u0000'
        final var chars = source.toCharArray();
        char[] result = new char[source.length()];
        int pointer = 0;
        int charCounter = 0;
        char previous = chars[0];

        for (int i = 1; i <= chars.length; i++) {
            char c = i == chars.length ? ++chars[i - 1] : chars[i];
            if (i != chars.length && (c < 'A' || (c > 'Z' && c < 'a') || c > 'z')) {
                throw new RuntimeException("String is invalid");
            }
            if (c == previous) {
                charCounter++;
                continue;
            }
            if (charCounter == 0) {
                result[pointer++] = previous;
                previous = c;
                continue;
            }
            final var multiplier = String.valueOf(++charCounter).toCharArray();
            for (int j = 0; j < multiplier.length; j++) {
                result[pointer++] = multiplier[j];
            }
            result[pointer++] = previous;
            previous = c;
            charCounter = 0;
        }
        var fResult = new char[pointer];
        for (int i = 0; i < pointer; i++) {
            fResult[i] = result[i];
        }
        return String.valueOf(fResult);
    }
}
