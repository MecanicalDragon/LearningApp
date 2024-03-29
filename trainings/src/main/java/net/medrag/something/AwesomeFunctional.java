package net.medrag.something;

/**
 * @author Stanislav Tretyakov
 * 30.06.2020
 */
@FunctionalInterface
public interface AwesomeFunctional {
    public static int STATIC_INT = 5;

    static void method() {
        System.out.println("STATIC");
    }

    void giveTheAnswer();

    //    Since java 9
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

class AwesomeClass implements AwesomeFunctional {

    @Override
    public void giveTheAnswer() {
        System.out.println("Hi! This is an awesome class!");
    }
}
