package com.example.withOutOrm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository
public class DatabaseLayer implements DaoInterface{
	@Autowired
	@Qualifier("orcl")
	DataSource ds;
	ResultSetMetaData rsmd= null;
	List <String> column= new LinkedList<String>();
	
	
	
// method to execute insert update delete query 
public String dbEdit(String sql)  {
	System.out.println(sql);
	try {
		return "the number of rows affected in "+sql.split(" ")[0]+" is " +ds.getConnection().createStatement().executeUpdate(sql)+" rows";
	} catch (SQLException e) {
		e.printStackTrace();
		return e.getMessage();
	}
}

// method to execute Select Query
public List <Map<String,Object>> dbSelect(String sql)  {
	System.out.println(sql);
	List <Map<String,Object>> listOfRows = null;
	try (ResultSet rs=ds.getConnection().createStatement().executeQuery(sql);){
		
		rsmd=rs.getMetaData();
		for (int i =1;i<=rsmd.getColumnCount();i++) {
			column.add(rsmd.getColumnName(i));
		}
		listOfRows= new ArrayList<Map<String,Object>>();
		while (rs.next()) {
			Map<String,Object> rows= new LinkedHashMap<String,Object>();
			Iterator<String> i= column.iterator();
			while (i.hasNext()) {
				String columnName= i.next();
				rows.put(columnName, rs.getObject(columnName));
			}
			listOfRows.add(rows);
			}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	System.out.println(listOfRows);
	return listOfRows;
}
	
}
