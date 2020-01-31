package oldLessons.reflection2;

interface Able2 {
    int i = 10;
    default void run(){
        System.out.println("another run");
    }
}
