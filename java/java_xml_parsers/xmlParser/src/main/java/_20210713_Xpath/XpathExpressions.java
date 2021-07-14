package _20210713_Xpath;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XpathExpressions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XpathExpressions x= new XpathExpressions();
		x.read();

	}
	
	private void read() {
		try {
	         File inputFile = new File("book.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder;
	         dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         XPath xPath =  XPathFactory.newInstance().newXPath();

	         String expression = "/catalog/child::book/price";
	          NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
			            doc, XPathConstants.NODESET);
//	          System.out.println(nodeList.item(0).getNodeName());
	          System.out.println(nodeList.getLength());
	          
	       
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
