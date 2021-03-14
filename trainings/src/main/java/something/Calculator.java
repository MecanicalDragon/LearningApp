package something;

/**
 * @author Stanislav Tretyakov
 * 14.03.2021
 */
public class Calculator {

    // method to refactor
    public static final Integer multiply(Integer x, Integer y) {
        return (y == 0) ? 0 : multiply(x, y - 1) + x;
    }
}
