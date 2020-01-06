package threadcoreknowledge.createthreads;

public class BothRunnableThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("我来自Runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("我来自Thread");
            }
        }.start();
    }
}
