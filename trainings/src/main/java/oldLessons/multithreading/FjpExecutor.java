package oldLessons.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * {@author} Stanislav Tretyakov
 * 31.01.2020
 * https://www.codeflow.site/ru/article/java-fork-join#
 */
public class FjpExecutor {

    public static void main(String[] args) {

        ForkJoinPool executor = ForkJoinPool.commonPool();
        executor.submit(new CustomRecursiveAction("ABC_DEF_GHI_JKL_MNO_PQR_STU_VW_XYZ"));
        ForkJoinTask<String> task = executor.submit(new CustomRecursiveTask("ABC_DEF_GHI_JKL_MNO_PQR_STU_VW_XYZ"));
        try {
            String s = task.get();
            System.out.println("Result is:");
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}

class CustomRecursiveTask extends RecursiveTask<String> {
    private String workload = "";
    private static final int THRESHOLD = 10;

    CustomRecursiveTask(String workload) {
        this.workload = workload;
    }

    @Override
    protected String compute() {
        if (workload.length() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream().map(CustomRecursiveTask::compute).reduce(String::concat).get();
        } else {
            return processing(workload);
        }
    }

    private List<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> subtasks = new ArrayList<>();
        int begin = 0, end = 10;
        while (begin < workload.length()) {
            int realEnd = Math.min(end, workload.length());
            subtasks.add(new CustomRecursiveTask(workload.substring(begin, realEnd)));
            begin += 10;
            end += 10;
        }
        return subtasks;
    }

    private String processing(String work) {
        String result = work.toLowerCase();
        System.out.println("Task result - (" + result + ") - was processed by " + Thread.currentThread().getName());
        return result;
    }
}

class CustomRecursiveAction extends RecursiveAction {

    private String workload = "";
    private static final int THRESHOLD = 10;

    CustomRecursiveAction(String workload) {
        this.workload = workload;
    }

    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workload);
        }
    }

    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subtasks = new ArrayList<>();
        int begin = 0, end = 10;
        while (begin < workload.length()) {
            int realEnd = Math.min(end, workload.length());
            subtasks.add(new CustomRecursiveAction(workload.substring(begin, realEnd)));
            begin += 10;
            end += 10;
        }
        return subtasks;
    }

    private void processing(String work) {
        String result = work.toLowerCase();
        System.out.println("Action result - (" + result + ") - was processed by " + Thread.currentThread().getName());
    }
}
