package oldLessons.human.head;

public class Nose {
    public enum Size{
        BIG,
        MID,
        SMALL
    }

    Size size;

    public Nose() {
    }

    @Override
    public String toString() {
        return "Nose{" +
                "size=" + size +
                '}';
    }
}
