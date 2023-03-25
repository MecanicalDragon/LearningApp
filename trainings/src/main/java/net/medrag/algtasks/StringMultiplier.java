package net.medrag.algtasks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Дана строка из латинских заглавных букв, цифр и скобок.
 * Скобочные выражения всегда верные.
 * За цифрой всегда стоит или буква или скобочное выражение.
 * Скобочные выражения могут быть вложенные.
 * Надо преобразовать в строку умножая выражение за цифрой
 * <p>
 * 3AB2(Z3K) → AAABZKKKZKKK
 * <p>
 * 2(Z3(KA)) → ZKAKAKAZKAKAKA
 *
 * @author Stanislav Tretyakov
 * 09.02.2022
 */
public class StringMultiplier {

    private static String[] STRINGS = {
            "ag",
            "1AG2Bx", // AGBBx
            "1(AG)2(c)", // AGcc
            "az3AI2(Z3gA)", // azAAAIZgggAZgggA
            "12(Z10x)", // (Zxxxxxxxxxx) x 12
            "2(z1(K2(A2(X)B3Z2(om))3D))" // zKAXXBZZZomomAXXBZZZomomDDDzKAXXBZZZomomAXXBZZZomomDDD
    };

    public static void main(String[] args) {

        for (String string : STRINGS) {
            printRecursively(string);
            printRecursively2(string);
            printTokenized(string);
            printNotched(string);
            System.out.println();
        }
    }

    public static void printNotched(String string) {

        final char[] chars = string.toCharArray();
        final int[] notches = new int[string.length() / 4];
        Arrays.fill(notches, -1);

        boolean par = false;

        StringBuilder resultReversed = new StringBuilder();
        // 1. iterate string
        for (int i = string.length() - 1; i >= 0; i--) {

            char ch = chars[i];
            if (Character.isDigit(ch)) {
                // if we meet a number, aggregate it and multiply everything before index, remove index
                StringBuilder digit = new StringBuilder(Character.toString(ch));
                int ii = i - 1;
                while (ii >= 0 && Character.isDigit(chars[ii])) {
                    digit.insert(0, chars[ii--]);
                }
                final var aDigit = Integer.parseInt(digit.toString()) - 1;
                if (par) {
                    int notchIndex = 0;
                    for (int j = 1; j < notches.length; j++) {
                        if (notches[j] == -1) {
                            notchIndex = notches[j - 1];
                            notches[j - 1] = -1;
                            break;
                        }
                    }
                    String appender = resultReversed.substring(notchIndex);
                    for (int j = 0; j < aDigit; j++) {
                        resultReversed.append(appender);
                    }
                    par = false;
                } else {
                    // if we meet number without p., just duplicate the last letter.
                    char appender = resultReversed.charAt(resultReversed.length() - 1);
                    for (int j = 0; j < aDigit; j++) {
                        resultReversed.append(appender);
                    }
                }
                i = ++ii;
            } else if (ch == ')') {
                // if we meet p., stash a notch about it with index.
                for (int j = 0; j < notches.length; j++) {
                    if (notches[j] == -1) {
                        notches[j] = resultReversed.length();
                        break;
                    }
                }
            } else if (ch == '(') {
                // next must be a digit.
                par = true;
            } else {
                // a letter here.
                resultReversed.append(ch);
            }
        }
        // reverse string.
        System.out.println(resultReversed.reverse());
    }

