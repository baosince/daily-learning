<div align=left><img width="150" height="90" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-logo-trademark.png"/></div>


我常用的 spark-submit
```
spark-submit \
      --conf spark.driver.extraJavaOptions="-Dconfig.resource=xxx.conf" \
      --class app.Main \
      --master yarn-client \
      --executor-cores 4 \
      --driver-memory 2g \
      --executor-memory 8G \
      --num-executors 6 \
      --queue csparkpda \
      $1 $2 $3
```