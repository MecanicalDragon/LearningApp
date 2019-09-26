package net.medrag.trainings.model;

/**
 * {@author} Stanislav Tretyakov
 * 31.10.2018
 */
public class Student {

    private int age;
    private String name;
    private boolean sex;

    protected String protectedString = "protectedString";
    protected void protectedString(){
        System.out.println(protectedString);
    }

    public Student() {
    }

    public Student(int age, String name, boolean sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
