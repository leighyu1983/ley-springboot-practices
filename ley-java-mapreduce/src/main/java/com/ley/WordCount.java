package com.ley;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * hdfs dfs -mkdir -p /001
 * hdfs dfs -put test.txt /001
 * hdfs dfs -ls /001
 * hdfs dfs -cat /root/output/part-r-00000
 *
 * hadoop jar ./ley-java-mapreduce-1.0.jar <hdfs中 待分析的文件或者目录路径> <hdfs中 结果输出目录>
 *
 */
public class WordCount {
	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		private IntWritable one = new IntWritable();
		private Text word = new Text();

		/**
		 * key 偏移量包括了回车所占的字符数(Windows和Linux环境会不同)
		 * value 一行数据
		 * context存储新Map的对象
		 */
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				one.set(1);
				context.write(word, one);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		/**
		 * key 为Map中的key，hadoop会把相同key的内容合并为一个list，该list就为values。
		 * context为存放结果的对象
		 */
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
				InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
