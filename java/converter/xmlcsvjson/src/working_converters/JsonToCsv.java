package working_converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToCsv {

	public static void main(String[] args) {
		reader r= new reader();
		String json=r.read("f:\\XmlToJson.json").toString();
		System.out.println("json to csv");
		 ObjectMapper mapper = new ObjectMapper();
 		 Map map =new HashMap();
 		 try {// convert JSON string to Map
 	            map= mapper.readValue(json, Map.class);
 	        } catch (IOException e) {
 	            e.printStackTrace();
 	        }
 		 //map to csv
    Iterator a= map.entrySet().iterator();
    Entry e=(Entry)a.next();
    ArrayList al= (ArrayList) e.getValue();
	  Iterator ali= al.iterator();
	 StringBuilder csvSb= new StringBuilder();
	 csvSb.append("name,age,role,type,door no, street, taluk,pin,s1,s2,s3,s4,s5\n");
  while(ali.hasNext()) {
	  
	  Map data=(Map) ali.next();
	  csvSb.append(data.get("name")+","+data.get("age")+","+data.get("role")+","+data.get("type")+",");
	  Map addr= (Map)data.get("address");
	  		csvSb.append(addr.get("doorNo")+","+addr.get("street")+","+addr.get("taluk")+","+addr.get("pin")+",");
	  		Map mark=(Map)data.get("mark");
	  		csvSb.append(mark.get("s1")+","+mark.get("s2")+","+mark.get("s3")+","+mark.get("s4")+","+mark.get("s5")+"\n");
  
  }
   System.out.println("json=>map=>csv");
  System.out.println(csvSb);
  r.write("f:\\JsonToCsv.csv", csvSb.toString());
		
	}

}
