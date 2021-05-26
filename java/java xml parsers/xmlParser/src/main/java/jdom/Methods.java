package jdom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Methods {

	void read() {
		try {
	         File inputFile = new File("book.xml");
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(inputFile);
	         
	         System.out.println(document.getRootElement());
	         Element root=document.getRootElement();
	        List <Element> books= root.getChildren();
	        for(Element book : books) {
				List <Element> bookDetails = book.getChildren();
				if (book.hasAttributes()) {
					List<Attribute>atts=book.getAttributes();
					for (Attribute att:atts) {
						System.out.println(att.getName()+" : "+att.getValue());
					}
//					System.out.println(book.getCType());
				}
				for(Element bookDetail:bookDetails) {
					System.out.println(bookDetail.getName() +" : "+bookDetail.getText());
				}
				System.out.println("================================================================================");
	        }
	        
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void createDoc() {

	      try{
	         //root element
	         Element carsElement = new Element("cars");
	         Document doc = new Document(carsElement);

	         //supercars element
	         Element supercarElement = new Element("supercars");
	         supercarElement.setAttribute(new Attribute("company","Ferrari"));

	         //supercars element
	         Element carElement1 = new Element("carname");
	         carElement1.setAttribute(new Attribute("type","formula one"));
	         carElement1.setText("Ferrari 101");

	         Element carElement2 = new Element("carname");
	         carElement2.setAttribute(new Attribute("type","sports"));
	         carElement2.setText("Ferrari 202");

	         supercarElement.addContent(carElement1);
	         supercarElement.addContent(carElement2);

	         doc.getRootElement().addContent(supercarElement);

	         XMLOutputter xmlOutput = new XMLOutputter();

	         // display ml
	         xmlOutput.setFormat(Format.getPrettyFormat());
	         xmlOutput.output(doc, System.out); 
	      } catch(IOException e) {
	         e.printStackTrace();
	      }
	}
	
	
	
	
	
}
