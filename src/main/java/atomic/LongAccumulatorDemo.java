package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 演示LongAccumulator的用法
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);
        ExecutorService service = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> service.submit(() -> accumulator.accumulate(i)));
        service.shutdown();
        while (!service.isTerminated()) {

        }
        System.out.println("accumulator结果：" + accumulator.get());
        accumulator.accumulate(10);
        System.out.println(accumulator.getThenReset());
    }
}
