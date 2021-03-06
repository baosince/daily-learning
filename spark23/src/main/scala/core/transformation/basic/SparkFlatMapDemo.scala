package core.transformation.basic

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  */
object SparkFlatMapDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd = spark.sparkContext.parallelize(Array("erwin", "caroline"))

    rdd.flatMap(_.toUpperCase).foreach(println(_))
    spark.stop()
  }
}
