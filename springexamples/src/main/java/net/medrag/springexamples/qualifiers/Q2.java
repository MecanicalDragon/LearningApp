package net.medrag.springexamples.qualifiers;

/**
 * @author Stanislav Tretyakov
 * 13.06.2022
 */
public class Q2 implements QInterface{
    @Override
    public String getString() {
        return "two";
    }
}
