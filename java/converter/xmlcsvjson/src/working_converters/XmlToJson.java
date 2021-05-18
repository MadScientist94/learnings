package working_converters;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToJson {

	public static void main(String[] args) {
	reader r= new reader();
    JsonNode tree = null;
	String xml=r.read("f:\\JsonToXml.xml").toString();
	 String value = null;
	 
	try
    {
        // Create a new XmlMapper to read XML tags
        XmlMapper xmlMapper1 = new XmlMapper();
//        //Reading the XML
        JsonNode jsonNode = xmlMapper1.readTree(xml);
//        //Create a new ObjectMapper
        ObjectMapper objectMapper1 = new ObjectMapper();
//        //Get JSON as a string
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

     r.write("f:\\XmlToJson.json",value );
	}

}
