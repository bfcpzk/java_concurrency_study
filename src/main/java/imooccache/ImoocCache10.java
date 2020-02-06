package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 出于安全性考虑，缓存设置有效期，到期自动失效。如果缓存一直不失效，那么带来缓存不一致的问题。
 * @param <A>
 * @param <V>
 */
public class ImoocCache10<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache10(Computable<A, V> computer) {
        this.computer = computer;
    }

    public V compute(A key) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(key);
            if (f == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computer.compute(key);
                    }
                };
                FutureTask<V> ft = new FutureTask<>(callable);
                f = cache.putIfAbsent(key, ft);//保证了原子性
                if (f == null) {
                    f = ft;
                    System.out.println("从FutureTask调用了计算函数");
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                System.out.println("被取消了");
                cache.remove(key);
                throw e;
            } catch (InterruptedException e) {
                cache.remove(key);
                throw e;
            } catch (ExecutionException e) {
                cache.remove(key);
                System.out.println("计算错误，开始重试");
            }
        }
    }

    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public V computeRandomExpire(A arg) throws InterruptedException {
        long randomExpire = (long) (Math.random()* 10000);
        return compute(arg, randomExpire);
    }

    public V compute(A arg, long expire) throws InterruptedException {
        if (expire > 0) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    expire(arg);
                }
            }, expire, TimeUnit.MILLISECONDS);
        }
        return compute(arg);
    }

    private synchronized void expire(A key) {
        Future<V> future = cache.get(key);
        if (future != null) {
            if (!future.isDone()) {//任务没执行完，直接取消任务
                System.out.println("Future的任务被取消");
                future.cancel(true);
            }
            System.out.println("过期时间到，缓存被清除");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws Exception {
        ImoocCache10<String, Integer> imoocCache10 = new ImoocCache10<>(new MayFail());
        new Thread(() -> {
            try {
                Integer result = imoocCache10.compute("666", 5000L);
                System.out.println("线程1的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache10.compute("666");
                System.out.println("线程3的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache10.compute("667");
                System.out.println("线程2的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(6000);
        Integer result = imoocCache10.compute("666");
        System.out.println("主线程的计算结果：" + result);
        executor.shutdown();
    }
}
