package sql

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext

object SparkSQLUDFDemo2 {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Scala UDF Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/temperatures.json")
    df.registerTempTable("citytemps")

    // Register the UDF with our SQLContext
    sqlContext.udf.register("CTOF", (degreesCelcius: Double) => (degreesCelcius * 9.0 / 5.0) + 32.0)

    sqlContext.sql("SELECT city, CTOF(avgLow) AS avgLowF, CTOF(avgHigh) AS avgHighF FROM citytemps").show()

    sc.stop()
  }
}
