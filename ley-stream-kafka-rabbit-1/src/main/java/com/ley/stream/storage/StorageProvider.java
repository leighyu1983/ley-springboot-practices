package com.ley.stream.storage;

import com.ley.stream.storage.pojo.ReceivedMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class StorageProvider {

	private IStorage iStorage;

	public StorageProvider(DataSource dataSource) throws Exception {
		iStorage = getStorage(new JdbcTemplate(dataSource));
	}

	private IStorage getStorage(JdbcTemplate jdbcTemplate) throws Exception {
		String dbDriver = jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();
		if ("MySQL".equals(dbDriver)) {
			return new OracleStorage(jdbcTemplate);
		} else {
			throw new Exception("Framework Error dbDriver is not supported:" + dbDriver);
		}
	}

	public void insert(ReceivedMessage receivedMessage, String tableName) {
		iStorage.insert(receivedMessage, tableName);
	}

	public boolean exist(ReceivedMessage receivedMessage, String tableName) {
		return iStorage.exist(receivedMessage, tableName);
	}

	public void updateStatus(String tableName, String uniqueFieldColumnValue, int status) {
		iStorage.updateStatus(tableName, uniqueFieldColumnValue, status);
	}
}
