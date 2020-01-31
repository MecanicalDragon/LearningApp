package oldLessons.reflection2;

public class AbstractClassExtender extends AbstractClass{
    static int i = 10;
    String s;

    public AbstractClassExtender(String s) {

        this.s = s;
    }

    public void run(){
        System.out.println("run");
    }

    void print(){
        System.out.println("ohoho!");
    }

}
