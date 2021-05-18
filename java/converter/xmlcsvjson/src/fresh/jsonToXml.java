package fresh;
import xmlcsvjson.JsonTo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class jsonToXml {

	public static void main(String[] args) {
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
		String json = sb.toString();
		  ObjectMapper jsonMapper = new ObjectMapper();
		  String jsonAsXml =""; 
		  StringWriter w = null;
		  try {
			student node = jsonMapper.readValue(json, student.class);
		System.out.println(node);
	       XmlMapper xmlMapper74 = new XmlMapper();	
	       w = new StringWriter();
	       xmlMapper74.writeValue(w, node);
	       System.out.println(w.toString());
	       
	       
	       ObjectMapper objectMapper = new ObjectMapper();
	       ObjectMapper xmlMapper = new XmlMapper();
	       JsonNode tree = objectMapper.readTree(json);
	       System.out.println(tree);
	        jsonAsXml = xmlMapper.writer().withRootName("student").writeValueAsString(tree);
	   	System.out.println(jsonAsXml);
	       } catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	       try {  
	   		JSONObject json175 = XML.toJSONObject(w.toString());   
	   		        String jsonString = json175.toString(4);  
	   		        System.out.println(jsonString);  
	   		  
	   		}catch (JSONException e) {  
	   		// TODO: handle exception  
	   		System.out.println(e.toString());  
	   		} 

	       
	       
	}

}
