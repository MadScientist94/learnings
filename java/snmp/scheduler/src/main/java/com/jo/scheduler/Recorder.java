package com.jo.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Recorder implements Job {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Recorder r= new Recorder();
		r.formater();
	}

	void formater()  {
		StringBuilder  sb;
		complexGetter cg= new complexGetter();
		System.out.println(cg.initialize()+"last");
		System.out.println("udpId,Oid,Value");
		TreeMap<String, TreeMap<String, String>> result=cg.initialize();
		 
		    
		 LocalDateTime myDateObj = LocalDateTime.now();
		    System.out.println("Before formatting: " + myDateObj);
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

		System.out.println(myDateObj.format(myFormatObj)+".csv");
		
		Set<String> udpids=  result.keySet();
//		sb=new StringBuilder();
		
		try {
		FileWriter writer= new FileWriter(new File("F:\\"+myDateObj.format(myFormatObj)+".csv"));
//	sb.append("udpId,Oid,Value\n");
		writer.write("udpId,Oid,Value\n");
		for (String udpid:udpids) {
			Iterator<Entry<String, String>>  i=result.get(udpid).entrySet().iterator();
			while (i.hasNext()) {
				Entry e = i.next();
//				e.getKey();
//				e.getValue();
				writer.write(udpid+",");
				writer.write(e.getKey()+",");
				writer.write(e.getValue()+"\n");
			}
		}
		
		
		writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		formater();
		
	}
	
}
