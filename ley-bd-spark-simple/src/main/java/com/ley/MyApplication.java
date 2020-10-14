package com.ley;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.Arrays;

public class MyApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("hi-1");
        JavaSparkContext sc = new JavaSparkContext(conf);

        LOGGER.info("this is testing program by ley....");

        JavaRDD<String> rdds = sc.parallelize(Arrays.asList("tom", "jerry", "jacky", "tom"));
        rdds.collect();

        JavaPairRDD<String,Integer> pairRdd = sc.parallelizePairs(Arrays.asList(
                new Tuple2<String,Integer>("w1",1),
                new Tuple2<String,Integer>("w2",2),
                new Tuple2<String,Integer>("w3",3),
                new Tuple2<String,Integer>("w2",22),
                new Tuple2<String,Integer>("w1",11)
        ));
        JavaPairRDD<String,Integer> result = pairRdd.reduceByKey((x, y) -> x + y);

        result.foreach(x -> {
            //System.out.println(String.format("%s sum is %d", x._1, x._2));
            LOGGER.info(String.format("%s sum is %d", x._1, x._2));
        });

    }
}
