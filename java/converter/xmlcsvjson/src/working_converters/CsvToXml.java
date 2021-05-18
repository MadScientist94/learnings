package working_converters;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class CsvToXml {

	public static void main(String[] args) {
		reader r = new reader();
		CsvReader csvr= new CsvReader();
		 MappingIterator<Map> rows =csvr.read("f:\\XmlToCsv.csv");
		StringBuilder csvtoxml= new StringBuilder();
		  csvtoxml.append("<students>");
		  ObjectMapper mp= new ObjectMapper();
		  while (rows.hasNext()) {
		    	Map s1= rows.next();
		    	
		    	mark m=new mark((String)s1.get("si"),(String)s1.get("s2"),(String)s1.get("s3"),(String)s1.get("s4"),(String)s1.get("s5"));
		    	address a1= new address((String)s1.get("door no"),(String)s1.get("street"),(String)s1.get("taluk"),(String)s1.get("pin"));
		    	String xmlString = null;
		    	System.out.println(a1.getClass().getSimpleName());
		    	String j = null;
		    	ObjectMapper xmlMapper = new XmlMapper();
				try {
					xmlString = xmlMapper.writeValueAsString(new student((String)s1.get("name"),(String)s1.get("age"),(String)s1.get("role"),(String)s1.get("type"), a1,m));
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    csvtoxml.append(xmlString);
		  }
		    csvtoxml.append("</students>");
		    System.out.println("csv=> map=>xml");
		  System.out.println(csvtoxml);  
r.write("f:\\CsvToXml.xml",csvtoxml.toString());	
	
	}

}
