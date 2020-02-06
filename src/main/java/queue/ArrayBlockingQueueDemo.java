package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 利用ArrayBlockingQueue实现面试场景
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> candidates = new ArrayBlockingQueue<>(3);
        new Thread(new Interviewer(candidates)).start();
        new Thread(new Consumer(candidates)).start();
    }
}

class Interviewer implements Runnable {
    private BlockingQueue<String> queue;

    public Interviewer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("10个候选人都来了");
        for (int i = 0; i < 10 ; i++) {
            String candidate = "Candidate" + i;
            try {
                queue.put(candidate);
                System.out.println("安排好了" + candidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String msg;
        try {
            while(!(msg = queue.take()).equals("stop")){
                System.out.println(msg + "到了");
                Thread.sleep(1000);
            }
            System.out.println("10位候选人都面试完了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}