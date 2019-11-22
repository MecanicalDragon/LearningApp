package oldLessons.genericsAndComparators;

import java.util.Date;

public class Person implements Comparable<Person>{
    public String name;
    int age;
    private Date birth;
    boolean sex;

    Person(String name, int age, Date birth, boolean sex) {
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.sex = sex;
    }

    public String toString(){
        String s = " ";
        return name.concat(s).concat(String.valueOf(age)).concat(s).
                concat(birth.toString()).concat(s).concat(String.valueOf(sex).
                concat("\n"));
    }

    public int compareTo(Person other){
        if (name.compareTo(other.name)==0){
            return age-other.age;
        }
        return name.compareTo(other.name);
    }
}
