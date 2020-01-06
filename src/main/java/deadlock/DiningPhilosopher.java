package deadlock;

/**
 * 哲学家就餐死锁问题
 * 解决策略：
 * 1. 避免策略，改变一个哲学家拿叉子的顺序
 */
public class DiningPhilosopher {

    static class Philosopher implements Runnable {
        private final Object leftChopstick;
        private final Object rightChopstick;

        public Philosopher(Object leftChopstick, Object rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    doAction("Thinking");
                    synchronized (leftChopstick) {
                        doAction("Pick up left chopstick");
                        synchronized (rightChopstick) {
                            doAction("Pick up right chopstick - eating");
                            doAction("Put down right chopstick");
                        }
                        doAction("Put down left chopstick");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void doAction(String message) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + ": " + message);
            Thread.sleep((long)(Math.random() * 500));
        }
    }

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] chopsticks = new Object[philosophers.length];
        for (int i = 0 ; i < chopsticks.length ; i++) {
            chopsticks[i] = new Object();
        }
        for (int i = 0 ; i < philosophers.length ; i++) {
            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(chopsticks[(i + 1) % chopsticks.length], chopsticks[i]);
            } else {
                philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            }
            new Thread(philosophers[i], "哲学家" + (i + 1)).start();
        }
    }
}
