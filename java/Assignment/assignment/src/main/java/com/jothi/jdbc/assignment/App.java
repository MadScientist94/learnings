package com.jothi.jdbc.assignment;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jothi.jdbc.utils.ConnectionUtils;
import com.jothi.jdbc.utils.ServiceUtils;

/**
 * Hello world!
 *
 */
public class App 
{
	Scanner sc= new Scanner(System.in);
	String dash="<====================================================>";
	public static void main( String[] args )
    {
      App a= new App();
      a.init();
    }
    
    void init() {
    	System.out.println(dash);
    	System.out.println("press '1' to Login User ");
    	System.out.println("press '2' to Create User ");
    	System.out.println("press '3' to Exit ");
    	int logOption = 0;
    	logOption = ServiceUtils.scanInt();
    	switch(logOption) {
    	case 1:{
    		loginUser();
    		break;
    	}
    	case 2:{
    		createUser();
    		break;
    	}
    	case 3:{
    		System.out.println("session closed");
    		break;
    	}
    	default:{
    		System.out.println("please provide the valid option[ 1 -or- 2 -or- 3");	
    		init();
    	}
    	}
    }

    void createUser() {
    	System.out.println("enter user name ");
    	String name= sc.nextLine().toString().trim();
    	System.out.println("enter password");
    	String pass= sc.nextLine().toString().trim();
    	try(Statement st=ConnectionUtils.getMySqlConnection().createStatement();) {
			st.executeUpdate(String.format("INSERT INTO USER_TABLE VALUES ('%s','%s')",name,pass));
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    void loginUser() {
    	System.out.println("enter user name ");
    	String name= sc.nextLine().toString().trim();
    	System.out.println("enter password");
    	String pass= sc.nextLine().toString().trim();
    	try(Statement st=ConnectionUtils.getMySqlConnection().createStatement();) {
			boolean b=st.execute(String.format("SELECT * from USER_TABLE WHERE USERNAME='%s'AND PASSWORD= '%s'",name,pass));
			System.out.println(b);
			if(b) {
				accessBook();
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    void accessBook() {
    	System.out.println("press 1 to create book");
    	System.out.println("press 2 to delete book");
    }



}
