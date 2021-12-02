package supportClasses;

/**
 * @author Stanislav Tretyakov
 * 25.11.2021
 */
public class StaticFail {

    static int x;

    static {
        x = 5 / 0;
    }
}
