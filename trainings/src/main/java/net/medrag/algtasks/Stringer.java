package net.medrag.algtasks;

import java.util.ArrayDeque;
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
public abstract class Stringer {

    private static final String TEST_1 = "3AI2(Z3gA)"; // AAAIZgggAZgggA
    private static final String TEST_2 = "2(Z3(ix))"; // ZixixixZixixix
    private static final String TEST_3 = "2(z1(K2(A2(X)B3Z2(om))3D))"; // zKAXXBZZZomomAXXBZZZomomDDDzKAXXBZZZomomAXXBZZZomomDDD

    abstract void printString(String string);

    public static void main(String[] args) {
        new RecursiveStringer().printString(TEST_1);
        new StackStringer().printString(TEST_1);
        System.out.println();

        new RecursiveStringer().printString(TEST_2);
        new StackStringer().printString(TEST_2);
        System.out.println();

        new RecursiveStringer().printString(TEST_3);
        new StackStringer().printString(TEST_3);
        System.out.println();
    }

    private static class StackStringer extends Stringer {

        private final Deque<String> tokens = new ArrayDeque<>();

        @Override
        void printString(String string) {
            final char[] chars = string.toCharArray();

            // iterate over string in reverse order
            for (int i = chars.length - 1; i >= 0; i--) {
                char c = chars[i];

                // looking for digit
                while (!Character.isDigit(c)) {
                    tokens.add(Character.toString(c));
                    c = chars[--i];
                }

                // now c is digit
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
                tokens.addLast(token.repeat(Integer.parseInt(Character.toString(c))));
            }

            // retrieve and print all tokens
            while (!tokens.isEmpty()) {
                System.out.print(tokens.removeLast());
            }
            System.out.println();
        }
    }

    private static class RecursiveStringer extends Stringer {

        @Override
        void printString(String string) {
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
        private int printChars(char[] chars, int start, int multiplier) {
            int toReturn = 0;
            for (int m = 0; m < multiplier; m++) {
                for (int i = start; i < chars.length; i++) {
                    final char currentChar = chars[i];
                    if (currentChar == ')') {
                        // we met closing parenthesis, that means, we should not process char array further.
                        // we need to return it to invocation place to proceed with next char.
                        // but first we need to process this chunk <multiplier> times.
                        toReturn = i;
                        break;
                    } else if (Character.isDigit(currentChar)) {

                        // current char is digit. Transform it to multiplication value.
                        final int m2 = Integer.parseInt(Character.toString(currentChar));
                        if (chars[i + 1] == '(') {

                            // we need to multiply chunk <m2> times. Do recursive call.
                            i = printChars(chars, i + 2, m2);
                        } else {

                            // we need to multiply digit. Just process it <m2> times.
                            for (int k = 0; k < m2; k++) {
                                processOutput(chars[i + 1]);
                            }

                            // unscheduled increment to move forward.
                            i++;
                        }
                    } else {

                        // char is letter. We can just process it.
                        processOutput(currentChar);
                    }
                }
            }
            return toReturn;
        }

        /**
         * Process exact char (print or add to buffer, if we need to return String.
         *
         * @param output - char to process.
         */
        private void processOutput(char output) {
            System.out.print(output);
        }
    }
}
