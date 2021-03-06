package core.processOS

/**
  * Created by yidxue on 2018/3/19
  * execute shell script on scala
  */
import com.typesafe.config.{Config, ConfigFactory}
import scala.sys.process._

object ScalaOSDemo1 {
  val conf: Config = ConfigFactory.load()

  def main(args: Array[String]): Unit = {
    val command = "ls -l /Users/"
    command.!!

    val command1 = conf.getString("sqoop.command")
    command1.!
  }
}
