package net.medrag.tasks.twostackcalculator.actions;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public class Plus implements MathematicalAction {
    @Override
    public Integer calculate(Integer first, Integer second) {
        return first + second;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public Character symbol() {
        return '+';
    }
}
