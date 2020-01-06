package synchronized_learn;

public class DisappearRequest1 implements Runnable {
    private int i;

    public DisappearRequest1() {
        this.i = 0;
    }

    public static void main(String[] args) throws InterruptedException {
        DisappearRequest1 dr = new DisappearRequest1();
        Thread t1 = new Thread(dr);
        Thread t2 = new Thread(dr);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(dr.i);
    }

    public void run() {
        for (int j = 0 ; j < 100000 ; j++) {
            i++;
        }
    }
}
