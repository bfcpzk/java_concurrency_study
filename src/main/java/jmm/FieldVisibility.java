package jmm;

/**
 * 演示可见性问题
 * a = 3, b = 2;
 * a = 1, b = 2;
 * a = 3, b = 3;
 * 可见性问题：
 * a = 1, b = 3
 */
public class FieldVisibility {
    volatile int a = 1;
    volatile int b = 2;

    private void print() {
        System.out.println("b=" + b + ", a=" + a);
    }

    private void change() {
        a = 3;
        b = a;
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.change();
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.print();
            }).start();
        }
    }
}
