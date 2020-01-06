package jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 重排序结果演示，
 * 未重排序
 * a = 1, x = b(0), b = 1, y = a(1)
 * b = 1, y = a(0), a = 1, x = b(1)
 * a = 1, b = 1, x = b(1), y = a(1)
 * 重排序
 * y = a(0), a = 1, x = b(0), b = 1
 *
 * a = 1;
 * x = b;
 * 这两行会发生重排序，因为两行之间没有依赖关系。
 * 如果把变量加上volatile就不会出现重排序的结果
 */
public class OutOfOrderExecution {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0 ; ; ) {
            i++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;

            CountDownLatch latch = new CountDownLatch(1);

            Thread thread1 = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });
            Thread thread2 = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });
            thread2.start();
            thread1.start();
            latch.countDown();
            thread1.join();
            thread2.join();

            String result = "第" + i + "次（" + x + "," + y + ")";

            System.out.println(result);
            if (x == 1 && y == 1) {
                break;
            }
        }
    }
}
