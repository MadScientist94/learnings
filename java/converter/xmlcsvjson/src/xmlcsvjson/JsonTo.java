package xmlcsvjson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.*;

public class JsonTo {

	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
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
// json to map
		String json = sb.toString();
		 ObjectMapper mapper = new ObjectMapper();
		 Map<String, String> map =new HashMap();
		student[]map1 ;
		 try {

	            // convert JSON string to Map
	            map= mapper.readValue(json, Map.class);
//	            map1= mapper.readValue(json, student[].class);
	            // it works
	            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

	            System.out.println(map);
//	            System.out.println(map1);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
//map to json
	       try {

	            // convert map to JSON string
	            String json1 = mapper.writeValueAsString(map);

	            System.out.println(json);   // compact-print

//	            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

	            System.out.println(json);   // pretty-print

	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }

	        
//json to xml
	        
	       ObjectMapper jsonMapper = new ObjectMapper();
	       JsonNode node = jsonMapper.readValue(json, JsonNode.class);
	       XmlMapper xmlMapper74 = new XmlMapper();
//	               xmlMapper74.configure(SerializationFeature.INDENT_OUTPUT, true);
//	               xmlMapper74.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
//	               xmlMapper74.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
	       StringWriter w = new StringWriter();
//	       
	       xmlMapper74.writeValue(w, node);
	       System.out.println(w.toString());  
	       
	       
	       ObjectMapper objectMapper = new ObjectMapper();
	       ObjectMapper xmlMapper = new XmlMapper();
	       JsonNode tree = objectMapper.readTree(json);
	       //what is the purpose of object mapper
//	       System.out.println(tree);
	       String jsonAsXml = xmlMapper.writer().withRootName("students").writeValueAsString(tree);
	System.out.println(jsonAsXml);

	
	
	
	
	
//	
//	 String data = "<?xml version='1.0' encoding='UTF-8'?>"+
//             "<student>"+
//             "<age>11</age>"+
//             "<id>12</id>"+
//             "<name>JavaInterviewPoint</name>"+
//          "</student>";
	
	String xml11 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<errors>\n" +
            "  <error>\n" +
            "    <status>400</status>\n" +
            "    <message>The field 'quantity' is invalid.</message>\n" +
            "    <details>\n" +
            "      <invalid_reason>The quantity specified is greater than the quantity of the product that is available to ship.</invalid_reason>\n" +
            "      <available_quantity>0</available_quantity>\n" +
            "      <order_product_id>12525</order_product_id>\n" +
            "    </details>\n" +
            "  </error>\n" +
            "<error>\n" +
            "    <status>400</status>\n" +
            "    <message>The field 'quantity' is invalid.</message>\n" +
            "    <details>\n" +
            "      <invalid_reason>The quantity specified is greater than the quantity of the product that is available to ship.</invalid_reason>\n" +
            "      <available_quantity>0</available_quantity>\n" +
            "      <order_product_id>12525</order_product_id>\n" +
            "    </details>\n" +
            "  </error>\n" +
            "</errors>";
//     
     try
     {
         // Create a new XmlMapper to read XML tags
         XmlMapper xmlMapper1 = new XmlMapper();
         
//         //Reading the XML
         JsonNode jsonNode = xmlMapper1.readTree(xml11);
//         
//         //Create a new ObjectMapper
         ObjectMapper objectMapper1 = new ObjectMapper();
//         
//         //Get JSON as a string
         String value = objectMapper1.writeValueAsString(jsonNode);
//         
//         System.out.println("*** Converting XML to JSON ***");
         System.out.println(value);
          System.out.println("143"); 

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

	String x="<student><name>jothi</name><age>26</age>"
			+ "<role>developer</role><type>intern</type>"
			+ "<address><doorNo>47</doorNo><street>north poonthottam st</street>"
			+ "<taluk>periyakulam</taluk><pin>625601</pin></address>"
			+ "<mark><s1>99</s1><s2>78</s2><s3>88</s3><s4>93</s4><s5>85</s5></mark>"
			+ "</student><student><name>jothi</name><age>20</age><role>developer</role>"
			+ "<type>intern</type>"
			+ "<address><doorNo>47</doorNo><street>north poonthottam st</street>"
			+ "<taluk>periyakulam</taluk><pin>625601</pin></address>"
			+ "<mark><s1>99</s1><s2>78</s2><s3>88</s3><s4>93</s4><s5>85</s5></mark></student>";
//	XmlMapper xmlMapper149 = new XmlMapper();
//	student poppy = xmlMapper149.readValue(x, student.class);
////	List<Students> a= new ArrayList();
////	student poppy1 = xmlMapper149.readValue(jsonAsXml, student.class);
//	
//	ObjectMapper mapper153 = new ObjectMapper();
//	String json154 = mapper153.writeValueAsString(poppy);
//	System.out.println(json154);
//	 json154 = mapper153.writeValueAsString(poppy1);
//		System.out.println(json154);
	
	
//xml to json org.json
	
	try {  
		JSONObject json175 = XML.toJSONObject(x);   
		        String jsonString = json175.toString(4);  
		        System.out.println("193");
//		        System.out.println(jsonString);  
		        
		  
		}catch (JSONException e) {  
		// TODO: handle exception  
		System.out.println(e.toString());  
		} 


//json to xml org.json

    JSONObject jsonObject = new JSONObject(json);
    String xml =  XML.toString(jsonObject) ;
    System.out.println(xml);
    System.out.println("195"); 
    
//// json to csv
//    JSONObject output;
//    try {
//        output = new JSONObject(json);
//        JSONArray docs = output.getJSONArray("student");
//        File file195 = new File("EmpDetails.csv");
//        String csv = CDL.toString(docs);
////        FileUtils.writeStringToFile(file195, csv);
////        System.out.println("Data has been Sucessfully Writeen to "+ file195);
//        System.out.println(csv);
//     }
//     catch(Exception e) {
//        e.printStackTrace();
//     }
//    

    
//json to map to csv    
    Iterator a= map.entrySet().iterator();
    Entry e=(Entry)a.next();
    ArrayList al= (ArrayList) e.getValue();
  Iterator ali= al.iterator();

	 StringBuilder csvSb= new StringBuilder();
	 csvSb.append("name,age,role,type,door no, street, taluk,pin,si,s2,s3,s4\n");
  while(ali.hasNext()) {
	  
	  Map data=(Map) ali.next();
	  csvSb.append(data.get("name")+","+data.get("age")+","+data.get("role")+","+data.get("type")+",");
	  Map addr= (Map)data.get("address");
	  		csvSb.append(addr.get("doorNo")+","+addr.get("street")+","+addr.get("taluk")+","+addr.get("pin")+",");
	  		Map mark=(Map)data.get("mark");
	  		csvSb.append(mark.get("s1")+","+mark.get("s2")+","+mark.get("s3")+","+mark.get("s4")+"\n");
	  System.out.println(data);
	  
  }
  System.out.println(csvSb);
    
  File csvFile = new File("f:\\std.csv").getAbsoluteFile();

  CsvMapper csvMapper = CsvMapper.builder().build();
  MappingIterator<Map> rows = csvMapper
          .readerWithSchemaFor(Map.class)
          .with(CsvSchema.emptySchema().withHeader())
          .readValues(csvFile);
  StringBuilder csvtoxml= new StringBuilder();
  csvtoxml.append("<students>");
  while (rows.hasNext()) {
    	Map s1= rows.next();
    	
    	mark m=new mark((String)s1.get("si"),(String)s1.get("s2"),(String)s1.get("s3"),(String)s1.get("s4"));
    	address a1= new address((String)s1.get("door no"),(String)s1.get("street"),(String)s1.get("taluk"),(String)s1.get("pin"));
    	String xmlString = xmlMapper
  	          .writeValueAsString(new student((String)s1.get("name"),(String)s1.get("age"),(String)s1.get("role"),(String)s1.get("type"), a1,m));
    System.out.println(xmlString);	
    csvtoxml.append(xmlString);
  
  
  }
    csvtoxml.append("</students>");
  System.out.println(csvtoxml);  
  File xmlOutput = new File("f:\\serialized.xml");
  FileWriter fileWriter = new FileWriter(xmlOutput);
  fileWriter.write(csvtoxml.toString());
  fileWriter.close();

	}

}






//class Students{
//	student student;
//
//	public student getStudent() {
//		return student;
//	}
//
//	public void setStudent(student student) {
//		this.student = student;
//	}
//}
//















class student{
	String name, age, role, type;
	address address;
	mark mark;
	student(String n, String a, String r, String t,address a1,mark m ){
		this.name=n;
		this.age=a;
		this.role=r;
		this.type=t;
		this.address=a1;
		this.mark=m;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public address getAddress() {
		return address;
	}
	public void setAddress(address address) {
		this.address = address;
	}
	public mark getMark() {
		return mark;
	}
	public void setMark(mark mark) {
		this.mark = mark;
	}
}

class address{
	String doorNo;
	String street;
	String taluk;
	String pin;
	address(String d, String s, String t, String p){
		this.doorNo=d;
		this.street=s;
		this.taluk=t;
		this.pin=p;
	}
	public String getDoorNo() {
		return doorNo;
	}
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTaluk() {
		return taluk;
	}
	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
}


class mark{
	private String s1;
	private String s2;
	private String s3;
	private String s4;
mark(String s1,String s2, String s3, String s4){
	this.s1=s1;
	this.s2=s2;
	this.s3=s3;
	this.s4=s4;
}
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	
}


