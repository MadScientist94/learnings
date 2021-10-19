package xml_parsing.DOM_tree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
//    
    void read() throws ParserConfigurationException, SAXException, IOException {
    	File file= new File("f:\\student.json");
    	String file_path="F:\\JsonToXml.xml";
    	File inputFile = new File("F:\\\\JsonToXml.xml");
		InputStream is = new FileInputStream(inputFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
		Document doc = dBuilder.parse(is);
	       ObjectMapper objectMapper = new ObjectMapper();
//	       ObjectMapper xmlMapper = new XmlMapper();
//	       JsonNode tree = objectMapper.readTree(json);
	       System.out.println(tree);
		
		
		
    }
    
    
}
