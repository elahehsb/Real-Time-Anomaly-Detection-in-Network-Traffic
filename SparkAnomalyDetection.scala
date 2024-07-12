import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

object SparkAnomalyDetection {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Spark Anomaly Detection")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "network_traffic")
      .load()

    val trafficDF = kafkaDF.selectExpr("CAST(value AS STRING)").as[String]
      .select(from_json($"value", schema).as("data"))
      .select("data.*")

    val anomalyDF = trafficDF.withColumn("is_anomaly", when($"bytes_sent" > 8000, 1).otherwise(0))

    val query = anomalyDF.writeStream
      .outputMode("append")
      .format("mongo")
      .option("uri", "mongodb://localhost:27017/network_db.anomalies")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    query.awaitTermination()
  }
}
