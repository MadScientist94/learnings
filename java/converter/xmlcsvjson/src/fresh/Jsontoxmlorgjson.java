package fresh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.json.*;
public class Jsontoxmlorgjson {

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
		try {
		JSONObject jsonObject = new JSONObject(json);
			FileWriter we= new FileWriter(new File("f:\\student3.xml"));
			String xml = XML.toString(jsonObject);
			String root="students";
			String xmlString="<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?><"+root+">" +xml+ "</"+root+">";
			System.out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?><"+root+">" +xml+ "</"+root+">");
			we.write(xmlString);
		
			
			we.close();
		
			 JSONObject json1 = XML.toJSONObject(xmlString); // converts xml to json
	         String jsonPrettyPrintString = json1.toString(4); // json pretty print
	         System.out.println(jsonPrettyPrintString);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
