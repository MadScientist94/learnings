package com.example.restservice;

import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
String rollNo;
String name;
String depaertment;
String email;
String address;
String phone;
@JsonProperty("marks")
Map<String,Integer> marks;
public Student(String rollNo,String name, String depaertment, String email, String address, String phone,
		Map<String, Integer> marks) {
	super();
	this.rollNo=rollNo;
	this.name = name;
	this.depaertment = depaertment;
	this.email = email;
	this.address = address;
	this.phone = phone;

	this.marks = marks;
}

public String getRollNo() {
	return rollNo;
}

public void setRollNo(String rollNo) {
	this.rollNo = rollNo;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Student() {
	super();
}

public String getDepaertment() {
	return depaertment;
}
public void setDepartment(String depaertment) {
	this.depaertment = depaertment;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Map<String, Integer> getMarks() {
	return marks;
}
public void setMarks(Map<String, Integer> marks) {
	this.marks = marks;
}

@Override
public String toString() {
	return "Student [rollNo=" + rollNo + ", name=" + name + ", depaertment=" + depaertment + ", email=" + email
			+ ", address=" + address + ", phone=" + phone + ", marks=" + marks + "]";
}

public void put(String key, String string) {
	// TODO Auto-generated method stub
	switch(key){
	case "rollNo":{
		setRollNo(string);
		break;
	}
	case "name" :{
		setName(string);
		break;
	}
	case "depaertment":
	{
		setDepartment(string);
		break;
	}
	case "email" :
	{
		setEmail(string);
		break;
	}
	case "address" :
	{
		setAddress(string);
		break;
	}
	case "phone" :
	{
		setPhone(string);
		break;
	}
	}
	
}

public boolean search(Map<String, String> searchVariable) {
	boolean result;
	for(Entry e:searchVariable.entrySet()) {
	System.out.println(e.getKey()+" "+e.getValue());
		switch(e.getKey().toString()){
	case "rollNo":{
		if(!getRollNo().equals(e.getValue()))
			return false;
		continue;
	}
	case "name" :{
		System.out.println(getName());
		System.out.println(e.getKey()+" "+e.getValue());
		if(!getName().equals(e.getValue()))
		return false;
		continue;
	}
	case "depaertment":
	{
		if(!getDepaertment().equals(e.getValue()))
		return false;
		continue;
	}
	case "email" :
	{
		System.out.println(getEmail());
		System.out.println(e.getKey()+" "+e.getValue());
		if(!getEmail().equals(e.getValue()))
		return false;
		continue;
	}
	case "address" :
	{
		if(!getAddress().equals(e.getValue()))
		return false;
		continue;
	}
	case "phone" :
	{
		if(!getPhone().equals(e.getValue()))
		return false;
		continue;
	}
	default: return false;
	}
	}
	return true;

}

	
}
