package imooccache.computable;

import java.util.concurrent.TimeUnit;

/**
 * 耗时计算的实现类，实现了Computable接口，但是本身不具备缓存能力，不需要考虑缓存的事情
 */
public class ExpensiveFunction implements Computable<String, Integer> {
    @Override
    public Integer compute(String key) throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return Integer.valueOf(key);
    }
}