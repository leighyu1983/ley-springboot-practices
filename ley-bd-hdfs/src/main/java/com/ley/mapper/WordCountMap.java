package com.ley.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.StringTokenizer;

public class WordCountMap extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable intWritable = new IntWritable(1);
	private Text text = new Text();

	@Override
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String line = new String(value.getBytes(), 0, value.getLength(), "UTF-8").trim();
		StringTokenizer st = new StringTokenizer(line);
		while(st.hasMoreTokens()) {
			text.set(st.nextToken());
			context.write(text, intWritable);
		}
	}
}
