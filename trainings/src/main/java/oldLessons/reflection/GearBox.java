package oldLessons.reflection;

public abstract class GearBox {
    protected int count;

    abstract void nextStep();
    abstract void prevStep();
    abstract void reverse();
    abstract void parking();
    abstract void neutral();

    public GearBox() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "GearBox{" +
                "count=" + count +'}';
    }
}
