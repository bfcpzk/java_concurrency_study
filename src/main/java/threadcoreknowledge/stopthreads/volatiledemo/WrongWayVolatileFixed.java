package threadcoreknowledge.stopthreads.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 修正volatile的boolean标志位方法，该用interrupt。
 */
public class WrongWayVolatileFixed {

    public static void main(String[] args) throws InterruptedException {
        WrongWayVolatileFixed body = new WrongWayVolatileFixed();
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);
        Producer producer = body.new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = body.new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了。");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了。");
        Thread.sleep(1000);
        //一旦不消费了应该让producer停下来，但实际情况停不下来
        producerThread.interrupt();
    }


    class Producer implements Runnable {
        private BlockingQueue storage;

        Producer(BlockingQueue blockingQueue) {
            this.storage = blockingQueue;
        }

        public void run() {
            int num = 0;
            try {
                while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        storage.put(num);
                        System.out.println(num + "是100的倍数, 放到仓库中了。");
                    }
                    num++;
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("producer中断退出。。。");
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
}

//方法里如果抛出InterruptedException，线程的中断标志位会被复位成false，
//如果确实是需要中断线程，要求我们自己在catch语句块里再次调用interrupt()。

