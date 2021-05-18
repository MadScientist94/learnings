package formatChanging;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

public class Pivoting {
	ArrayList<String[]> fullData;
	LinkedHashSet<String> staticFields;//static fields
	TreeSet<String> headerFields;//subject  headers
	LinkedHashSet<String> headerField;	//new headers
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pivoting p = new Pivoting();
		p.read();
		String[] ss= {"sid","name"};
		String[]h= {"subject","mark"};
		p.setStatic(ss,h);
		p.format();
	}
	
void read() {
		fullData=new ArrayList<String[]>();
		try {
		File file = new File("F:\\marks1.csv");
		Scanner reader= new Scanner(file);
		while(reader.hasNext()) {
			String [] rows = reader.next().split(",");
			fullData.add(rows);}
		reader.close();
		}catch(Exception e) {System.out.println(e);}
	}
	
void setStatic(String[] ss,String[] h) {
		staticFields= new LinkedHashSet<String>();
		headerField= new LinkedHashSet<String>();
		for(String ssv:ss) {
			staticFields.add(ssv);}
		headerFields= new TreeSet<String>();
		for(String s: fullData.get(0)) {
			if(!staticFields.contains(s)){headerFields.add(s);}}
		for(String heading: h) {
			headerField.add(heading);}
	}
	 														
Map<String,Map<String,Map<String,String>>> student= new TreeMap();//student{
Map<String,Map<String,String>> studSubMark=  new TreeMap();			//       sid value:{=>studSubMark
Map<String,String> staticfield= new TreeMap();						//					"static":{static headear: static value}=>  staticfield
Map<String,String> dynamicfield= new TreeMap();						//					"dynamic":{dynamic header: dynamic values}}}=>dynamicfield

	void format() {
		Iterator<String[]> fdi =fullData.iterator();
		fdi.next();
		while(fdi.hasNext()) {
			String [] rows=fdi.next();
			if (!student.containsKey(rows[0])) {
				studSubMark=  new TreeMap();
				staticfield=new LinkedHashMap();
				dynamicfield=new TreeMap();
				student.put(rows[0], studSubMark);}
			else {
				studSubMark=  student.get(rows[0]);
				staticfield=studSubMark.get("static");
				dynamicfield=studSubMark.get("dynamic");}
			int i=0;
			for(String s:staticFields) {
				staticfield.put(s, rows[i]);
				i++;}
			for(String s:headerFields) {
				if(i<rows.length) {
				dynamicfield.put(s, rows[i]);
				i++;}
			}
			student.get(rows[0]).put("static",staticfield);
			student.get(rows[0]).put("dynamic",dynamicfield);
		reproduce();
		}	
	}

void reproduce() {
	Iterator<Entry<String, Map<String, Map<String, String>>>> i= student.entrySet().iterator();
	PrintWriter writer;
	try {
	 writer = new PrintWriter(new File("F:\\marks2.csv"));
		writer.write(String.join(",",staticFields));
		writer.write(","+String.join(",",headerField)+"\n");
	while(i.hasNext()) {
		studSubMark=i.next().getValue();
		staticfield=studSubMark.get("static");
		dynamicfield=studSubMark.get("dynamic");
		Iterator<Entry<String, String>>  di =dynamicfield.entrySet().iterator();
		while(di.hasNext()) {
			Entry e= di.next();
		writer.write(String.join(",",staticfield.values())+","+e.getKey()+","+e.getValue()+"\n");
		}
	}
	writer.close();
	}
	catch(Exception e) {System.out.println(e);}
}
}
