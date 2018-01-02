package com.datastax.spark.connector.rdd

import org.apache.spark.SparkConf
import org.scalatest.{FlatSpec, ShouldMatchers}

class ReadConfTest extends FlatSpec with ShouldMatchers {

  "A ReadConf" should "ignore JoinWithCassandraTable Parameters if readsPerSec is set" in {
    val expected = 50
    val notExpected = 122

    val conf = new SparkConf(true)
      .set(ReadConf.ReadsPerSecParam.name, expected.toString)
      .set(ReadConf.ThroughputJoinQueryPerSecParam.name, notExpected.toString)

    val readConf = ReadConf.fromSparkConf(conf)
    readConf.readsPerSec should be (expected)
  }

  it should "use JoinWithCassandraTable Parameters if readPerSec is not set" in {
    val expected = 493

    val conf = new SparkConf(true)
      .set(ReadConf.ThroughputJoinQueryPerSecParam.name, expected.toString)

    val readConf = ReadConf.fromSparkConf(conf)
    readConf.readsPerSec should be (expected)
  }

  it should "read parallelismLevel as default" in {
    val conf = new SparkConf(true)

    val readConf = ReadConf.fromSparkConf(conf)
    readConf.parallelismLevel should be (512)
  }

  it should "read parallelismLevel from conf" in {
    val expected = 256

    val conf = new SparkConf(true)
      .set(ReadConf.ParallelismLevelParam.name, expected.toString)

    val readConf = ReadConf.fromSparkConf(conf)
    readConf.parallelismLevel should be (expected)
  }

}
