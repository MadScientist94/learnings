package sax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Methods {

		public static OutputStream outputStream = null;
		public static XMLStreamWriter xout = null;
	
	
	
	void read() {
	
		
		  try {
			outputStream = new FileOutputStream(new File("doc.xml"));
			StringBuilder sb= new StringBuilder();
//			 XMLOutputFactory.newInstance().
			xout = XMLOutputFactory.newInstance().createXMLStreamWriter(
	                new OutputStreamWriter(outputStream, "utf-8"));
			xout.writeStartDocument();
		  } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		

		
		
		try {
        File inputFile = new File("new2.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        UserHandler userhandler = new UserHandler();
        saxParser.parse(inputFile, userhandler); 
     
		
		
        xout.writeEndDocument();
        System.out.println();
        xout.close();
		} catch (Exception e) {
        e.printStackTrace();
     }	
}
}


//OutputStream outputStream = new FileOutputStream(new File("doc.xml"));
//
//XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
//                new OutputStreamWriter(outputStream, "utf-8"));
//
//out.writeStartDocument();
//out.writeStartElement("doc");
//
//out.writeStartElement("title");
//out.writeCharacters("Document Title");
//out.writeEndElement();
//
//out.writeEndElement();
//out.writeEndDocument();
//
//out.close();