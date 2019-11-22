package oldLessons.human.limbs;

public class Hand {
    Finger bigFinger;
    Finger showFinger;
    Finger midFinger;
    Finger littleFinger;
    Finger unnamedFinger;

    public Hand(){

    }

    @Override
    public String toString() {
        return "Hand{" +
                "bigFinger=" + bigFinger +
                ", showFinger=" + showFinger +
                ", midFinger=" + midFinger +
                ", littleFinger=" + littleFinger +
                ", unnamedFinger=" + unnamedFinger +
                '}';
    }
}
