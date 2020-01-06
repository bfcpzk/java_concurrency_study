package threadcoreknowledge.security;

/**
 * 初始化未完毕，就this赋值
 */
public class MultiThreadThisAssignment {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
        //Thread.sleep(10);
        Thread.sleep(105);
        System.out.println(point);
    }
}

class Point{
    final int x,y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        MultiThreadThisAssignment.point = this;
        Thread.sleep(100);
        this.y = y;
    }

    public String toString() {
        return x + "," + y;
    }
}

class PointMaker extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
