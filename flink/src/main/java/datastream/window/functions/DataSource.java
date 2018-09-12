package datastream.window.functions;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * Created by yidxue on 2018/9/12
 */
public class DataSource extends RichParallelSourceFunction<Tuple3<String, Integer, Long>> {
    private volatile boolean running = true;

    @Override
    public void run(SourceContext<Tuple3<String, Integer, Long>> ctx) throws InterruptedException {

        Tuple3[] elements = new Tuple3[]{
            Tuple3.of("a", 1, 1000000050000L),
            Tuple3.of("a", 2, 1000000054000L),
            Tuple3.of("a", 3, 1000000079900L),
            Tuple3.of("a", 4, 1000000115000L),
            Tuple3.of("b", 5, 1000000100000L),
            Tuple3.of("b", 6, 1000000108000L)
        };

        int count = 0;
        while (running && count < elements.length) {
            ctx.collect(new Tuple3<>((String) elements[count].f0, (Integer) elements[count].f1, (Long) elements[count].f2));
            count++;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}