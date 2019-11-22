package oldLessons.multithreading;

public class Abss extends AbstractClass{
    static int i = 10;
    String s;

    public Abss( String s) {

        this.s = s;
    }

    public void run(){
        System.out.println("run");
    }

    void print(){
        System.out.println("ohoho!");
    }

}
