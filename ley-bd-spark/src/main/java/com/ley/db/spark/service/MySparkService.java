package com.ley.db.spark.service;

import com.ley.db.spark.entity.Person;
import com.ley.db.spark.entity.School;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class MySparkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MySparkService.class);

	/**
	 * create table if not exists ja03(
	 *     name string comment 'this is name',
	 *     pwd string comment 'this is password')
	 * partitioned by (year int comment '年')
	 * row format delimited fields terminated by ',';
	 *
	 * 目录写到了ja03的同级目录
	 * 	ja03
	 * 	year=2029
	 * 	year=2034
	 */
	public void sparkSqlBySparkSubmitWithPartition() {
		SparkConf conf = new SparkConf();
		conf.set("hive.exec.dynamic.partition", "true");
		conf.set("hive.exec.dynamic.partition.mode", "nonstrict");

		SparkSession session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		JavaSparkContext context = new JavaSparkContext(session.sparkContext());

//		session.sql("create table if not exists py01(\n" +
//				"    name string comment 'this is name',\n" +
//				"    pwd string comment 'this is password'\n" +
//				"    )  comment '测试表'\n" +
//				"partitioned by (year int comment '年')\n" +
//				"row format delimited fields terminated by ',' ").show();

		List<Person> personList = Arrays.asList(
				new Person("张燕", "4441", 2029),
				new Person("王涛", "5551", 2034)
				);

		LOGGER.info("step 0..... printed person list is....." + personList.toString());
		JavaRDD<Person> rdd = context.parallelize(personList);
		LOGGER.info("step 1.....rdd");
		Dataset<Row> dataFrame = session.createDataFrame(rdd, Person.class);
		LOGGER.info("step 2.....dataFrame");
		/**
		 * https://spark.apache.org/docs/2.4.6/api/java/org/apache/spark/sql/DataFrameWriter.html#insertInto(java.lang.String)
		 *
		 * insertInto ignores the column names and just uses position-based resolution
		 *
		 */
		dataFrame.write().option("path", "/user/hive/warehouse").mode(SaveMode.Append).format("hive").partitionBy("year").saveAsTable("ja03");
		session.sql("select * from ja03").show();
		LOGGER.info("step 4.....finish save");
	}

	/**
	 create table if not exists ru04(
	 	name string comment 'this is name',
	 	pwd string comment 'this is password')
	 row format delimited fields terminated by ',';

	 /ley/programs/spark-2.4.6-bin-hadoop2.7/bin/spark-submit   --master yarn --files /ley/programs/apache-hive-3.1.2-bin/conf/hive-site.xml   --deploy-mode cluster   --class com.ley.db.spark.MyWordCount  /ley/testdata/ley-bd-spark.jar

	 */
	public void sparkSqlBySparkSubmitNoPartition() {
		SparkConf conf = new SparkConf();
//		conf.set("hive.exec.dynamic.partition", "true");
//		conf.set("hive.exec.dynamic.partition.mode", "nonstrict");

		SparkSession session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		JavaSparkContext context = new JavaSparkContext(session.sparkContext());


		List<School> schoolList = Arrays.asList(
				new School("张勇气2", "q2"),
				new School("启明涛2", "t2")
		);

		LOGGER.info("step 0..... printed school list is....." + schoolList.toString());
		JavaRDD<School> rdd = context.parallelize(schoolList);
		LOGGER.info("step 1.....rdd");
		Dataset<Row> dataFrame = session.createDataFrame(rdd, School.class);
		LOGGER.info("step 2.....dataFrame");

		dataFrame.write().mode(SaveMode.Overwrite).format("hive").saveAsTable("ru04");
		session.sql("select * from ru04").show();
		LOGGER.info("step 4.....finish save");
	}
}
