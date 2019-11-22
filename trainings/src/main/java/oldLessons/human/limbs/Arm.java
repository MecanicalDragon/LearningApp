package oldLessons.human.limbs;

public class Arm {
    Cubit cubit;
    Hand hand;
    boolean operable;

    public Arm(){

    }

    @Override
    public String toString() {
        return "Arm{" +
                "cubit=" + cubit +
                ", hand=" + hand +
                ", operable=" + operable +
                '}';
    }
}
