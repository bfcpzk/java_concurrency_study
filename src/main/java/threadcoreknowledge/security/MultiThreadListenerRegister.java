package threadcoreknowledge.security;

/**
 * 观察者模式注册监听器存在的安全隐患
 * 错误：注册了但是count还没有初始化，listener就已经被触发了。
 * 解决方案：利用工厂模式，安全发布对象，先创建listener，再注册。
 */
public class MultiThreadListenerRegister {

    int count;
    EventListener listener;

    private MultiThreadListenerRegister(MySource mySource) {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println("我得到的数字是：" + count);
            }
        };

        for (int i = 0 ; i < 10000 ; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    /**
     * 都先初始化，然后再注册listener，再发布，就能保证线程安全性。
     * @param mySource
     * @return
     */
    public static MultiThreadListenerRegister getInstance(MySource mySource) {
        MultiThreadListenerRegister safeListener = new MultiThreadListenerRegister(mySource);
        mySource.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);//错误：注册了但是count还没有初始化，listener就已经被触发了。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mySource.eventCome(new Event() {
                });
            }
        }).start();
        new MultiThreadListenerRegister(mySource);
    }

    static class MySource {
        private EventListener listener;

        void registerListener(EventListener listener) {
            this.listener = listener;
        }

        public void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("listener还没完成初始化");
            }
        }
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {

    }
}
