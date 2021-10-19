package com.example.restservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class StudentDaoImp implements StudentDao{
	@Autowired
	DataBaseLayer dbl;
//insert data
	@Override
	public Object dbInsert(Map<String, Object> data) {
	String[] kv=keyValue(data);
	String tableName="student_table";
	String sqlQuery=String.format("insert into %s (%s) values (%s)", tableName,kv[0],kv[1]);
	String opt="edit";
	return dbl.dbCon(sqlQuery, opt);
	}

	@Override
	public String dbUpdate(Map<String, Object> data,Map<String, Object> condition) {
		
		String sqlQuery=String.format("update student_table set %s where %s"
				,setValue(data),whereCondition(condition));
		System.out.println(sqlQuery); 
		String opt="edit";
		return (String) dbl.dbCon(sqlQuery, opt);
	}

	@Override
	public String dbDelete(Map<String, Object> condition) {
		String sqlQuery=String.format("delete from student_table where %s"
				,whereCondition(condition));
		System.out.println(sqlQuery);
		String opt="edit";
		return (String) dbl.dbCon(sqlQuery, opt);
	}

	@Override
	public Object dbSelect(Map<String, Object> condition) {
		String sqlQuery=String.format("select * from student_table where %s"
				,whereCondition(condition));
		System.out.println(sqlQuery);
		String opt="select";
	
		return dbl.dbCon(sqlQuery, opt);
	
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
		Iterator i=data.entrySet().iterator();
		while(i.hasNext())
		{Entry e= (Entry) i.next();
		if(e.getValue().getClass().getSimpleName().equals("String"))
			quotes="'";
		else quotes="";
			value.append(e.getKey()+"="+quotes+e.getValue()+quotes+comma);
			if (i.hasNext())
				comma="";
		}
		
		return value.toString();
	}
	//db update where condition generator
	String whereCondition(Map<String , Object> data) {
		String joiner="AND";
		String quotes="'";
		StringBuilder value= new StringBuilder();
		Iterator i=data.entrySet().iterator();
		while(i.hasNext())
		{Entry e= (Entry) i.next();
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
