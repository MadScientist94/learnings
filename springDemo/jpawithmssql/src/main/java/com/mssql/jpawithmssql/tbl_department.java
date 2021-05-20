package com.mssql.jpawithmssql;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class tbl_department {
@Id
int id;
String departmenname;
String manager;

public tbl_department() {
	super();
}

public tbl_department(int id, String departmenname, String manager) {
	super();
	this.id = id;
	this.departmenname = departmenname;
	this.manager = manager;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getDepartmenname() {
	return departmenname;
}

public void setDepartmenname(String departmenname) {
	this.departmenname = departmenname;
}

public String getManager() {
	return manager;
}

public void setManager(String manager) {
	this.manager = manager;
}

@Override
public String toString() {
	return "tblDepartment [id=" + id + ", departmenname=" + departmenname + ", manager=" + manager + "]";
}
	
	
	
}
