package _20210425;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class _20210425 {

	public static void main(String[] args) {
		File file= new File("f:\\student3.xml");
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
		System.out.println(json);
		ObjectMapper om= new ObjectMapper();
		XmlMapper xmlMapper = new XmlMapper();
		student[] car = null;
		try {
			car = xmlMapper.readValue(json, student[].class);
			
//			new TypeReference<List<Car>>(){}
//			students car = (students) mapper.readValue(json, students.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		System.out.println(car);
	}

}



