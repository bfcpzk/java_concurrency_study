package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  30个线程打印日期，创建30个SimpleDateFormat对象，无线程安全问题。
 */
public class ThreadLocalNormalUsage01 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i < 30 ; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage01().date(finalI);
                    System.out.println(date);
                }
            }).start();
            Thread.sleep(100);
        }
    }

    public String date(int seconds) {
        //单位是毫秒
        Date date = new Date(seconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
