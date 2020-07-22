package com.ley.stream.storage;

import com.ley.stream.storage.pojo.ReceivedMessage;


public interface IStorage {
	String COLUMN_SQL = "channel, header, data, cdate, edate, creator, editor, unique_key, status, trace_id";
	/**
	 * tableName by channel
	 *
	 */
	String getInsertSql(String tableName);
	String getUpdateStatusSql(String tableName);
	String getExistSql(String tableName);

	void insert(ReceivedMessage receviedMessage, String tableName);
	void updateStatus(String tableName, String uniqueKey, int status);
	boolean exist(ReceivedMessage receviedMessage, String tableName);
}
