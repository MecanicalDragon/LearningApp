package something;

/**
 * @author Stanislav Tretyakov
 * 14.03.2021
 */
public class CalculatorExtender extends Calculator {

    // CompilationError because of final method in calculator
//    public static Integer multiply(Integer x, Integer y) {
//        return (y == 0) ? 0 : multiply(x, y - 1) + x;
//    }
}
