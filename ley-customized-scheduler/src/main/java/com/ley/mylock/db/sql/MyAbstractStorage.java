package com.ley.mylock.db.sql;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class MyAbstractStorage {

	public static MyAbstractStorage create(NamedParameterJdbcTemplate template) throws Exception {
		String dbName = template.getJdbcTemplate().getDataSource().getConnection().getMetaData().getDatabaseProductName();

		if(dbName.toLowerCase().contains("mysql")) {
			return new MySqlStorage();
		} else {
			throw new Exception("");
		}
	}

	public abstract String sql4Select();
	public abstract String sql4Insert();
}
