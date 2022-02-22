package net.medrag.tasks.twostackcalculator;

import net.medrag.tasks.twostackcalculator.actions.ClosingParenthesis;
import net.medrag.tasks.twostackcalculator.actions.MathematicalAction;
import net.medrag.tasks.twostackcalculator.actions.OpeningParenthesis;
import net.medrag.tasks.twostackcalculator.actions.Symbol;

import java.util.*;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public class Calculator {
    private final Deque<Symbol> symbols;
    private final Deque<Integer> integers;
    private final Map<Character, Symbol> symbolPool;

    private final StringBuilder digitBuffer = new StringBuilder();

    public Calculator(Set<Symbol> symbolSet) {
        symbols = new ArrayDeque<>();
        integers = new ArrayDeque<>();
        symbolPool = new HashMap<>();
        for (Symbol symbol : symbolSet) {
            symbolPool.put(symbol.symbol(), symbol);
        }
    }

    Integer calculateExpression(String expression) {
        for (final char currentChar : expression.toCharArray()) {
            final Symbol symbol = symbolPool.get(currentChar);
            if (symbol == null) {
                digitBuffer.append(currentChar);
            } else {
                flushDigitBuffer();
                Symbol topSymbol = symbols.peekLast();
                while (topSymbol != null && topSymbol.priority() >= symbol.priority() && !(topSymbol instanceof OpeningParenthesis)) {
                    calculate();
                    topSymbol = symbols.peekLast();
                }
                if (symbol instanceof ClosingParenthesis) {
                    symbols.removeLast();
                } else {
                    symbols.add(symbol);
                }
            }
        }
        flushDigitBuffer();
        while (!symbols.isEmpty()) {
            calculate();
        }
        return integers.removeLast();
    }

    private void calculate() {
        MathematicalAction action = (MathematicalAction) symbols.removeLast();
        final Integer last = integers.pollLast();
        final Integer first = integers.pollLast();
        final Integer result = action.calculate(first, last);
        integers.add(result);
    }

    private void flushDigitBuffer() {
        if (!digitBuffer.isEmpty()) {
            integers.add(Integer.parseInt(digitBuffer.toString()));
            digitBuffer.delete(0, digitBuffer.length());
        }
    }
}
