package com.ley.mylock.db.sql;

public class MySqlStorage extends MyAbstractStorage {

	@Override
	public String sql4Select() {
		return "select * from mysql ...";
	}

	@Override
	public String sql4Insert() {
		return "insert into mysql ....";
	}
}
