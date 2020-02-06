package imooccache.computable;

import java.io.IOException;

public class MayFail implements Computable<String, Integer> {
    @Override
    public Integer compute(String key) throws Exception {
        if (Math.random() > 0.5) {
            throw new IOException("读取文件出错");
        }
        Thread.sleep(3000);
        return Integer.valueOf(key);
    }
}
