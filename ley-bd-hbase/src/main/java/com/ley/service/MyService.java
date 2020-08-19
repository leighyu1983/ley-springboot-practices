package com.ley.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;


@Service
public class MyService {

	private final static String TABLE_NAME = "student";
	private final static String COLUMN_FAMILY_GRADES = "grades";
	private final static String COLUMN_NAME = "name";
	private final static String COLUMN_AGE = "age";

	public void test() throws Exception {

		Configuration conf = HBaseConfiguration.create();
		conf.set(HConstants.ZOOKEEPER_QUORUM, "192.168.175.101");
		conf.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");

		// connect to table
		Connection connection = ConnectionFactory.createConnection(conf);
		Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
		table.close();

		// create record r1 with
		String rowKey = "r1";
		Put record = new Put(Bytes.toBytes(rowKey));
		record.addColumn(Bytes.toBytes(COLUMN_FAMILY_GRADES), Bytes.toBytes(COLUMN_NAME), Bytes.toBytes("hello world for" + rowKey));
		record.addColumn(Bytes.toBytes(COLUMN_FAMILY_GRADES), Bytes.toBytes(COLUMN_AGE), Bytes.toBytes(13));
		table.put(record);

		// search record r1 with row key
		Get getRowKey = new Get(Bytes.toBytes(rowKey));
		Result result = table.get(getRowKey);
		byte [] byteName = result.getValue(Bytes.toBytes(COLUMN_FAMILY_GRADES), Bytes.toBytes(COLUMN_NAME));
		byte [] byteAge = result.getValue(Bytes.toBytes(COLUMN_FAMILY_GRADES), Bytes.toBytes(COLUMN_AGE));

		System.out.println("name: " + Bytes.toString(byteName));
		System.out.println("age: " + Bytes.toInt(byteAge));

		// update


		// delete, use or not depends on business team


	}

}
