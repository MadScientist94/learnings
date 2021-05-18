package working_converters;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;



public class CsvToJson {

	public static void main(String[] args) {
		  //csv to map to xml
		CsvReader csvr= new CsvReader();
		 MappingIterator<Map> rows =csvr.read("f:\\xmlToCsv.csv");
		  StringBuilder csvtojson= new StringBuilder();
		 csvtojson.append("{student:[");
		  ObjectMapper objectMapper= new ObjectMapper();
		  while (rows.hasNext()) {
		    	Map s1= rows.next();
		    	mark m=new mark((String)s1.get("si"),(String)s1.get("s2"),(String)s1.get("s3"),(String)s1.get("s4"),(String)s1.get("s5"));
		    	address a1= new address((String)s1.get("door no"),(String)s1.get("street"),(String)s1.get("taluk"),(String)s1.get("pin"));
		    	System.out.println(a1.getClass().getSimpleName());
		    	String json = null;
				try {
					json= objectMapper.writeValueAsString(new student((String)s1.get("name"),(String)s1.get("age"),(String)s1.get("role"),(String)s1.get("type"), a1,m));
					} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
		    csvtojson.append(json);  
		  }
		csvtojson.append("]}");
		  System.out.println("csv=> map=>json");
		  System.out.println(csvtojson);
reader r=  new reader();
r.write("f:\\CsvToJson.json", csvtojson.toString());
	}

}
