package sax;

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
	      
	      if (qName.equalsIgnoreCase("book")) {
	         String id = attributes.getValue("id");
	         System.out.println("book id No : " + id);
	      } else if (qName.equalsIgnoreCase("author")) {
	         author = true;
	      } else if (qName.equalsIgnoreCase("title")) {
	         title = true;
	      } else if (qName.equalsIgnoreCase("genre")) {
		         genre = true;
		      }
		      else if (qName.equalsIgnoreCase("price")) {
	         price = true;
	      } else if (qName.equalsIgnoreCase("publish_date")) {
	    	  publish_date = true;
	      }
	      else if (qName.equalsIgnoreCase("description")) {
	         description = true;
	      }
	   }

	   @Override
	   public void endElement(String uri, 
	      String localName, String qName) throws SAXException {
	      
	      if (qName.equalsIgnoreCase("student")) {
	         System.out.println("End Element :" + qName);
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
