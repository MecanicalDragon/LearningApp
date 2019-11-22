package oldLessons.reflection;

public class Car {

    @AutoInject (isRequired = true)
    private Engine engine;
    private GearBox gearBox;

    public Car() {
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", gearBox=" + gearBox +
                '}';
    }
}
