package util.oracle

import java.util
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/2/5
  */
object Main {

  def setConf(): util.HashMap[String, String] = {
    val dbConf: util.HashMap[String, String] = new util.HashMap[String, String]
    dbConf.put("USERANME", "xxx")
    dbConf.put("PASSWORD", "xxx")
    dbConf.put("HOSTNAME", "xxx")
    dbConf.put("PORT", "1660")
    dbConf.put("SERVICENAME", "xxx.com")
    dbConf
  }


  def getPredicates(dateToProcess: String): Array[String] =
    Array(
      s"$dateToProcess 00:00:00" -> s"$dateToProcess 02:00:00",
      s"$dateToProcess 02:00:00" -> s"$dateToProcess 04:00:00",
      s"$dateToProcess 04:00:00" -> s"$dateToProcess 06:00:00",
      s"$dateToProcess 06:00:00" -> s"$dateToProcess 08:00:00",
      s"$dateToProcess 08:00:00" -> s"$dateToProcess 09:00:00",
      s"$dateToProcess 09:00:00" -> s"$dateToProcess 10:00:00",
      s"$dateToProcess 10:00:00" -> s"$dateToProcess 11:00:00",
      s"$dateToProcess 11:00:00" -> s"$dateToProcess 12:00:00",
      s"$dateToProcess 12:00:00" -> s"$dateToProcess 13:00:00",
      s"$dateToProcess 13:00:00" -> s"$dateToProcess 13:30:00",
      s"$dateToProcess 13:30:00" -> s"$dateToProcess 14:00:00",
      s"$dateToProcess 14:00:00" -> s"$dateToProcess 14:20:00",
      s"$dateToProcess 14:20:00" -> s"$dateToProcess 14:40:00",
      s"$dateToProcess 14:40:00" -> s"$dateToProcess 15:00:00",
      s"$dateToProcess 15:00:00" -> s"$dateToProcess 15:20:00",
      s"$dateToProcess 15:20:00" -> s"$dateToProcess 15:40:00",
      s"$dateToProcess 15:40:00" -> s"$dateToProcess 16:00:00",
      s"$dateToProcess 16:00:00" -> s"$dateToProcess 16:15:00",
      s"$dateToProcess 16:15:00" -> s"$dateToProcess 16:35:00",
      s"$dateToProcess 16:35:00" -> s"$dateToProcess 17:00:00",
      s"$dateToProcess 17:00:00" -> s"$dateToProcess 17:30:00",
      s"$dateToProcess 17:30:00" -> s"$dateToProcess 18:00:00",
      s"$dateToProcess 18:00:00" -> s"$dateToProcess 18:30:00",
      s"$dateToProcess 18:30:00" -> s"$dateToProcess 19:00:00",
      s"$dateToProcess 19:00:00" -> s"$dateToProcess 19:30:00",
      s"$dateToProcess 19:30:00" -> s"$dateToProcess 20:00:00",
      s"$dateToProcess 20:00:00" -> s"$dateToProcess 20:30:00",
      s"$dateToProcess 20:30:00" -> s"$dateToProcess 21:00:00",
      s"$dateToProcess 21:00:00" -> s"$dateToProcess 21:30:00",
      s"$dateToProcess 21:30:00" -> s"$dateToProcess 22:00:00",
      s"$dateToProcess 22:00:00" -> s"$dateToProcess 23:59:59"
    ).map {
      case (start, end) =>
        s"TIMESTAMP >= TO_DATE('$start','YYYY-MM-DD HH24:MI:SS')" + s"and TIMESTAMP <= TO_DATE('$end','YYYY-MM-DD HH24:MI:SS')"
    }


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RowKey Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val day = "2018-03-07"
    val afterDay = "2018-03-08"

    val tableName = "TEST.wbxeventlog"
    val tableFields = "confid, uid_, gid, TIMESTAMP"

    val dbConf: util.HashMap[String, String] = setConf()

    val query = s"select $tableFields from $tableName where TIMESTAMP>= TO_DATE('$day 00:00', 'yyyy-MM-dd HH24:mi') AND TIMESTAMP <= TO_DATE('$afterDay 00:00', 'yyyy-MM-dd HH24:mi')"
    // 这里必须前后包个括号，并且重命名临时表为 tmp。
    val sql = s"($query) tmp"

    OracleUtils.selectOracleSimpleDF(sqlContext, dbConf = dbConf, sql, predicates = getPredicates(day)).show()
  }
}
