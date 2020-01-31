package oldLessons.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Executor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newCachedThreadPool(); // unlimited threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futureList = new ArrayList<Future<String>>();

        for (int i = 0; i < 10; i++) {
            final int finalI = (int) (Math.random() * 5);
            int finalIndex = i;
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("Starting callable " + finalIndex + ". Sleeping: " + finalI + "seconds.");
                    Thread.sleep(1000 * finalI);
                    return "This Future is result of Callable " + finalIndex + ", that has waited for" + finalI + " seconds.";
                }
            });
            futureList.add(future);
        }

        for (Future future : futureList) {
            System.out.println(future.get());
        }

        executorService.shutdown();
    }
}