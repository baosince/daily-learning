package dataset.sinkData;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.DataSetUtils;

/**
 * Created by yidxue on 2018/2/12
 */
public class FlinkSinkCsvDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataSet<String> in = env.fromElements("A", "B", "C", "D", "E", "F", "G", "H");

        DataSet<Tuple2<Long, String>> result = DataSetUtils.zipWithIndex(in);

        String resultPath = "data/flink/sink";
        result.writeAsCsv(resultPath, "\n", ",");
        env.execute();
    }
}