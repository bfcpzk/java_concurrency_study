package future;

import java.util.concurrent.*;

public class FutureTaskDemo {

    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);
        //1. 另起线程启动
        //new Thread(integerFutureTask).start();
        //2. 线程池启动
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(integerFutureTask);

        try {
            System.out.println("task运行结果：" + integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100 ; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
