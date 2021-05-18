package working_converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToCsv {

	public static void main(String[] args) {
		reader r=new reader();
String xml=r.read("f:\\JsonToXml.xml").toString();
		ObjectMapper mapper = new ObjectMapper();
 		 Map<String, String> map =new HashMap();
 		List xmlToData=new ArrayList(); ;
 		XmlMapper xmlMapper;
 		 try {
 			xmlMapper = new XmlMapper();
 	           xmlToData = xmlMapper.readValue(xml, List.class);
 	        } catch (IOException e) {
 	            e.printStackTrace();
 	        }
 		 //map to csv;
    Iterator listIterator= xmlToData.iterator();
	 StringBuilder csvSb= new StringBuilder();
	 csvSb.append("name,age,role,type,door no, street, taluk,pin,s1,s2,s3,s4,s5\n");
  while (listIterator.hasNext()) {
	  Map data=(Map) listIterator.next();
	  csvSb.append(data.get("name")+","+data.get("age")+","+data.get("role")+","+data.get("type")+",");
	  Map addr= (Map)data.get("address");
  		csvSb.append(addr.get("doorNo")+","+addr.get("street")+","+addr.get("taluk")+","+addr.get("pin")+",");
  		Map mark=(Map)data.get("mark");
  		csvSb.append(mark.get("s1")+","+mark.get("s2")+","+mark.get("s3")+","+mark.get("s4")+","+mark.get("s5")+"\n");
  }
  
  
  System.out.println("xml=>map=>csv");
 
  System.out.println(csvSb);

r.write("f:\\XmlToCsv.csv", csvSb.toString());
	}

}
