package net.medrag.tasks.twostackcalculator;

import net.medrag.tasks.twostackcalculator.actions.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public class TwoStackCalculator {

    static String EXPRESSION = "(17+20*(3+40/2-(11+2))*2+1-((25*2)+4)/3)/2";

    public static void main(String[] args) {
        Calculator calculator = new Calculator(Set.of(
                new Minus(),
                new Plus(),
                new Multiply(),
                new Divide(),
                new OpeningParenthesis(),
                new ClosingParenthesis()
        ));
        final Integer result = calculator.calculateExpression(EXPRESSION);
        System.out.println(result);
    }
}

