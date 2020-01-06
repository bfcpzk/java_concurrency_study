package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 演示AtomicIntegerFieldUpdater的用法
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {
    static Candidate tom = new Candidate();;
    static Candidate peter = new Candidate();;

    private static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 10000 ; i++) {
            peter.score++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    static class Candidate {
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerFieldUpdaterDemo r = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("peter得分：" + peter.score);
        System.out.println("tom得分：" + tom.score);
    }
}
