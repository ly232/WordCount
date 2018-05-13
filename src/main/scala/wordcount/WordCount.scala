package wordcount

import com.typesafe.scalalogging.Logger
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import java.io.File

object WordCount {

  val logger = Logger(this.getClass.getCanonicalName)

  /**
    * Helper to return the full system path for the sample mapreduce pdf paper.
    *
    * @return Filepath to mapreduce pdf paper.
    */
  private[wordcount] def filePath = {
    val resource = this.getClass.getClassLoader.getResource("mapreduce-osdi04.txt")
    if (resource == null) sys.error("Failed to find resource pdf file.")
    new File(resource.toURI).getPath
  }

  // Initialize handles to spark cluster. Only run in local mode, wiht 4 threads.
  val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName(this.getClass().getCanonicalName())
  val sc: SparkContext = new SparkContext(conf)
  val docRDD: RDD[String] = sc.textFile(filePath).flatMap(_ split ' ').cache()

  def main(args: Array[String]): Unit = {
    // Map-reduce computation flow. Functional style helps avoid boilerplates in setting up mapper and reducer classes.
    val wordCount = docRDD
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .collect()
      .sortBy{ case (word, count) => count }

    wordCount.foreach{ case (word, count) => logger.info(s"$word: $count") }
  }
}
