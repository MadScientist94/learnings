package working_converters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class reader {

	StringBuilder read(String fileLocation) {
		File file= new File(fileLocation);
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

		
		return sb;
	}
	
	void write(String fileLocation,String data){
		
		File Output = new File(fileLocation);
		 
		try {
		  FileWriter fileWriter = null;
		  fileWriter = new FileWriter(Output);
		  fileWriter.write(data);
		  fileWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
}
