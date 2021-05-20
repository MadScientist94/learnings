package com.oracle.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		jdbc:sqlserver://[serverName[\instanceName][:portNumber]]
		String url="jdbc:sqlserver://localhost:1433";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Class loaded and registered");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url);
				Statement statement = connection.createStatement()) {
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
