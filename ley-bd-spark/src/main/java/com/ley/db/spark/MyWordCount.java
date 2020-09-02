package com.ley.db.spark;

import com.ley.db.spark.service.MySparkService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 */
public class MyWordCount {

	private final static String SERVER = "cdh101:9000";

//	static {
//		System.setProperty("HADOOP_USER_NAME", "root");
//		System.setProperty("HADOOP_CONF_DIR", "/ley/programs/hadoop-3.3.0/");
//	}

	public static void main(String[] args) {
		//new MySparkService().sparkSqlBySparkSubmitWithPartition_NOT_WORKING();
		new MySparkService().sparkSqlBySparkSubmitNoPartition();
		//runOnCluster();
		//runOnLocal();
	}

	private static  void runOnCluster() {
		JavaSparkContext sc = new JavaSparkContext();

		String inputPath = MessageFormat.format("hdfs://{0}/test/input/wordcount", SERVER);
		String outputPath = MessageFormat.format("hdfs://{0}/test/output", SERVER);
		sc.parallelize(null, 0);
		nonLambda(sc, inputPath, outputPath);
	}

	private static void runOnLocal() {
		System.setProperty("HADOOP_USER_NAME", "root");
		System.setProperty("HADOOP_CONF_DIR", "/ley/programs/hadoop-3.3.0/");

		SparkConf conf = new SparkConf().setMaster("local")
				.setAppName("ley-bd-spark")
		;
		JavaSparkContext sc = new JavaSparkContext(conf);

		String inputPath = MessageFormat.format("hdfs://{0}/test/input/wordcount", SERVER);
		String outputPath = MessageFormat.format("hdfs://{0}/test/output", SERVER);
		StringBuilder sb;
		StringBuffer sb1;
		nonLambda(sc, inputPath, outputPath);
		// lambda(sc, inputPath, outputPath);
	}

	private static void nonLambda(JavaSparkContext sc, String inputPath, String outputPath) {
		// 创建一个叫lines的RDD
		JavaRDD<String> lines = sc.textFile(inputPath);

		// 过滤掉含有Python的行
		//lines = lines.filter(line -> line.contains("Python"));

		//将文本数据按行处理，每行按空格拆成一个数组,flatMap会将各个数组中元素合成一个大的集合
		//这里需要注意的是FlatMapFunction中<String, String>,第一个表示输入，第二个表示输出
		//与Hadoop中的map-reduce非常相似
		JavaRDD<String> words = lines.flatMap(
				new FlatMapFunction<String, String>() {
					public Iterator<String> call(String x) {
						return Arrays.asList(x.split(" ")).iterator();
					}
				}
		);

		//处理合并后的集合中的元素，每个元素的值为1，返回一个Tuple2,Tuple2表示两个元素的元组
		//值得注意的是上面是JavaRDD，这里是JavaPairRDD，在返回的是元组时需要注意这个区别
		//PairFunction中<String, String, Integer>，第一个String是输入值类型
		//第二第三个，String, Integer是返回值类型
		//这里返回的是一个word和一个数值1，表示这个单词出现一次
		JavaPairRDD<String, Integer> splitFlagRDD = words.mapToPair(
				new PairFunction<String, String, Integer>(){
					public Tuple2<String, Integer> call(String x){
						return new Tuple2(x, 1);
					}
				}
		);

		//reduceByKey会将splitFlagRDD中的key相同的放在一起处理
		//传入的（x,y）中，x是上一次统计后的value，y是本次单词中的value，即每一次是x+1
		JavaPairRDD<String, Integer> countRDD = splitFlagRDD.reduceByKey(
				new Function2<Integer, Integer, Integer>(){
					public Integer call(Integer x, Integer y) {
						return x + y;
					}
				}
		);
		System.out.println(countRDD.collect());
		// 将统计出来的单词总数存入一个文本文件，引发求值
		countRDD.saveAsTextFile(outputPath);
	}

	private static void lambda(JavaSparkContext sc, String inputPath, String outputPath) {
		sc.textFile(inputPath)
				.flatMap(s->Arrays.asList(s.split(" ")).iterator())
				.mapToPair(s->new Tuple2<>(s,1))
				.reduceByKey((x,y)->x+y)
				.saveAsTextFile(outputPath);
	}
}
