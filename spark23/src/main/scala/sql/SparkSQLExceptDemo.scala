package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/30
  */
object SparkSQLExceptDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    val dataSeq2 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "wenzhou")
    )
    val inputDF2 = spark.sparkContext.parallelize(dataSeq2).toDF("id", "name", "city")

    // method 1: 求差集
    inputDF1.except(inputDF2).show()

    // method 2: 根据指定字段，求差集
    inputDF1.join(inputDF2, Seq("city"), "leftanti").show()

    spark.stop()
  }
}
