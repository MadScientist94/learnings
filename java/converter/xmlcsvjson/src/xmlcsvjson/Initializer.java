package xmlcsvjson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Initializer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file= new File("f:\\student.json");
		try {
			
//		      ArrayList fullData=new ArrayList();
		      Scanner myReader = new Scanner(file);
		      boolean head=true;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
//		        if (!head) {
//		        	String[] d=data.split(",");
//		        fullData.add(d);}
//		        head=false;
		        
		      String datau= data.replace("\"", "");
		      String datac=datau.replace(",", "");
		      
		      System.out.println(datac.contains(":{"));
		      
		      
		      String[] datas= datac.split(":");
		        System.out.print("<"+datas[0]+">");
		        if (datas.length>1)
		        System.out.print(datas[1]);
		        System.out.println("</"+datas[0]+">");
		        
		      }
		      myReader.close();
//		      System.out.println(fullData);
		     
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

}
