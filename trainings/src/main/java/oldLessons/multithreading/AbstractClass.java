package oldLessons.multithreading;

public abstract class AbstractClass implements Able3 {
    public static int i = 5;

    void print(){
        System.out.println("printAbstract");
    }
    void print(int x){
        System.out.println(x);
    }

}
