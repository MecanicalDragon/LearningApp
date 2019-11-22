package oldLessons.genericsAndComparators;

import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        Generic<Number> gen = new Generic<>(224);
        gen.setGeneric2(101f);
        gen.setGeneric3(2L);
        gen.show();
        System.out.println("");

        GenQ<Integer>inta = new GenQ<>(52);
        GenQ<Double>intb = new GenQ<>(24d);
        inta.showDelta(intb);
        System.out.println("");

        new MethGen().show("new String", 42);

        Person Dave = new Person("Dave", 29, new Date(),true);
        Person Mary = new Person("Mary", 18, new Date(),false);
        Person Stas = new Person("Stas", 25, new Date(),true);
        Person Anna = new Person("Anna", 23, new Date(),false);
        Person Mark = new Person("Mary", 31, new Date(),true);

        List<Person>persons = new ArrayList<>();
        Collections.addAll(persons, Dave,Mary,Stas,Anna,Mark);
        System.out.println(persons);

        Collections.sort(persons);
        System.out.println(persons);

        persons.sort(new PComp());
        System.out.println(persons);

        //Using Comparator, you don't need to implement Comparable<T>;
        persons.sort(Comparator.comparing(o -> o.sex));
        System.out.println(persons);

        persons.sort(new PComp.PComp2());
        System.out.println(persons);

        for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();){
            iterator.next();
            iterator.remove();
        }
        System.out.println(persons);

    }
}
