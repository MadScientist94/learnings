package com.jothi.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtils {
	static {
		// step 1: load and register driver
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getMySqlConnection() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:sqlserver://LENOVO-PC\\\\SQLEXPRESS:1433;databaseName=myFirstDatabase","sa","sa");
		System.out.println("connection created");
		return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void cleanup(ResultSet rs, Statement st) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	
}
