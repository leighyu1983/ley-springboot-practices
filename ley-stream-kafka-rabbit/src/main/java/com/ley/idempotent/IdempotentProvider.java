package com.ley.idempotent;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class IdempotentProvider {

	private String INSERT = "insert into {0}(bizid, message, createdOn) values(:bizid, :message, :createdOn)";
	private NamedParameterJdbcTemplate template;

	public IdempotentProvider(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public void insert(String tableName, String bizId, String message) {
		String sql = MessageFormat.format(INSERT, tableName);
		template.update(sql, getParams(bizId,  message));
	}

	private Map<String, Object> getParams(String bizId, String message) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("bizId", bizId);
		map.put("message", message);
		map.put("createdOn", new Date());
		return map;
	}
}
