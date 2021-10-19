package com.example.restservice;
import static com.example.restservice.classloading.properties.GlobalConstants.ENV ;
import static com.example.restservice.classloading.properties.GlobalConstants.RESOURCE_PREFIX ;
import static com.example.restservice.classloading.properties.GlobalConstants.RESOURCE_SUFFIX ;
import static com.example.restservice.classloading.properties.GlobalConstants.URL ;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class DummyDataBaseLayer {
	public static void main(String [] a) {
		DummyDataBaseLayer dbl= new DummyDataBaseLayer();
		dbl.save();
	}
	void save() {
	// sql query
			String sql = "select * from s1";
			// Step 1: load and register Driver
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Class loaded and registered");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			String env = System.getenv(ENV);
			Properties properties = new Properties();

			try (InputStream is = ClassLoader
					.getSystemResourceAsStream(String.format("%s%s%s", RESOURCE_PREFIX, env, RESOURCE_SUFFIX))) {
				properties.load(is);
				// Step 2 Create a Connection, Step 3: Create a Statement
				try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
						Statement statement = connection.createStatement()) {
					// Step 4 : execute sql Query
				ResultSet x = statement.executeQuery(sql);
				
//				
					// Step5: process the result
//					if (x != null)
//						while(x.next())System.out.println(x.getInt(1)+" "+x.getString(2)+" "+x.getString(3)); 
//					else
//						System.out.println("Record not selected");
//					
					select(statement,sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Step 6 Close the connection is not needed due to usage of try with resource
}
	boolean insertUpdateDelete(Statement stmt,String query) throws SQLException {
		return stmt.execute(query);
	}
	ResultSet select(Statement stmt,String Query) throws SQLException {
		ResultSet rs=stmt.executeQuery(Query);
		ResultSetMetaData rsmd=rs.getMetaData();
		System.out.println(rsmd.getColumnCount());
		
		Set table_column=new LinkedHashSet();
		
		for (int i=1 ; i<=rsmd.getColumnCount();i++) {
//			System.out.println();
			table_column.add(rsmd.getColumnName(i));
		}
		System.out.println(table_column);
		while (rs.next()) {
//			System.out.println(rs.getObject(1));
			System.out.println(rs.getObject(1));
			System.out.println(rs.getObject(1).equals("jothi1"));
			if(rs.getObject(1).equals("jothi1"))
				rs.deleteRow();
		}
		return stmt.executeQuery(Query);
		
	}
}