package workflow.job.timeout.runable;

import java.util.concurrent.*;

/**
 * @author yidxue
 * 超时退出示例
 * code from: https://stackoverflow.com/questions/2275443/how-to-timeout-a-thread
 * NOTICE: 比demo2的实现方式要好，因为现在监控线程是一个守护线程，被监控线程（TaskRun）是一个用户线程。
 */
public class TimeoutDemo1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(new TaskRun());
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            // mayInterruptIfRunning设成false话，不允许在线程运行时中断，设成true的话就直接终端。
            future.cancel(true);
            System.out.println("it is time out!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}