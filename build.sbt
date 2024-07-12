name := "SparkAnomalyDetection"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.1.1",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.1.1",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1"
)
