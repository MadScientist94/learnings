package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SqlConn {

	public static void main(String[]args) {
		initiateConnection();
	} 
	static void initiateConnection() {	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@Lenovo-PC:1522:orcl", "scott", "tiger"); 
			Statement stmt=con.createStatement();
//			String Query="SELECT table_name FROM user_tables";
//			String Query="CREATE TABLE Student(Roll_no number(5) PRIMARY KEY,Name varchar(50) NOT NULL,Department varchar(50) not null)";
//			String Query="DROP TABLE Student";
//			String Query="SELECT ENAME FROM EMP";
			String Query="DESC EMP";//not working ??
//			String Query="";
//			String Query="";
//			String Query="";
//			String Query="";
//			String Query="";
//			ResultSet rs=stmt.executeQuery(Query);  
//			System.out.println(rs.getFetchSize());
////			System.out.println(rs.getMetaData());
////		
//			while(rs.next()) {
//			System.out.println(rs.getString("EMP"));
//			}
			
			//to get the table description
			ResultSet rs = stmt.executeQuery("select * from emp");
			 ResultSetMetaData rsmd = rs.getMetaData();//it used to check the column name
			 System.out.println("No. of columns : " + rsmd.getColumnCount());
			 System.out.println("Column name of 1st column : " + rsmd.getColumnName(1));
			 System.out.println("Column type of 1st column : " + rsmd.getColumnTypeName(1));
			 System.out.println();
			 
			 
//			while(rs.next()) {
				
//			
//			System.out.println(rs.getInt("table_name"));
//		}
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}



//SELECT table_name FROM user_tables
//it is used to fetch all the tables from the current database
// CREATE TABLE customers  
//( customer_id number(10) NOT NULL,  
//		  customer_name varchar2(50) NOT NULL,  
//		  city varchar2(50),  
//		  CONSTRAINT customers_pk PRIMARY KEY (customer_id)  
//		);  


