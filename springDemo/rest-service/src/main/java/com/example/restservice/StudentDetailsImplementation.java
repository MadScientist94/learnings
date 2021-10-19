package com.example.restservice;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentDetailsImplementation implements StudentDetails {
Map<String,Student> studentsData=new TreeMap();
ObjectMapper mapper = new ObjectMapper();
@Override
public String save(Student s) {
	Map insertData=mapper.convertValue(s, Map.class);
	Map keyValue= new TreeMap();
	Iterator i=insertData.entrySet().iterator();
	while(i.hasNext()) {
		Entry e=(Entry) i.next();
		String valueType=e.getValue().getClass().getSimpleName();
		String []types= {"String","Integer","Character","Boolean"};
		
	}
	DataBaseLayer dbl= new DataBaseLayer();
	StringBuilder sb= new StringBuilder();
	sb.append("insert into table student"); 
	
	
//	String sqlQuery="insert into table student ";
//	dbl.dbCon(sqlQuery, "edit");
	
	
	
	if(!studentsData.containsKey(s.rollNo)) {
		studentsData.put(s.rollNo, s);

		return "student with roll no : "+s.rollNo+"creater successfully";
	}
	
	
	return "student with roll no : "+s.rollNo+" already exist in table. \nplease try update option";
}

@Override
public Student read(String rollNo) {
	if(studentsData.containsKey(rollNo)) {
		return studentsData.get(rollNo);
	}
	
	return null;
}

@Override
public String update(String rollNo,Map<String,String> data) {
	// TODO Auto-generated method stub
	System.out.println(3);
	if(studentsData.containsKey(rollNo)) {
		Map mark=studentsData.get(rollNo).getMarks();
		System.out.println(data.get("mark").split(","));
		for (String subMark:data.get("mark").split(",")) {
			mark.put(subMark.split("=")[0], Integer.parseInt(subMark.split("=")[1]));
		}
		studentsData.get(rollNo).setMarks(mark);
		System.out.println(1);
		System.out.println(studentsData);
		for(String key :data.keySet()) {
			if (!(key.equals("mark"))) {
				studentsData.get(rollNo).put(key,data.get(key));
			}
		
		}
		System.out.println(studentsData);
		System.out.println(2);
		return "student with roll no "+rollNo+" details had updated successfully";
	}
	return null;
}

@Override
public String delete(String rollNo) {
	
	return studentsData.remove(rollNo).toString()+"has deleted successfully";
}

public List<Student> search(Map<String, String> searchVariable) {
	return studentsData.values().stream().filter(s->s.search(searchVariable)).collect(Collectors.toList());
}
	




}



