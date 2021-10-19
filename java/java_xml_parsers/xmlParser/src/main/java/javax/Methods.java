package javax;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Methods {
void read() {
	try {
    //get file to be rendered
		File inputFile = new File("book.xml");
		//builder used to convert xml file to object model
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        
//      doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("book");
        System.out.println("----------------------------");
        for (int i =0 ; i<nList.getLength();i++) {
        	Node book =nList.item(i);
        	if (book.getNodeType()==1) {
        		Element bookDetail= (Element) book;
        		System.out.println(bookDetail.getElementsByTagName("author").item(0).getTextContent());
        	}
        
        }
//        System.out.println(nList.getLength());
//        System.out.println(nList.item(0).hasChildNodes());
//        System.out.println(nList.item(0).getNodeName() );
//        System.out.println(((NodeList)nList.item(1).getChildNodes()).item(7).getChildNodes().item(0).getNodeType());
//        System.out.println(nList.item(1).getNodeType());
//        System.out.println(((Element)nList.item(1)).getChildNodes().item(1).getNodeType());
//        System.out.println(((Element)nList.item(1)).getTagName());
//        System.out.println(((Element)nList.item(1)).getElementsByTagName("author").item(0).getTextContent());
//
//        System.out.println(nList.item(0).getNodeName() );
        
        
        //        System.out.println(nList.getLength());
//        System.out.println(nList.item(1).getBaseURI());
//        System.out.println(nList.item(1).getLocalName());
//        System.out.println(nList.item(1).getNamespaceURI());
//        System.out.println(nList.item(1).getNodeName());
//        System.out.println(nList.item(1).getNodeValue());
//        System.out.println(nList.item(1).getPrefix());
//        System.out.println(nList.item(1).getTextContent());
//        System.out.println(nList.item(1).hasAttributes());
//        System.out.println(nList.item(1).hasChildNodes());
//        System.out.println(nList.item(1).getAttributes());
//        System.out.println(nList.item(1).getChildNodes());
//        System.out.println(nList.item(1).getChildNodes().getLength());
//        System.out.println(nList.item(1).getChildNodes().item(3).getTextContent());
//        System.out.println(nList.item(1).getChildNodes().item(2).getNextSibling().getTextContent());
////        System.out.println(nList.item(0).getNextSibling().getChildNodes().item(3).getTextContent());//not working
//        System.out.println(nList.item(1).getNodeType());
//        System.out.println(nList.item(1).getOwnerDocument().getNodeName());
//        System.out.println(nList.item(1).getParentNode().getNodeName());
//        System.out.println(nList.item(4).getPreviousSibling().hasAttributes());
//        Node four=nList.item(4);
//        Node three=four.getPreviousSibling();
//        System.out.println(four.hasAttributes());
//        System.out.println(three.hasAttributes());
//        System.out.println(four.getNodeName());
//       System.out.println(three);
//        System.out.println(52);
//        System.out.println(((Element)three.getPreviousSibling()).getAttribute("id"));
//        System.out.println(((Element)three.getPreviousSibling().getPreviousSibling().getPreviousSibling()).getAttribute("id"));
//        System.out.println(((Element)three.getPreviousSibling().getPreviousSibling().getPreviousSibling().getPreviousSibling().getPreviousSibling()).getAttribute("id"));
//        
//        out put for above codes
//        Root element :catalog
//        ----------------------------
//        12
//        file:/F:/learning/java/java%20xml%20parsers/xmlParser/book.xml
//        null
//        null
//        book
//        null
//        null
//
//              Ralls, Kim
//              Midnight Rain
//              Fantasy
//              5.95
//              2000-12-16
//              A former architect battles corporate zombies, 
//              an evil sorceress, and her own childhood to become queen 
//              of the world.
//           
//        true
//        true
//        com.sun.org.apache.xerces.internal.dom.AttributeMap@10455d6
//        [book: null]
//        13
//        Midnight Rain
//        Midnight Rain
//        1
//        #document
//        catalog
//        false
//        true
//        false
//        book
//        [#text: 
//           ]
//        52
//        bk104
//        bk103
//        bk102

        
        
        
        
}catch (Exception e) {
	
}
}	


void modify() {

	try {
	    //get file to be rendered
			File inputFile = new File("book.xml");
			//builder used to convert xml file to object model
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        
//	      doc.getDocumentElement().normalize();
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("book");
	        System.out.println("----------------------------");
	        for (int i =0 ; i<nList.getLength();i++) {
	        	Node book =nList.item(i);
	        	if (book.getNodeType()==1) {
	        		Element bookDetail= (Element) book;
	        	bookDetail.getElementsByTagName("title").item(0).setTextContent(bookDetail.getElementsByTagName("title").item(0).getTextContent().toUpperCase());
	        	bookDetail.setAttribute("id",bookDetail.getAttribute("id").toUpperCase());
	        	
//	        	("title").item(0).setTextContent(bookDetail.getElementsByTagName("title").item(0).getTextContent().toUpperCase());

	        	}
	        
	        }
	        
	     // write the content on console
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         System.out.println("-----------Modified File-----------");
	         StreamResult consoleResult = new StreamResult(System.out);
	         transformer.transform(source, consoleResult);
	        

	}catch(Exception e) {
		e.printStackTrace();
	}
	
	}
	
}
