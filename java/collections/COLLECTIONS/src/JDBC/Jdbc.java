package JDBC;

import java.sql.*;

public class Jdbc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Connection  con =null;
PreparedStatement stmt=null;
try {
	Class.forName("sun.jdbc.odbc.jdbcOdbcDriver");
con= DriverManager.getConnection("jdbc:odbc:swing");
}
catch(Exception e) {
	System.out.println(e);
}



	}

}
