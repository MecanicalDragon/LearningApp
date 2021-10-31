package net.medrag.something.mbean;

/**
 * @author Stanislav Tretyakov
 * 31.10.2021
 */
public class Example implements ExampleMBean{

    private String value;

    public Example(String value) {
        this.value = value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
