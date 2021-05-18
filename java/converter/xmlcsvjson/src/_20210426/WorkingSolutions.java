package _20210426;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;



public class WorkingSolutions {

	public static void main(String[] args) {
		File file= new File("f:\\student.json");
		StringBuilder sb = new StringBuilder();

		try {
			Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        sb.append(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	String json=sb.toString();
	
		//json to xml
	System.out.println("json to xml");
        
	       ObjectMapper jsonMapper = new ObjectMapper();
	       JsonNode node = null;
		try {
			node = jsonMapper.readValue(json, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       XmlMapper xmlMapper74 = new XmlMapper();
//	               xmlMapper74.configure(SerializationFeature.INDENT_OUTPUT, true);
//	               xmlMapper74.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
//	               xmlMapper74.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
	       StringWriter w = new StringWriter();
//	       
	       try {
			xmlMapper74.writeValue(w, node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       System.out.println(w.toString());  
	       
	       
	       ObjectMapper objectMapper = new ObjectMapper();
	       ObjectMapper xmlMapper = new XmlMapper();
	       JsonNode tree = null;
		try {
			tree = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       //what is the purpose of object mapper
//	       System.out.println(tree);
	       String jsonAsXml = null;
		try {
			jsonAsXml = xmlMapper.writer().withRootName("students").writeValueAsString(tree);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	System.out.println(jsonAsXml);

	
	try
    {
        // Create a new XmlMapper to read XML tags
        XmlMapper xmlMapper1 = new XmlMapper();
        
//        //Reading the XML
        JsonNode jsonNode = xmlMapper1.readTree(jsonAsXml);
//        
//        //Create a new ObjectMapper
        ObjectMapper objectMapper1 = new ObjectMapper();
//        
//        //Get JSON as a string
        String value = objectMapper1.writeValueAsString(jsonNode);
//        
//        System.out.println("*** Converting XML to JSON ***");

        System.out.println("xml to json"); 
        System.out.println(value);

    } catch (JsonParseException e)
    {
        e.printStackTrace();
    } catch (JsonMappingException e)
    {
        e.printStackTrace();
    } catch (IOException e)
    {
        e.printStackTrace();
    }

		
	  //json to map to csv    
	 // json to map
	 		json = sb.toString();
	 		 ObjectMapper mapper = new ObjectMapper();
	 		 Map<String, String> map =new HashMap();
	 		List mdata=new ArrayList(); ;
	 		XmlMapper xmlMapper1;
//	 		student[] mdata = new HashMap();
	 		
	 		 try {

	 	            // convert JSON string to Map
	 	            map= mapper.readValue(json, Map.class);
	 	            xmlMapper1 = new XmlMapper();
	 	           mdata = xmlMapper1.readValue(jsonAsXml, List.class);
//	 	            map1= mapper.readValue(json, student[].class);
	 	            // it works
	 	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

//	 	            System.out.println(map);
//	 	            System.out.println(map1);
	 	        } catch (IOException e) {
	 	            e.printStackTrace();
	 	        }
	 		 
	 		 //map to csv
	    Iterator a= map.entrySet().iterator();
	    Iterator a11= mdata.iterator();

	    Entry e=(Entry)a.next();
	    
	    
	    ArrayList al= (ArrayList) e.getValue();
//	    Map al1=  (Map) e11.getValue();
		  Iterator ali= al.iterator();
//		  Iterator ali1=al1.entrySet().iterator();
		  
System.out.println("163"+mdata);
		 StringBuilder csvSb= new StringBuilder();
		 StringBuilder csvSb1= new StringBuilder();
		 csvSb.append("name,age,role,type,door no, street, taluk,pin,si,s2,s3,s4\n");
		 csvSb1.append("name,age,role,type,door no, street, taluk,pin,si,s2,s3,s4\n");
	  while(ali.hasNext()) {
		  
		  Map data=(Map) ali.next();
		  csvSb.append(data.get("name")+","+data.get("age")+","+data.get("role")+","+data.get("type")+",");
		  Map addr= (Map)data.get("address");
		  		csvSb.append(addr.get("doorNo")+","+addr.get("street")+","+addr.get("taluk")+","+addr.get("pin")+",");
		  		Map mark=(Map)data.get("mark");
		  		csvSb.append(mark.get("s1")+","+mark.get("s2")+","+mark.get("s3")+","+mark.get("s4")+"\n");
//		  System.out.println(data);
		  
	  }
	  
	  while (a11.hasNext()) {
		  Map data=(Map) a11.next();
		  
//		  Entry e11=(Entry)a11.next();
		  System.out.println(data);
//		  Map data= (Map) e11;
		  csvSb1.append(data.get("name")+","+data.get("age")+","+data.get("role")+","+data.get("type")+",");
		  Map addr= (Map)data.get("address");
	  		csvSb1.append(addr.get("doorNo")+","+addr.get("street")+","+addr.get("taluk")+","+addr.get("pin")+",");
	  		Map mark=(Map)data.get("mark");
	  		csvSb1.append(mark.get("s1")+","+mark.get("s2")+","+mark.get("s3")+","+mark.get("s4")+"\n");
	  }
	  
	  
	  System.out.println("json=>map=>csv");
	  System.out.println(csvSb);
	  System.out.println(csvSb1);
	  
	  
  //csv to map to xml
	  File csvFile = new File("f:\\std.csv").getAbsoluteFile();

	  CsvMapper csvMapper = CsvMapper.builder().build();
	  MappingIterator<Map> rows = null;
	try {
		rows = csvMapper
		          .readerWithSchemaFor(Map.class)
		          .with(CsvSchema.emptySchema().withHeader())
		          .readValues(csvFile);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  StringBuilder csvtoxml= new StringBuilder();
	  StringBuilder csvtojson= new StringBuilder();
	  csvtoxml.append("<students>");
	 csvtojson.append("{student:[");
	  ObjectMapper mp= new ObjectMapper();
	  while (rows.hasNext()) {
	    	Map s1= rows.next();
	    	
	    	mark m=new mark((String)s1.get("si"),(String)s1.get("s2"),(String)s1.get("s3"),(String)s1.get("s4"));
	    	address a1= new address((String)s1.get("door no"),(String)s1.get("street"),(String)s1.get("taluk"),(String)s1.get("pin"));
	    	String xmlString = null;
	    	System.out.println(a1.getClass().getSimpleName());
	    	String j = null;
			try {
				j= mp.writeValueAsString(new student((String)s1.get("name"),(String)s1.get("age"),(String)s1.get("role"),(String)s1.get("type"), a1,m));
//				System.out.println(j);
				xmlString = xmlMapper
				      .writeValueAsString(new student((String)s1.get("name"),(String)s1.get("age"),(String)s1.get("role"),(String)s1.get("type"), a1,m));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    csvtoxml.append(xmlString);
	    csvtojson.append(j);
	  
	  
	  }
	    csvtoxml.append("</students>");
	    csvtojson.append("]}");
	    System.out.println("csv=> map=>xml");
	  System.out.println(csvtoxml);  
	  System.out.println("csv=> map=>json");
	  System.out.println(csvtojson);
	  File xmlOutput = new File("f:\\serialized.xml");
	  File xmlOutput1 = new File("f:\\serialized.json");
	  FileWriter fileWriter = null;
	  FileWriter fileWriter1 = null;
	try {
		fileWriter = new FileWriter(xmlOutput);
		fileWriter1 = new FileWriter(xmlOutput1);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  try {
		fileWriter.write(csvtoxml.toString());
		fileWriter1.write(csvtoxml.toString());
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  try {
		fileWriter.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	void org() {
		 String jsonString = null;
		//xml to json org.json
		String x="<student><role>developer</role><address><pin>625601</pin><taluk>periyakulam</taluk><street>north poonthottam st</street><doorNo>47</doorNo></address><name>jothi</name><type>intern</type><age>26</age><mark><s3>88</s3><s4>93</s4><s5>85</s5><s1>99</s1><s2>78</s2></mark></student><student><role>developer</role><address><pin>625601</pin><taluk>periyakulam</taluk><street>north poonthottam st</street><doorNo>47</doorNo></address><name>jothi</name><type>intern</type><age>20</age><mark><s3>88</s3><s4>93</s4><s5>85</s5><s1>99</s1><s2>78</s2></mark></student>";
			try {  
				JSONObject json175 = XML.toJSONObject(x);   
				jsonString= json175.toString(4);  
				        System.out.println("193");
				        System.out.println(jsonString);  
				        
				  
				}catch (JSONException e) {  
				// TODO: handle exception  
				System.out.println(e.toString());  
				} 


		//json to xml org.json

		    JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(jsonString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String xml = null;
			try {
				xml = XML.toString(jsonObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(xml);
		    System.out.println("195"); 	
		

	}
}
