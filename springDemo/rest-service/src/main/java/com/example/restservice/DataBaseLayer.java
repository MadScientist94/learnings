package com.example.restservice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class DataBaseLayer {
	public static void main(String [] a) throws SQLException {
		DataBaseLayer dbl= new DataBaseLayer();
		String sqlQuery="select * from emp ";
		String opt="select";
		ResultSet r=(ResultSet)dbl.dbCon( sqlQuery, opt);
		while (r.next())
		System.out.println(r.getObject(1));
	}
	Object dbCon(String sqlQuery,String opt) {
		Object result = null;
	// sql query
			String sql = sqlQuery;
			// Step 1: load and register Driver
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Class loaded and registered");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
//
//			String env = System.getenv(ENV);
//			Properties properties = new Properties();

//			try (InputStream is = ClassLoader.getSystemResourceAsStream(String.format("%s%s%s", RESOURCE_PREFIX, env, RESOURCE_SUFFIX))) 
//			try{

//				System.out.println(is);
//				properties.load(is);
				// Step 2 Create a Connection, Step 3: Create a Statement  //DriverManager.getConnection(properties.getProperty(URL)
				try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@Lenovo-PC:1522:orcl", "scott","tiger");
						Statement statement = connection.createStatement()) {
					// Step 4 : execute sql Query
					int affectedRows;
					ResultSet rs=null;
					ResultSetMetaData rsmd= null;
					List column= new LinkedList();
					switch(opt) {
					case "select":{
						rs=statement.executeQuery(sql);
						rsmd=rs.getMetaData();
						for (int i =1;i<=rsmd.getColumnCount();i++) {
							column.add(rsmd.getColumnName(i));
						}
						List <Map<String,Object>> listOfRows= new ArrayList();
						while (rs.next()) {
							Map<String,Object> rows= new LinkedHashMap();
							Iterator<String> i= column.iterator();
							while (i.hasNext()) {
								String columnName= i.next();
								rows.put(columnName, rs.getObject(columnName));
							
							}
						
							listOfRows.add(rows);
							}
						System.out.println(listOfRows);
						result=listOfRows;
						break;
					}
					case "edit":{
						System.out.println(sql);
						affectedRows= statement.executeUpdate(sql);
						result="no of rows affected on "+ sql.split(" ")[0]+" process is :"+ affectedRows;
						break;
					}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
//			} 
//	catch (Exception fe) {
//				fe.printStackTrace();
//			} 
			// Step 6 Close the connection is not needed due to usage of try with resource
			return result;
}
	 
}