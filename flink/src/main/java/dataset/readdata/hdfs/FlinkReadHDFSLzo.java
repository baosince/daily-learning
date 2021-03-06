package dataset.readdata.hdfs;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.hadoop.mapred.HadoopInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;

/**
 * @author yidxue
 * reference: https://flink.apache.org/news/2014/11/18/hadoop-compatibility.html
 */
public class FlinkReadHDFSLzo {

    private static final String TIMING_PROD_PATH = "hdfs://localhost:9000/user/tmp.lzo";

    private static JobConf getConfiguration() {
        JobConf conf = new JobConf();
        conf.set("io.compression.codecs", "com.hadoop.compression.lzo.LzopCodec");
        return conf;
    }

    public static void main(String[] args) {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        JobConf jmtCallConf = getConfiguration();
        HadoopInputFormat<LongWritable, Text> inputFormat = new HadoopInputFormat<>(new TextInputFormat(), LongWritable.class, Text.class, jmtCallConf);
        TextInputFormat.addInputPath(jmtCallConf, new Path(TIMING_PROD_PATH));
//        DataSet<Tuple2<LongWritable, Text>> rawCall = env.createInput(inputFormat);
        DataSet<Tuple2<LongWritable, Text>> rawCall = env.createInput(inputFormat, TypeExtractor.getInputFormatTypes(inputFormat));

        try {
            System.out.println(rawCall.count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
