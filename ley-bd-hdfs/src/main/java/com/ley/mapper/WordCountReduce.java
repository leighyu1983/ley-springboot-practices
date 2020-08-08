package com.ley.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable intWritable = new IntWritable();

	@Override
	public void reduce(Text text, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		for (IntWritable val : values) {
			count += val.get();
		}
		intWritable.set(count);
		context.write(text, intWritable);
	}
}