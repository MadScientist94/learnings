package com.jothi.jdbc.utils;

import java.util.Scanner;

public class ServiceUtils {
static Scanner sc= new Scanner(System.in);

	public static int scanInt() {
	int res=0;
	try {
		res=sc.nextInt();
	}
	catch(Exception e) {
		
		sc=new Scanner(System.in);
	System.out.println("please provide a valid integer value");
	res=scanInt();
	}
	
	return res;
}
}
