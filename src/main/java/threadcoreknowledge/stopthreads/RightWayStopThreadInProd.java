package threadcoreknowledge.stopthreads;

/**
 * 最佳实践：catch了InterruptedException之后的优先选择：在方法签名中抛出异常，那么在run()就会强制try/catch
 * 一直抛出，只有到最上层run捕获进行中断处理，不要吞掉中断异常。
 */
public class RightWayStopThreadInProd implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("go");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                System.out.println("开始保存日志");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("日志保存完毕");
                break;
            }
        }
        System.out.println("任务中断。。。");
    }

    private void throwInMethod() throws InterruptedException {
        Thread.sleep(200);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        thread.sleep(1000);
        thread.interrupt();
    }
}
