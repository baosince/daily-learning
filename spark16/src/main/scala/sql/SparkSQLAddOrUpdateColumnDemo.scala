package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{lit, when}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLAddOrUpdateColumnDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", null),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    inputDF
      .select($"id", $"name", $"city")
      .withColumn("city", when($"city".isNull, "hangzhou").otherwise($"city"))   // update column
      .withColumn("date", lit("2018-01-18"))           // add column
      .show()
  }
}