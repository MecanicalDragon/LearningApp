package oldLessons.multithreading;

import java.util.concurrent.TimeUnit;

public class Interrupt {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new ThreadExtender("just a name");
        thread.start();
        TimeUnit.SECONDS.sleep(5);
        thread.interrupt();
        System.out.println("Finished");

    }

    static class ThreadExtender extends Thread {

        ThreadExtender(String name) {
            super(name);
        }

        public void run() {
            while (!interrupted()) {   //interrupted() drops interrupt flag to false
                System.out.println("tik");
                try {
                    // sleep automatically checks "interrupt" when it called
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    // InterruptedException drops interrupt flag to false
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }
    }
}
