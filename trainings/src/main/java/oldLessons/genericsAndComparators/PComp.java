package oldLessons.genericsAndComparators;

import java.util.Comparator;

public class PComp implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.age-o2.age;
    }

    public static class PComp2 implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.name.compareTo(o2.name);
        }
    }
}
