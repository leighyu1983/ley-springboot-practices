package com.ley;


import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.util.HashMap;

public class Application {

	public static void main(String[] args) throws Exception {

		HashMap env = new HashMap<>();
		env.put("HADOOP_USER_NAME", "root");
		env.put("YARN_CONF_DIR", "/ley/programs/hadoop-3.3.0/");

		SparkAppHandle handler = new SparkLauncher(env)
				.setSparkHome("/ley/programs/spark-2.4.6-bin-hadoop2.7")
				.setAppResource("hdfs://cdh101:9000/test/input/ley-bd-spark.jar")
				.setMaster("yarn")
				.setVerbose(true)
				.setMainClass("com.ley.db.spark.MyWordCount")
				.setDeployMode("cluster")
				.setConf("spark.driver.cores", "1")
				.setConf("spark.driver.memory", "1g")
				.setConf("spark.executor.cores", "1")
				.setConf("spark.executor.memory", "1g")
				.startApplication();

		handler.addListener(new SparkAppHandle.Listener(){
			@Override
			public void stateChanged(SparkAppHandle handle) {
				System.out.println("**********  state  changed  ********** ->" + handle.getState().name());
			}

			@Override
			public void infoChanged(SparkAppHandle handle) {
				System.out.println("**********  info  changed  ********** ->" + handle.getState().name());
			}
		});


		while(!"FINISHED".equalsIgnoreCase(handler.getState().toString())){
			System.out.println("id    "+handler.getAppId());
			System.out.println("state "+handler.getState());

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
