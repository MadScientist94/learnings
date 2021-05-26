package Xpath;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Methods {
	

		public void read() {
			 try {
		         File inputFile = new File("mondial.xml");
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder;

		         dBuilder = dbFactory.newDocumentBuilder();

		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();

		         XPath xPath =  XPathFactory.newInstance().newXPath();

		         String expression = "/mondial/lake";
		         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
		            doc, XPathConstants.NODESET);
		         System.out.println(nodeList.getLength());
		         for (int i=0; i<nodeList.getLength();i++) {
		        	 Node e=  nodeList.item(i);
		        	 System.out.println(e.getNodeName());
		        	 System.out.println(e.hasChildNodes());
		        	 if (e.hasChildNodes()) {
		        	NodeList nl=	 e.getChildNodes();
		        	for (int j=0 ;j<nl.getLength();j++) {	 
		        		System.out.println(nl.item(j).getNodeName());
			        		
		        	}
		        	 }
		        	 
		         }
			 }catch(Exception e) {
				 
			 }
		}
}
