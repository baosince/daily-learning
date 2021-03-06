package core.trydemo

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Created by yidxue on 2018/3/8
  * code from : https://alvinalexander.com/scala/try-success-failure-example-reading-file
  */
object ScalaTryMatchDemo {
  def main(args: Array[String]): Unit = {
    def readTextFile(filename: String): Try[List[String]] = {
      Try(Source.fromFile(filename).getLines.toList)
    }

    val filename = "data/data"
    readTextFile(filename) match {
      case Success(lines) => lines.foreach(println)
      case Failure(f) => println(f)
    }
  }
}
