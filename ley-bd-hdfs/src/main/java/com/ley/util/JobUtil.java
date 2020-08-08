package com.ley.util;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobUtil {
	@Value("${hdfs.path}")
	private String path;
	@Value("${hdfs.ip}")
	private String ip;

	private static String hdfsPath;
	private static String host;


	public static Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", hdfsPath);
		configuration.set("mapred.job.tracker", hdfsPath);
		configuration.set("mapreduce.framework.name", "yarn");
		configuration.set("yarn.resourcemanmager.hostname", host);
		configuration.set("yarn.resourcemanager.address", host+":8032");
		configuration.set("yarn.resourcemanager.scheduler.address", host+":8030");
		configuration.set("mapreduce.app-submission.cross-platform", "true");
		return configuration;
	}

	public static void getWordCountJobsConf(String jobName, String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		/* 获取hdfs配置 */
		Configuration conf = getConfiguration();
		Job job = Job.getInstance(conf, jobName);
		job.setMapperClass(com.ley.mapper.WordCountMap.class);
		job.setCombinerClass(com.ley.mapper.WordCountReduce.class);
		job.setReducerClass(com.ley.mapper.WordCountReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//job.addFileToClassPath(new Path("/0000/lib/ikanalyzer-2012_u6.jar"));

		job.setJar("D:\\CodeBase\\Projects\\ley-springboot-practices\\ley-bd-hdfs\\target\\ley-bd-hdfs-1.0.jar");
		/* 小文件合并 */
		job.setInputFormatClass(CombineTextInputFormat.class);
		CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
		CombineTextInputFormat.setMinInputSplitSize(job, 2 * 1024 * 1024);
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.waitForCompletion(true);
	}

	@PostConstruct
	public void setHhdfsPath() {
		hdfsPath = this.path;
	}

	public static String getHdfsPath() {
		return hdfsPath;
	}
	@PostConstruct
	public void setHost() {
		host = this.ip;
	}

	public static String getHost() {
		return host;
	}
}