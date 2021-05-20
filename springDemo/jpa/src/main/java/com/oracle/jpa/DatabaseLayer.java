package com.oracle.jpa;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class DatabaseLayer{
	@Autowired
	@Qualifier("jt")
	JdbcTemplate jt;
	ResultSetMetaData rsmd= null;
	List <String> column= null;
	
	
	
// method to execute insert update delete query 
public String dbEdit(String sql)  {
	System.out.println(sql);

	return "the number of rows affected in "+sql.split(" ")[0]+" is " +	jt.update(sql)+" rows";
	
}

// method to execute Select Query
public List <Map<String,Object>> dbSelect(String sql)  {
	System.out.println(sql);
column= new LinkedList<String>();
	return jt.query(sql,(rs,rowno)-> rsToList(rs));
}
private Map<String,Object> rsToList(ResultSet rs) throws SQLException {

	rsmd=rs.getMetaData();
	System.out.println(!column.isEmpty());
	if(column.isEmpty()) {
			for (int i =1;i<=rsmd.getColumnCount();i++) {
				column.add(rsmd.getColumnName(i));
			}}

				Map<String,Object> rows= new LinkedHashMap<String,Object>();
				Iterator<String> i= column.iterator();
				while (i.hasNext()) {
					String columnName= i.next();
					rows.put(columnName, rs.getObject(columnName));
				}

			return rows;
}
	
}
