package com.example.restservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {
	private static final Logger LOGGER=LoggerFactory.getLogger(RestServiceApplication.class);
	public static void main(String[] args) {
		
//		jdbc:sqlserver://[serverName[\instanceName][:portNumber]]
//		String url="jdbc:sqlserver://localhost:1433";
//		;integratedSecurity=true; myFirstDatabase
		String url="jdbc:sqlserver://LENOVO-PC\\SQLEXPRESS:1433;databaseName=myFirstDatabase";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Class loaded and registered");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url,"sa","sa");
//				,"Lenovo-PC\\jothi",""
				Statement statement = connection.createStatement()) {
			ResultSet rs=statement.executeQuery("select * from tblDepartment");
			rs.next();
			System.out.println(rs.getObject(1)+" "+rs.getObject(2)+" "+rs.getObject(3));
			SpringApplication.run(RestServiceApplication.class, args);
			LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	
		
	}

}
