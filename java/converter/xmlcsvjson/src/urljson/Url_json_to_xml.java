package urljson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
//import org.json.simple.*;


import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Url_json_to_xml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		URL url;
//		String inline="";
//		try {
//			 url= new URL("https://jsonplaceholder.typicode.com/posts");
//			 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			 conn.setRequestMethod("GET");
//			 conn.connect();
//			 int responsecode = conn.getResponseCode();
//			 if(responsecode != 200)
//			 {}
//				 else
//				 {
////					 Scanner sc = new Scanner(url.openStream());
//					 Scanner sc = new Scanner(new File("F:\\student.json"));
//					 StringBuilder sb=new StringBuilder();
//					 while(sc.hasNext())
//					 {
//					 sb.append(sc.nextLine());
//					 }
//				inline=sb.toString();
//					 sc.close();
//				 } 
//			 
//			 
//			  
//			
//		} catch (MalformedURLException | ProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	
	
		StringBuilder content = new StringBuilder();  
		  // Use try and catch to avoid the exceptions  
		    try  
		    {  
		      URL url = new URL("https://jsonplaceholder.typicode.com/posts"); // creating a url object  
		      URLConnection urlConnection = url.openConnection(); // creating a urlconnection object  
		  
		      // wrapping the urlconnection in a bufferedreader  
		      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
		      String line;  
		      // reading from the urlconnection using the bufferedreader  
		      while ((line = bufferedReader.readLine()) != null)  
		      {  
		        content.append(line );  
		      }  
		      bufferedReader.close();  
		    }  
		    catch(Exception e)  
		    {  
		      e.printStackTrace();  
		    }  
		    System.out.println( content.toString());  

		    ObjectMapper objectMapper = new ObjectMapper();
			 ObjectMapper xmlMapper = new XmlMapper();
		    JsonNode tree = null;
		    String jsonAsXml = null;
			String json="{\"users\":"+content.toString()+"}";
			System.out.println("json to xml");
			 
		     try {
				tree = objectMapper.readTree(json);
				jsonAsXml = xmlMapper.writer().withRootName("cases").writeValueAsString(tree);
		     } catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		     System.out.println(jsonAsXml);
		     File Output = new File("f:\\try.xml");
			 
				try {
				  FileWriter fileWriter = null;
				  fileWriter = new FileWriter(Output);
				  fileWriter.write(jsonAsXml);
				  fileWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				String xml=jsonAsXml;
				 String value = null;
				 
				try
			    {
			        // Create a new XmlMapper to read XML tags
			        XmlMapper xmlMapper1 = new XmlMapper();
//			        //Reading the XML
			        JsonNode jsonNode = xmlMapper1.readTree(xml);
//			        //Create a new ObjectMapper
			        ObjectMapper objectMapper1 = new ObjectMapper();
//			        //Get JSON as a string
			         value = objectMapper1.writeValueAsString(jsonNode);
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

				File Output1 = new File("f:\\try.json");
				 
				try {
				  FileWriter fileWriter = null;
				  fileWriter = new FileWriter(Output1);
				  fileWriter.write(value);
				  fileWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				
				
	
	}  
	
	

  
	
	}


