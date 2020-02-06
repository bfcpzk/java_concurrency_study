package imooccache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 利用HashMap实现最简单的缓存
 */
public class ImoocCache1 {
    private Map<String, Integer> cache = new HashMap<>();

    public Integer compute(String userId) throws InterruptedException {
        Integer result = cache.get(userId);
        if (result == null) {
            result = doCompute(userId);
            cache.put(userId, result);
        }
        return result;
    }

    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Integer(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        ImoocCache1 cache = new ImoocCache1();
        System.out.println("开始计算了");
        Integer result = cache.compute("13");
        System.out.println("第一次计算结果：" + result);
        result = cache.compute("13");
        System.out.println("第二次计算结果：" + result);
    }
}