    public static void printTokenized(String string) {
        final char[] chars = string.toCharArray();
        final Deque<String> tokens = new ArrayDeque<>();

        // iterate string in reverse order
        for (int i = chars.length - 1; i >= 0; i--) {

            // looking for digit
            while (i >= 0 && !Character.isDigit(chars[i])) {
                tokens.add(Character.toString(chars[i--]));
            }
            int multiplier;
            if (i >= 0) {
                final var numberBuilder = new StringBuilder();
                while (i >= 0 && Character.isDigit(chars[i])) {
                    numberBuilder.insert(0, chars[i--]);
                }
                multiplier = numberBuilder.isEmpty() ? 1:Integer.parseInt(numberBuilder.toString());
                if (i >= 0) {
                    i++;
                }
            } else {
                multiplier = 1;
            }

            String token = tokens.removeLast();
            if (token.equals("(")) {
                // retrieve and append tokens until ')' is met
                final StringBuilder sb = new StringBuilder();
                String nextToken = tokens.removeLast();
                while (nextToken != null && !nextToken.equals(")")) {
                    sb.append(nextToken);
                    nextToken = tokens.pollLast();
                }
                // add new token, built with all retrieved between parenthesis
                token = sb.toString();
            }
            // build new token, multiplying last one
            tokens.addLast(token.repeat(multiplier));
        }
        // retrieve and print all tokens
        while (!tokens.isEmpty()) {
            System.out.print(tokens.removeLast());
        }
        System.out.println();
    }

    public static void printRecursively(String string) {
        printChars(string.toCharArray(), 0, 1);
        System.out.println();
    }

    /**
     * Process char array.
     *
     * @param chars      - char array.
     * @param start      - starting index in char array.
     * @param multiplier - how many times to process this array.
     * @return - index of last processed char in char array.
     */
    private static int printChars(char[] chars, int start, int multiplier) {
        int toReturn = 0;
        for (int m = 0; m < multiplier; m++) {
            for (int i = start; i < chars.length; i++) {
                char currentChar = chars[i];
                if (currentChar == ')') {
                    // we met closing parenthesis, that means, we should not process char array further.
                    // we need to return it to invocation place to proceed with next char.
                    // but first we need to process this chunk <multiplier> times.
                    toReturn = i;
                    break;
                } else if (Character.isDigit(currentChar)) {
                    // build multi-digit number
                    final var numBuilder = new StringBuilder();
                    while (Character.isDigit(currentChar)) {
                        numBuilder.append(currentChar);
                        currentChar = chars[++i];
                    }
                    // current char is digit. Transform it to multiplication value.
                    final int m2 = Integer.parseInt(numBuilder.toString());
                    if (chars[i] == '(') {
                        // we need to multiply chunk <m2> times. Do recursive call.
                        i = printChars(chars, i + 1, m2);
                    } else {
                        // we need to multiply digit. Just process it <m2> times.
                        for (int k = 0; k < m2; k++) {
                            System.out.print(chars[i]); // process output
                        }
                    }
                } else {
                    // char is letter. We can just process it.
                    System.out.print(currentChar); // process output
                }
            }
        }
        return toReturn;
    }

    public static void printRecursively2(String string) {
        System.out.println(transform(string.toCharArray(), 0, string.length()));
    }

    /**
     * Transform char array.
     *
     * @param chars - char array.
     * @return - transformed string.
     */
    private static char[] transform(char[] chars, int start, int end) {
        final var resultBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            int multiplier = 1;
            if (Character.isDigit(chars[i])) {
                final var numBuilder = new StringBuilder();
                while (Character.isDigit(chars[i])) {
                    numBuilder.append(chars[i++]);
                }
                multiplier = Integer.parseInt(numBuilder.toString());
                int nextStart = chars[i] == '(' ? i + 1 : i;
                int nextEnd = nextStart + 1;
                if (chars[i] == '(') {
                    for (int j = i + 2, c = 1; j < end; j++) {
                        if (chars[j] == ')') {
                            c--;
                        } else if (chars[j] == '(') {
                            c++;
                        }
                        if (c == 0) {
                            nextEnd = j;
                            i = j;
                            break;
                        }
                    }
                } else {
                    i = nextEnd - 1;
                }

                final var transformed = transform(chars, nextStart, nextEnd);
                for (int j = 0; j < multiplier; j++) {
                    resultBuilder.append(transformed);
                }
            } else if (chars[i] != ')') {
                resultBuilder.append(chars[i]);
            }
        }
        return resultBuilder.toString().toCharArray();
    }
}
