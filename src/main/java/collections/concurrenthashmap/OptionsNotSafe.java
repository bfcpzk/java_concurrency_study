package collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

public class OptionsNotSafe implements Runnable {
    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        scores.put("小明", 0);

        Thread t1 = new Thread(new OptionsNotSafe(), "线程1");
        Thread t2 = new Thread(new OptionsNotSafe(), "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(scores);
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000 ; i++) {
            //这样做还不如直接用HashMap
            /*synchronized (scores) {
                Integer score = scores.get("小明");
                int newScore = score + 1;
                scores.put("小明", newScore);
            }*/
            //推荐做法
            while (true) {
                Integer score = scores.get("小明");
                int newScore = score + 1;
                boolean b = scores.replace("小明", score, newScore);
                if (b) {
                    break;
                }
            }
        }
    }
}
