package Stax;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Methods {
	  XMLOutputFactory xMLOutputFactory ;
	  XMLStreamWriter xMLStreamWriter;
	  StringWriter stringWriter;
	void read() {
		 try {
			  stringWriter = new StringWriter();

	          xMLOutputFactory = XMLOutputFactory.newInstance();
	          xMLStreamWriter =
	            xMLOutputFactory.createXMLStreamWriter(stringWriter);
			 
			 
	         XMLInputFactory factory = XMLInputFactory.newInstance();
	         XMLEventReader eventReader =
	         factory.createXMLEventReader(new FileReader("book.xml"));

boolean start=false;
	         while(eventReader.hasNext()) {
	             XMLEvent event = eventReader.nextEvent();
	                
	             switch(event.getEventType()) {
	                
	                case XMLStreamConstants.START_ELEMENT:{
	                   StartElement startElement = event.asStartElement();
	                   String qName = startElement.getName().getLocalPart();
	                   System.out.print(qName +" : ");
	                   xMLStreamWriter.writeStartElement(qName);
	                   Iterator<Attribute> i=startElement.getAttributes();
	                   while(i.hasNext()) {Attribute in= i.next();
	                   xMLStreamWriter.writeAttribute(in.getName().getLocalPart(), in.getValue());
	                	   System.out.println("\n"+in.getName()+" : "+in.getValue());}
	                   
	                   start=true;
	                   break;
	                }
	                case XMLStreamConstants.CHARACTERS:
	                { Characters characters = event.asCharacters();
	                   String cd=characters.getData().toString();
	                   if (start)
	                   {                 System.out.println(cd);
	                   xMLStreamWriter.writeCharacters(cd);
	                   }
	                   start=false;
	                break;
	                }
	                case XMLStreamConstants.END_ELEMENT:
	                {  EndElement endElement = event.asEndElement();
	                   xMLStreamWriter.writeEndElement();
	                break;
	                }
	                case XMLStreamConstants.END_DOCUMENT:{
	                	 xMLStreamWriter.flush();
	                     xMLStreamWriter.close();

	                     String xmlString = stringWriter.getBuffer().toString();

	                     stringWriter.close();

	                     System.out.println(xmlString);
	                	break;
	                }
	             } 
	                
	          }
	       } catch (FileNotFoundException e) {
	          e.printStackTrace();
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	    }
	 }

