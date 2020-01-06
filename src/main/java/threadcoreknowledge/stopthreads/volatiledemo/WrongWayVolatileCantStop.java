package threadcoreknowledge.stopthreads.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 演示用volatile的局限part2，陷入阻塞时，volatile是无法停止线程的
 * 此例中，生产者的生产速度很快，消费者消费速度慢，所以阻塞队列满了以后，
 * 生产者会阻塞，等待消费者进一步消费。
 */
public class WrongWayVolatileCantStop {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);
        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了。");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了。");
        Thread.sleep(1000);
        //一旦不消费了应该让producer停下来，但实际情况停不下来
        //问题在于阻塞到storage.put(); 循环判断条件可能达不到，所以不行。
        producer.canceled = true;
        System.out.println(producer.canceled);
    }
}

class Producer implements Runnable {
    volatile boolean canceled;
    private BlockingQueue storage;

    Producer(BlockingQueue blockingQueue) {
        this.storage = blockingQueue;
    }

    public void run() {
        int num = 0;
        try {
            while (num <= 10000 && !canceled) {
                if (num % 100 == 0) {
                    storage.put(num);
                    System.out.println(num + "是100的倍数, 放到仓库中了。");
                }
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行。");
        }
    }
}

class Consumer {
    BlockingQueue storage;

    Consumer(BlockingQueue blockingQueue) {
        this.storage = blockingQueue;
    }

    boolean needMoreNums() {
        return !(Math.random() > 0.95);
    }
}