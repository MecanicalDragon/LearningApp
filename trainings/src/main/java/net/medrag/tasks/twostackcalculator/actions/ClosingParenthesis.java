package net.medrag.tasks.twostackcalculator.actions;

/**
 * @author Stanislav Tretyakov
 * 08.02.2022
 */
public class ClosingParenthesis implements Symbol {

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Character symbol() {
        return ')';
    }
}
