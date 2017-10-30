package com.mcmaster.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	public static DataSource getDataSource()
	{
		return cpds;
	}
	
	public static Connection getConnection() throws SQLException
	{
		return cpds.getConnection();
	}
}
