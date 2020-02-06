package flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟5名运动员等待裁判发令枪，枪声响后，运动员一起出发
 */
public class CountDownLatchDemo2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService services = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5 ; i++) {
            int no = i + 1;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    System.out.println("No." + no + "等待完毕,等待发令枪");
                    try {
                        begin.await();
                        System.out.println("No." + no + "开始跑步");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + no + "到达了终点");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            services.submit(r);
        }
        //裁判检查发令枪
        Thread.sleep(5000);
        System.out.println("发令枪响，比赛开始");
        begin.countDown();

        end.await();
        System.out.println("所有人到达了终点，比赛结束");
    }
}
