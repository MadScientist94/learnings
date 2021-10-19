package checkedEH;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IoException {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	IoException eh= new IoException();
//	eh.checkFileNotFound();
	eh.checkEOF();
	}

	
	public void checkFileNotFound()
    {
        try
        {
        	
//            FileInputStream in = new FileInputStream("F:\\learning\\java\\strings\\ExceptionEg\\bin\\checkedEH\\input.txt");
        	FileInputStream in = new FileInputStream("input.txt");
            System.out.println("This is not printed");
        } 
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        
        System.out.print("work");
    }
	
	public void checkEOF()
    {
        File file = new File("F:\\\\learning\\\\java\\\\strings\\\\ExceptionEg\\\\bin\\\\checkedEH\\\\input.txt");
        DataInputStream dataInputStream =  null;
        try
        {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            while(true)
            {
                System.out.println(dataInputStream.readInt());  
            }           
        }
        catch (EOFException eofException)
        {           
            eofException.printStackTrace();
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        finally
        {
            try{
                if (dataInputStream != null)
                {
                    dataInputStream.close();
                }
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
	
}
