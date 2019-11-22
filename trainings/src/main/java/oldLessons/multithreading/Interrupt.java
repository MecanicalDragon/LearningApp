package oldLessons.multithreading;

import java.util.concurrent.TimeUnit;

public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
            Thread t = new Thread("ololo"){
              public void run(){
                  while (!interrupted()){   //interrupted() drops interrupt flag to false
                      System.out.println("tik");
                      try {
                          TimeUnit.MILLISECONDS.sleep(500);
                          // sleep automatically checks "interrupt" when it called
                      } catch (InterruptedException e) {
                          // InterruptedException drops interrupt flag to false
                          return;
                      }
                  }
              }
            };
            t.start();
            TimeUnit.SECONDS.sleep(5);
            t.interrupt();
        System.out.println("Finished");
    }
}
