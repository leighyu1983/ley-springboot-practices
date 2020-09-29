package com.ley.service;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SparkBusinessEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkBusinessEntry.class);

    public void select() {
        SparkConf conf = new SparkConf();

        SparkSession session = SparkSession.builder().config(conf).getOrCreate();
        Dataset<Row> df = session.read().json("/test/input/user_phone.json");
        df.show();

        LOGGER.info("cool....................");
    }
}
