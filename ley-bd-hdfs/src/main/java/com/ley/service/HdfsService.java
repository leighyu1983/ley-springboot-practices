package com.ley.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;


@Service
public class HdfsService {

	@Value("${hdfs.path}")
	private String hdfsPath;

	@Value("${hdfs.user}")
	private String user;

	/**
	 * 获取hdfs配置信息
	 * @return
	 */
	private Configuration getConfiguration(){
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", hdfsPath);
		return configuration;
	}

	/**
	 * 获取文件系统对象
	 * @return
	 */
	public FileSystem getFileSystem() {
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileSystem;
	}
}

