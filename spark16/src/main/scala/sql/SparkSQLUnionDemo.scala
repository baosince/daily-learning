package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by yidxue on 2018/1/30
  */
object SparkSQLUnionDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    val dataSeq2 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "wenzhou")
    )
    val inputDF2 = sc.parallelize(dataSeq2).toDF("id", "city", "name")

    inputDF1.unionAll(inputDF2).show()

    sc.stop()
  }
}
