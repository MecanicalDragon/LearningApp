package something;

/**
 * @author Stanislav Tretyakov
 * 30.06.2020
 */
@FunctionalInterface
public interface AwesomeFunctional {

    static void method() {
        System.out.println("STATIC");
    }

    void giveTheAnswer();

    private String getString() {
        return "IS THIS INTERFACE FUNCTIONAL?";
    }

    default void goDefault() {
        System.out.println("DEFAULT");
    }

    public static void main(String[] args) {
        AwesomeFunctional a = () -> System.out.println("YES, IT IS!");
        System.out.println(a.getString());
        a.giveTheAnswer();
    }
}
