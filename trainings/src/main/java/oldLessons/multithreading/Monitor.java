package oldLessons.multithreading;

// Example of monitor object
// "synchronized(this) - "this" means object, which has a synchronized method, so it isn't suits always.
// static synchronized methods use monitor of Object.class
public class Monitor {

public static void main(String[] args) throws InterruptedException {
    Object sync = new Object();         // 1. Creating of monitor object
    Data data = new Data();
    Thread t = new Thread(new WaitingThread(sync, data));
    t.start();
    try{
        System.out.println("main::Sleeping");
        Thread.sleep(500);
    }catch(InterruptedException ex){
        System.err.println("main::Interrupted: "+ex.getMessage());
    }
    synchronized (sync){        // 4. MainThread locks monitor of sync
        System.out.println("main::setting value to 1");
        data.value = 1;
        System.out.println("main::notifying thread");
        sync.notify();      // 5. MainThread notifys random thread, waited on sync object
        Thread.sleep(5000);     // 6. But monitor still locked by MainThread
        System.out.println("main::Thread notified");
    }       // 7. Only now monitor is unlocked, and Thread can continue running
}

    static class Data {
        public int value = 0;
    }

    static class WaitingThread implements Runnable {

        private Object sync;
        private Data data;

        public WaitingThread(Object sync, Data data) {
            this.sync = sync;
            this.data = data;
        }

        public void run() {
            System.out.println("own:: Thread started");
            synchronized(sync) {        // 2. Thread locks monitor of sync
                if (data.value == 0) {
                    try {
                        System.out.println("own:: Waiting");
                        sync.wait();        // 3. Thread unlocks monitor, when waits
                        System.out.println("own:: Running again");
                    } catch (InterruptedException ex) {
                        System.err.println("own:: Interrupted: "+ex.getMessage());
                    }
                }
                System.out.println("own:: data.value = "+data.value);
            }
        }
    }
}
