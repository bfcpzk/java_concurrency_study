package threadcoreknowledge.startthreads;

public class CannotStartTwice {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.start();
    }
}
