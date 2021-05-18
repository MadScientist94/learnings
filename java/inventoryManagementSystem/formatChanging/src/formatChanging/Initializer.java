package formatChanging;

//import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Initializer {

	public static void main(String[] args) {
		
		Initializer i= new Initializer();
		i.readFile();

	}

	void readFile() {
//		Map<String,Map<String,Double>>
		ArrayList fullData= new ArrayList();
		try {
		      File myObj = new File("F:\\marks.csv");
		      Scanner myReader = new Scanner(myObj);
		      boolean head=true;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        if (!head) {
		        	String[] d=data.split(",");
		        fullData.add(d);}
		        head=false;
		      }
		      myReader.close();
		      System.out.println(fullData);
//		      load(fullData);
		      parser(fullData);
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
	LinkedHashSet sid= new LinkedHashSet();
	TreeSet sub= new TreeSet();
	
	HashMap fullData = new HashMap(); ;

	void load(ArrayList data) {
		Iterator i= data.iterator();
		while(i.hasNext()) {
			String[] rows=(String[]) i.next();
			sid.add(rows[0]);
			sub.add(rows[1]);
			}
		 Iterator sidI= sid.iterator();
		
		while(sidI.hasNext()) {
			String studentId=(String)sidI.next();
			  i= data.iterator();
			   HashMap mark= new HashMap();
			 while(i.hasNext()) {
				 String[] rows=(String[]) i.next();
			if(studentId.equals(rows[0])) {
				mark.put(rows[1], rows[2]);
			}
			fullData.put(studentId,mark);
			}
			}
		System.out.println(fullData);
		changeFormat();
	}
	
	Map<String,Map<String,String>> stdMark=new HashMap<String,Map<String,String>>();
	void parser(ArrayList data) {
		Iterator i= data.iterator();
		while(i.hasNext()) {
			String[] rows=(String[]) i.next();
			Map<String,String> marks=null;
			if(stdMark.containsKey(rows[0])) {
				marks=stdMark.get(rows[0]);
			} else {
				marks=new TreeMap<String,String>();
			}
			marks.put(rows[1], rows[2]);
			stdMark.put(rows[0], marks);
			sub.add(rows[1]);
			}
		System.out.println(stdMark);
		writeFile() ;
		 /*Iterator sidI= sid.iterator();
		
		while(sidI.hasNext()) {
			String studentId=(String)sidI.next();
			  i= data.iterator();
			   HashMap mark= new HashMap();
			 while(i.hasNext()) {
				 String[] rows=(String[]) i.next();
			if(studentId.equals(rows[0])) {
				mark.put(rows[1], rows[2]);
			}
			fullData.put(studentId,mark);
			}
			}
		System.out.println(fullData);
		changeFormat();*/
	}
	
	void writeFile() {
		Iterator i;
			try {
			PrintWriter writer = new PrintWriter(new File("F:\\marks1.csv"));
			
			String header="sid,"+String.join(",", sub)+"\n";
			writer.write(header);
			
			Set<Entry<String, Map<String, String>>>  marksEntrySet=stdMark.entrySet();
			for(Entry<String, Map<String, String>> marksEntry:marksEntrySet) {
				Set<Entry<String,String>> subMarkEntrySet=marksEntry.getValue().entrySet();
				
				String studentMark=marksEntry.getKey()+",";
				List<String> marks=new ArrayList<String>(marksEntry.getValue().size());
				for(Entry<String,String> subMarkEntry:subMarkEntrySet) {
					marks.add(subMarkEntry.getValue());
				}
				studentMark+=String.join(",", marks)+"\n";
				writer.write(studentMark);
			}
			
		writer.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		}
	
	void changeFormat() {
	Iterator i;
		try {
		PrintWriter writer = new PrintWriter(new File("F:\\marks1.csv"));
		StringBuilder sb = new StringBuilder();
		i=sub.iterator();
//column attributes
		sb.append("sid");
		while(i.hasNext())
		sb.append(","+i.next());
		
		sb.append("\n");
		
//values 
	 i = sid.iterator();
		while (i.hasNext()) {
			String studentId= (String)i.next();
			sb.append(studentId);
			HashMap data= (HashMap) fullData.get(studentId);
			Iterator subi= sub.iterator();
			while(subi.hasNext()) {
				sb.append(","+data.get(subi.next()));
				if (!subi.hasNext()) {
					sb.append("\n");
			}
			}
		}
		System.out.println(sb);
		writer.write(sb.toString());
	writer.close();
	}
	catch(Exception e) {
		System.out.println(e);
	}
	
	}
}