package com.ley.mylock.db.exection;

import com.ley.mylock.db.sql.MyAbstractStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;

public class MyLockProvider {
	private NamedParameterJdbcTemplate jdbcTemplate = null;
	private MyAbstractStorage myAbstractStorage = null;

	public MyLockProvider(DataSource dataSource) throws Exception  {
		this(new JdbcTemplate(dataSource));
	}

	public MyLockProvider(JdbcTemplate jdbcTemplate) throws Exception {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		this.myAbstractStorage = MyAbstractStorage.create(this.jdbcTemplate);
	}

	public void lock() throws Exception {
		jdbcTemplate.update(myAbstractStorage.sql4Insert(), new HashMap<>());
	}
}
