package oldLessons.human.context;

import java.util.List;

public class BodyPart {
    String name;
    String classs;
    List<Property> properties;

    public BodyPart(String name, String classs, List<Property> properties) {
        this.name = name;
        this.classs = classs;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BodyPart{" +
                "name='" + name + '\'' +
                ", classs='" + classs + '\'' +
                ", properties=" + properties +
                '}';
    }
}
