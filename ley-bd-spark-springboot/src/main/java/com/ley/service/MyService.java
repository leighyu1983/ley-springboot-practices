package com.ley.service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;

@Service
public class MyService implements Serializable {
	@Autowired private transient JavaSparkContext sc;

	private final static String SERVER = "192.168.175.101:9000";

	public void simpleCompute() {
		String inputPath = MessageFormat.format("hdfs://{0}/test/input", SERVER);
		String outputPath = MessageFormat.format("hdfs://{0}/test/output", SERVER);

		nonLambda(inputPath, outputPath);
	}

	private void nonLambda(String inputPath, String outputPath) {
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
}
