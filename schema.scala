import org.apache.spark.sql.types._

val schema = new StructType()
  .add("source_ip", StringType)
  .add("destination_ip", StringType)
  .add("bytes_sent", IntegerType)
  .add("timestamp", LongType)
