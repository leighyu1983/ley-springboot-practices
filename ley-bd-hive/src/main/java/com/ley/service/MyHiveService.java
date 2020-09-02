package com.ley.service;

import org.apache.hive.service.auth.PlainSaslHelper;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;


@Service
public class MyHiveService {

	private final static String TABLE_NAME = "student";
	private final static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

	/**
	 * test on company environment
	 *
	 */
	public void testJdbcKeytab() throws Exception {

	}

	/**
	 * test againt local environment
	 *
	 * @throws Exception
	 */
	public void testJdbcBasicAuth() throws Exception {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = DriverManager.getConnection(
				"jdbc:hive2://192.168.175.101:10000/default", "hive001", "123456");
		Statement stmt = con.createStatement();
		// show tables
		String sql = "select * from usr2";
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(MessageFormat.format("{0}-{1}-{2}",
					res.getString(1),res.getString(2),res.getString(3)));
		}
	}

	/**
	 * test againt local environment
	 *
	 * @throws Exception
	 */
	public void testThriftBasicAuthClientMode() throws Exception {
		//TTransport transport = HiveAuthFactory.("192.168.175.101", "10000", 99999);
		TTransport transport  = new TSocket("192.168.175.101", 10000);
		transport = PlainSaslHelper.getPlainTransport("hive001", "123456", transport);
		transport.open();

	}
}
