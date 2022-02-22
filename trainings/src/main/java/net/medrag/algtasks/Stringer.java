package net.medrag.algtasks;

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

    private static final char P1 = '(';
    private static final char P2 = ')';

    private static final String TEST_1 = "3AB2(Z3K)";
    private static final String TEST_2 = "2(Z3(KA))";

    abstract void printString(String string);

    public static void main(String[] args) {
        new RecursiveStringer().printString(TEST_1);
        new StackStringer().printString(TEST_1);

        new RecursiveStringer().printString(TEST_2);
        new StackStringer().printString(TEST_2);
    }

    private static class StackStringer extends Stringer {

        @Override
        void printString(String string) {
        }
    }

    private static class RecursiveStringer extends Stringer {

        @Override
        void printString(String string) {
            printChars(string.toCharArray(), 0, 1);
            System.out.println();
        }

        private int printChars(char[] chars, int start, int multiplier) {
            int toReturn = 0;
            for (int i = 0; i < multiplier; i++) {
                for (int j = start; j < chars.length; j++) {
                    final char currentChar = chars[j];
                    if (currentChar == P2) {
                        toReturn = j;
                        break;
                    } else if (Character.isDigit(currentChar)) {
                        final int m2 = Integer.parseInt(Character.toString(currentChar));
                        if (chars[j + 1] == P1) {
                            j = printChars(chars, j + 2, m2);
                        } else {
                            for (int k = 0; k < m2; k++) {
                                System.out.print(chars[j + 1]);
                            }
                            j++;
                        }
                    } else {
                        System.out.print(currentChar);
                    }
                }
            }
            return toReturn;
        }
    }
}
