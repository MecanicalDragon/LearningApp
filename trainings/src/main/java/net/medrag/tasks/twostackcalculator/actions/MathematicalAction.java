package net.medrag.tasks.twostackcalculator.actions;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public interface MathematicalAction extends Symbol{
    Integer calculate(Integer first, Integer second);
}
