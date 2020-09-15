package com.ley.db.spark.service;

import com.ley.db.spark.entity.Person;
import com.ley.db.spark.entity.Product;
import com.ley.db.spark.entity.School;
import com.ley.db.spark.entity.User;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MySparkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MySparkService.class);

	private static final String MYSQL_URL = "jdbc:mysql://cdh101:3306/ley01?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT";
	private static final String USER_NAME = "root";
	private static final String PASSWORD= "123456";
	private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

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
	public void sparkSqlBySparkSubmitHiveWithPartition() {
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
		 *
		 * Exception of .option("path", "/user/hive/warehouse").
		 *  The location of the existing table `default`.`ja03` is `hdfs://cdh101:9000/user/hive/warehouse/ja03`.
		 *  It doesn't match the specified location `/user/hive/warehouse`.;
		 *
		 */
		dataFrame.write().mode(SaveMode.Append).format("hive").partitionBy("year").saveAsTable("ja03");
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
	public void sparkSqlBySparkSubmitHiveNoPartition() {
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

		dataFrame.write().mode(SaveMode.Append).format("hive").saveAsTable("ru04");
		session.sql("select * from ru04").show();
		LOGGER.info("step 4.....finish save");
	}


	/**
	 * Read json file from hdfs, json can be read as datasource like a table.
	 *
	 * createOrReplaceTempView: SparkSession - Temporary views in Spark SQL are session-scoped and will disappear if the session that creates it terminates.
	 * 		Delete the view by calling "dropTempView"
	 * createGlobalTempView: SparkSession.newSession - a temporary view that is shared among all sessions and keep alive until the Spark application terminates,
	 * 					Global temporary view is tied to a system preserved database global_temp,
	 * 					and we must use the qualified name to refer it, e.g. "SELECT * FROM global_temp.view1".
	 *
	 */
	public void sparkSqlQueryFromJson() throws AnalysisException {
		SparkSession session = SparkSession.builder().getOrCreate();
		// hdfs browser /test/input/user_phone.json， ranther than hive....
		Dataset<Row> df = session.read().json("/test/input/user_phone.json");

		/**
		 * dataframe api, show/filter/group
		 */
		// print table on log
		df.show();
		// print name column data on log
		df.select("username").show();

		/**
		 * sql
		 * store json data into table `people`
		 */
		df.createOrReplaceTempView("people");
		Dataset<Row> sqlDF = session.sql("SELECT * FROM people");
		sqlDF.show();

		df.createGlobalTempView("g_people");

		/**
		 * Global temporary view is cross-session
		 * session temporary view
 		 */
		SparkSession newSession = session.newSession();

		// throws exception "Table or view 'people' not found in database 'default'"
		//newSession.sql("SELECT * FROM people").show();

		LOGGER.info("new session print global based........");
		newSession.sql("SELECT * FROM global_temp.g_people").show();

	}

	public void sparkFile2Rdd() throws AnalysisException {
		SparkSession session = SparkSession.builder().getOrCreate();

		JavaRDD<User> userJavaRDD = session.read()
				.textFile("/test/input/user_phone_txt.txt")
				.javaRDD()
				.map(line -> {
					String[] parts = line.split(",");
					User user = new User(Integer.parseInt(parts[0]), parts[1], Long.parseLong(parts[2]));
					return user;
				});

		// Apply a schema to an RDD of JavaBeans to get a DataFrame
		Dataset<Row> userDF = session.createDataFrame(userJavaRDD, User.class);

		// Register the DataFrame as a temporary view
		userDF.createOrReplaceTempView("my_user");

		// SQL statements can be run by using the sql methods provided by spark
		Dataset<Row> teenagersDF = session.sql("SELECT name FROM my_user WHERE phone > 13623345511");

		Encoder<String> stringEncoder = Encoders.STRING();
		Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
				(MapFunction<Row, String>) row -> "Name: " + row.getString(0), stringEncoder);
		teenagerNamesByIndexDF.show();
	}

	/**
	 * Windows: Hadoop, idea, spark dependency in pom
	 * Linux: Spark on Yarn, Hive
	 *
	 Since it is a client... so.....
	 <property>
	   <name>hive.metastore.uris</name>
	   <value>thrift://cdh101:9083</value>
	 </property>
	 <property>
	   <name>fs.defaultFS</name>
	   <value>hdfs://cdh101:9000</value>
	 </property>
	 <property>
	   <name>javax.jdo.option.ConnectionURL</name>
	   <value>jdbc:mysql://cdh101:3306/hive?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;useSSL=false&amp;zeroDateTimeBehavior=convertToNull&amp;serverTimezone=GMT%2B8&amp;createDatabaseIfNotExist=true</value>
	 </property>

	 https://github.com/cdarlint/winutils
	 no hadoop 3.3.0 winutils.exe

	 HADOOP_HOME D:\ProgramFiles\hadoop-3.2.1
	 PATH  %HADOOP_HOME%\bin
	 put winutils.exe  in %HADOOP_HOME%/bin/
	 restart idea
	 /ley/programs/hadoop-3.3.0/bin/hdfs dfs -chmod -R 777 /tmp/hive



	 *
	 * @throws AnalysisException
	 */
	public void sparkFromIdeaRemoteCallHive() throws AnalysisException {
		SparkConf conf = new SparkConf();
		conf.setMaster("local[2]");
		conf.setAppName("good-luck-01");

		SparkSession session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		JavaSparkContext context = new JavaSparkContext(session.sparkContext());

		List<School> schoolList = Arrays.asList(
				new School("张气7", "q7"),
				new School("启涛7", "t7")
		);

		LOGGER.info("step 0..... printed school list is....." + schoolList.toString());
		JavaRDD<School> rdd = context.parallelize(schoolList);
		LOGGER.info("step 1.....rdd");
		Dataset<Row> dataFrame = session.createDataFrame(rdd, School.class);
		LOGGER.info("step 2.....dataFrame");
		dataFrame.write().mode(SaveMode.Append).format("hive").saveAsTable("ru04");
	}

	public void sparkFromIdeaRemoteCallHiveForSearch() throws AnalysisException {
		SparkConf conf = new SparkConf();
		conf.setMaster("local[2]");
		conf.setAppName("good-luck-01");

		SparkSession session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		//session.sql("select * from ru04").show();
		session.sql("select * from ru04 where pwd like 'q%' ").show();
	}

	/**
	 * json has schema;
	 *
	 */
	public void sparkMysqlRWLocalMysqlConnectorJson() {
		SparkConf conf = new SparkConf();
		conf.setAppName("local mysql connector");
		conf.setMaster("local[1]");

		SparkSession session = SparkSession.builder().config(conf).getOrCreate();
		Dataset<Row> jsonDf = session.read().json("/test/input/user_phone.json");

		Encoder<Product> productRowEncoder = Encoders.bean(Product.class);
		Dataset<Product> productDf = jsonDf.map((MapFunction<Row, Product>) row -> {
			Product p = new Product();
			p.setId(Integer.parseInt(row.getAs("id")));
			p.setName(row.getAs("username"));
			p.setDescription(row.getAs("phone") + " ...hoho");
			return p;
		}, productRowEncoder);

		Properties prop = new Properties();
		prop.setProperty("user", USER_NAME);
		prop.setProperty("password", PASSWORD);
		prop.setProperty("driver", DRIVER_MYSQL);

		/**
		 * create table if not exist, and columns are from json field names;
		 *
		 */
		productDf.write().mode(SaveMode.Append).jdbc(MYSQL_URL, "hcin", prop);
	}


	/**
	 * test text file without schema;
	 *
	 */
	public void sparkMysqlRWLocalMysqlConnectorText() {
		SparkConf conf = new SparkConf();
		conf.setAppName("local mysql connector");
		conf.setMaster("local[1]");

		SparkSession session = SparkSession.builder().config(conf).getOrCreate();
		//session.read().textFile("").javaRDD()
		JavaSparkContext context = new JavaSparkContext(session.sparkContext());
		JavaRDD<String> txtRdd = context.textFile("/test/input/user_phone_txt.txt");

		JavaRDD<Product> productRdd = txtRdd.map(row -> {
			Product p = new Product();
			p.setId(Integer.parseInt(row.split(",")[0]));
			p.setName(row.split(",")[1]);
			p.setDescription(row.split(",")[2] + "...nice");
			return p;
		});

		Dataset<Row> productDf = session.createDataFrame(productRdd, Product.class);

		Properties prop = new Properties();
		prop.setProperty("user", USER_NAME);
		prop.setProperty("password", PASSWORD);
		prop.setProperty("driver", DRIVER_MYSQL);

		/**
		 * create table if not exist, and columns are from json field names;
		 *
		 */
		productDf.write().mode(SaveMode.Append).jdbc(MYSQL_URL, "hcin", prop);
	}


	public void sparkMysqlRWRemoteMysqlConnector() {

	}
}
