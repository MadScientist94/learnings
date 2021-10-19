package sax;

import javax.xml.stream.XMLStreamException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {
	boolean author = false;
	   boolean title = false;
	   boolean genre = false;
	   boolean price = false;
	   boolean publish_date= false;
	   boolean description=false;

	   @Override
	   public void startElement(
	      String uri, String localName, String qName, Attributes attributes)
	      throws SAXException {
		   try {
//			   System.out.println(qName);
			Methods.xout.writeStartElement(qName);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //create start tag 
		   if(attributes.getLength()>0) {
			   for(int i=0; i<attributes.getLength();i++) {
				   //create attribute in the  start element
			   }
		   }
	   }

	   @Override
	   public void endElement(String uri, 
	      String localName, String qName) throws SAXException {
	      
try {
	Methods.xout.writeEndElement();
} catch (XMLStreamException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	   }

	   @Override
	   public void characters(char ch[], int start, int length) throws SAXException {

	      if (author) {
	         System.out.println("author Name: " + new String(ch, start, length));
	         author = false;
	      } else if (title) {
	         System.out.println("Book Name: " + new String(ch, start, length));
	         title = false;
	      } else if (genre) {
	         System.out.println("genre: " + new String(ch, start, length));
	         genre = false;
	      } else if (price) {
	         System.out.println("Price: " + new String(ch, start, length));
	         price = false;
	      }
	      else if (publish_date) {
		         System.out.println("publish_date: " + new String(ch, start, length));
		         publish_date = false;
		      }
	      else if (description) {
		         System.out.println("description: " + new String(ch, start, length));
		         description = false;
		      }
	   }
}
