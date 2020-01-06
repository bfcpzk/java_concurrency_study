package threadcoreknowledge.stopthreads.volatiledemo;

/**
 * 注意：Thread.interrupted()方法的目标对象是"当前线程"，而不管本方法来自于哪个对象。
 */
public class RightWayInterrupted {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {

                }
            }
        });
        thread1.start();
        thread1.interrupt();

        //获取中断标志
        System.out.println("isInterrupted:" + thread1.isInterrupted());
        //获取中断标志并重置
        System.out.println("thread1.interrupted:" + thread1.interrupted()); //false,与目标线程无关（thread1），与当前线程有关（main）
        //获取中断标志并重置
        System.out.println("Thread.interrupted:" + Thread.interrupted());//false,与目标线程无关（thread1），与当前线程有关（main），main 本身就是false，没被中断
        //获取中断标志
        System.out.println("isInterrupted:" + thread1.isInterrupted());// true，thread1中断，同时恢复成false
    }
}
