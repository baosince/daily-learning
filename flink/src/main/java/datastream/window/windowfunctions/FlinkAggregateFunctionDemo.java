package datastream.window.windowfunctions;

import bean.MyEvent;
import datastream.window.windowfunctions.function.MyAggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/21
 * @author yidxue
 */
public class FlinkAggregateFunctionDemo {

    public static class SelectMess implements KeySelector<MyEvent, String> {
        @Override
        public String getKey(MyEvent w) {
            return w.getMessage();
        }
    }

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置数据源
        DataStream<MyEvent> source = env.fromElements(
            new MyEvent(1, "a", 1534581000),
            new MyEvent(2, "b", 1534581005),
            new MyEvent(8, "a", 1534581010),
            new MyEvent(4, "b", 1534581015),
            new MyEvent(8, "a", 1534581020)
        );

        // 设置水位线
        DataStream<MyEvent> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<MyEvent>(Time.seconds(10)) {
                @Override
                public long extractTimestamp(MyEvent element) {
                    return element.getTimestamp();
                }
            }
        );

        // 窗口聚合
        stream.keyBy(new SelectMess()).window(TumblingEventTimeWindows.of(Time.seconds(1))).aggregate(new MyAggregateFunction()).print();

        env.execute("FlinkWindowAggregateFunctionDemo");
    }
}
