package com.ley.stream.storage;

import com.ley.stream.storage.pojo.ReceivedMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import java.text.MessageFormat;
import java.util.Date;

public class OracleStorage implements IStorage {

	private JdbcTemplate jdbcTemplate;

	public OracleStorage(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public String getInsertSql(String tableName) {
		return MessageFormat.format("insert into {0}({1}) values(?,?,?,?,?,?,?,?,?,?)", tableName, COLUMN_SQL) ;
	}

	@Override
	public String getUpdateStatusSql(String tableName) {
		return MessageFormat.format("update {0} set status=?, edate=? where unique_key=?", tableName) ;
	}

	@Override
	public String getExistSql(String tableName) {
		return MessageFormat.format("select count(1) from {0} where unique_key=?", tableName) ;
	}

	@Override
	public void insert(ReceivedMessage receviedMessage, String tableName) {
		int notProceeded = 0;
		String sql = this.getInsertSql(tableName);
		jdbcTemplate.update(sql, new Object[] {
				receviedMessage.getChannel(), receviedMessage.getHeader(), receviedMessage.getData(),
				receviedMessage.getCdate(), receviedMessage.getEdate(), receviedMessage.getCreator(),
				receviedMessage.getEditor(), receviedMessage.getUniqueFieldValue(), notProceeded
		});
	}

	@Override
	public void updateStatus(String tableName, String uniqueKey, int status) {
		String sql = this.getUpdateStatusSql(tableName);
		jdbcTemplate.update(sql, new Object[] {status, new Date(), uniqueKey});
	}

	@Override
	public boolean exist(ReceivedMessage receviedMessage, String tableName) {
		String sql = this.getExistSql(tableName);
		int r = jdbcTemplate.queryForObject(sql, new Object[] {receviedMessage.getUniqueFieldValue()}, Integer.class);
		return r > 0;
	}
}
