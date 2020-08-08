package com.ley.service;

import com.ley.util.JobUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WordCountService {
	SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	String BATCH = df.format(new Date());

	public void wordCount(String jobName, String inputPath) throws InterruptedException, IOException, ClassNotFoundException {
		if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
			return;
		}
		/* 存储路径 */
		String outputPath =  "/0000/" + jobName+"_"+BATCH;
		JobUtil.getWordCountJobsConf(jobName, inputPath, outputPath);
	}
}
