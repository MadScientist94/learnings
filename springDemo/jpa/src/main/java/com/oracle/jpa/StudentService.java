package com.oracle.jpa;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {
	
	@Autowired
	DatabaseLayer dbl;
	//table in which we going to modify
	String tableName="student_table";
	
	//insert query with simple where condition
	public String dbInsert(Map<String, Object> data) {
	String[] kv=keyValue(data);
	String sqlQuery=String.format("insert into %s (%s) values (%s)", tableName,kv[0],kv[1]);
	return dbl.dbEdit(sqlQuery );
	}

	//update query with simple where condition
	public String dbUpdateWithWhere(Map<String, Object> data,Map<String, Object> condition) {
		String sqlQuery=String.format("update %s set %s where %s",tableName
				,setValue(data),whereCondition(condition));
		return dbl.dbEdit(sqlQuery );
	}

	//update query by id
		public String dbUpdateById(Map<String, Object> data,Integer roll_no) {
			String sqlQuery=String.format("update %s set %s where roll_no='%s'",tableName
					,setValue(data),roll_no);
			return dbl.dbEdit(sqlQuery );
		}

	
	//delete query with simple where condition
	public String dbDeleteWithWhere(Map<String, Object> condition) {
		String sqlQuery=String.format("delete from %s where %s",tableName
				,whereCondition(condition));
		return dbl.dbEdit(sqlQuery );
	}

	//delete query by id
	public String dbDeleteById(Integer roll_no) {
		String sqlQuery=String.format("delete from %s where roll_no=  '%s'",tableName
				,roll_no);
		return dbl.dbEdit(sqlQuery );
	}

	
	//select query with simple where condition
	public List<Map<String,Object>> dbSelectWithWhere(Map<String, Object> condition) {
		String sqlQuery=String.format("select * from %s where %s",tableName
				,whereCondition(condition));
		System.out.println(sqlQuery);
		return dbl.dbSelect(sqlQuery);
	}
	//Select Query
	public List<Map<String,Object>> dbSelect() {
		String sqlQuery=String.format("select * from %s ",tableName);
		return dbl.dbSelect(sqlQuery);
	}
	//Select Query by id
		public List<Map<String,Object>> dbSelectById(Integer roll_no) {
			String sqlQuery=String.format("select * from %s where Roll_no ='%s'",tableName,roll_no);
			return dbl.dbSelect(sqlQuery);
		}
	
//db insertkey value generator for sql query	
	String[] keyValue(Map<String, Object> data){
		Iterator<Entry<String, Object>> dataIterator= data.entrySet().iterator();
		StringBuilder key = new StringBuilder();
		StringBuilder value= new StringBuilder();
		while (dataIterator.hasNext()) {
			String comma=",";
	String quotes="'";
			Entry<String, Object> e= dataIterator.next();
			if(!dataIterator.hasNext())
				comma="";
			
				key.append(e.getKey()+comma);
				if (e.getValue().getClass().getSimpleName().equals("String"))
					quotes="'";
				else quotes="";
			value.append(quotes+e.getValue()+quotes+comma);
		}
		String[] s= {key.toString(),value.toString()};
		return s;
	}
// db update set value generator
	String setValue(Map<String , Object> data) {
		String comma=",";
		String quotes="'";
		StringBuilder value= new StringBuilder();
		Iterator<Entry<String, Object>> i=data.entrySet().iterator();
		while(i.hasNext())
		{Entry<String, Object> e=  i.next();
		if(e.getValue().getClass().getSimpleName().equals("String"))
			quotes="'";
		else quotes="";
		if (!i.hasNext())
			comma="";	
		value.append(e.getKey()+"="+quotes+e.getValue()+quotes+comma);
			
		}
		
		return value.toString();
	}
	//db update where condition generator
	String whereCondition(Map<String , Object> data) {
		String joiner="AND";
		String quotes="'";
		StringBuilder value= new StringBuilder();
		Iterator<Entry<String, Object>> i=data.entrySet().iterator();
		while(i.hasNext())
		{Entry<String, Object> e=  i.next();
		if(e.getValue().getClass().getSimpleName().equals("String"))
			quotes="'";
		else quotes="";
		if (!i.hasNext())
			joiner="";
			value.append(e.getKey()+"="+quotes+e.getValue()+quotes+" "+joiner+" ");
			
		}
		return value.toString();
	}

}
