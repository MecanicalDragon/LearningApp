package oldLessons.multithreading;

import static java.lang.Thread.sleep;

/**
 * {@author} Stanislav Tretyakov
 * 31.01.2020
 */
public class Synch1 {

    static synchronized void go1() throws InterruptedException {
        System.out.println("go1 started");
        sleep(5000);
        System.out.println("go1 finished");
    }

    static synchronized void go2() throws InterruptedException {
        System.out.println("go2 started");
        sleep(5000);
        System.out.println("go2 finished");
    }

    synchronized void go3() throws InterruptedException {
        System.out.println("go3 started");
        sleep(5000);
        System.out.println("go3 finished");
    }

    synchronized void go4() throws InterruptedException {
        System.out.println("go4 started");
        sleep(5000);
        System.out.println("go4 finished");
    }


    public static void main(String[] args) {
//        staticMethods();
//        nonStaticMethods();
        mixedMethods();
    }

    private static void mixedMethods() {
        Synch1 synch = new Synch1();

        Thread thread = new Thread(() -> {
            try {
                synch.go3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                go1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread2.start();
    }

    private static void nonStaticMethods() {
        Synch1 synch = new Synch1();

        Thread thread = new Thread(() -> {
            try {
                synch.go3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                synch.go4();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread2.start();
    }

    private static void staticMethods() {
        Thread thread = new Thread(() -> {
            try {
                go1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                go2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread2.start();
    }
}
