package _20210425;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ja {

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
		String json = sb.toString();
		System.out.println(json);
		ObjectMapper om= new ObjectMapper();
		XmlMapper xmlMapper = new XmlMapper();
		student[] car = null;
		try {
			car = om.readValue(json, student[].class);
			
//			new TypeReference<List<Car>>(){}
			 car = (student[]) om.readValue(json, student[].class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(car[1].age);
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.put(myCustomList.get(i).getJSONObject());

	}

}
