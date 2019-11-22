package oldLessons.human.context;

public class Property {
    String name;
    String value;
    String[] mods;


    public Property(String name, String value, String[] mods) {
        this.name = name;
        this.value = value;
        this.mods = mods;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
