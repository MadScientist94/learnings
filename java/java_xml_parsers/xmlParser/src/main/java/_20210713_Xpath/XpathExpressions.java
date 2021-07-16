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
	         File inputFile = new File("device_sample.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder;
	         dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         XPath xPath =  XPathFactory.newInstance().newXPath();

	         String expression = "/devices/device/counters/counter";
	          NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
			            doc, XPathConstants.NODESET);

	          System.out.println(nodeList.item(0).getParentNode().getParentNode().getAttributes().item(0));
	         System.out.println( nodeList.item(0).getOwnerDocument());
	         NodeList nodeList1 = (NodeList) xPath.compile("../*").evaluate(
	        		 nodeList.item(0).getOwnerDocument(), XPathConstants.NODESET);
	         System.out.println(nodeList1.item(0).getChildNodes().item(0).getNamespaceURI());
	          
	          //	          nodeList.
//	          System.out.println(nodeList.item(0).getBaseURI());
//	          System.out.println(nodeList.item(0).getLocalName());
//	          System.out.println(nodeList.item(0).getNodeName());
//	          System.out.println(nodeList.item(0).getNodeValue());
//	          System.out.println(nodeList.item(0).getPrefix());
//	          System.out.println(nodeList.item(0).getTextContent().replaceAll("\n", ",") .replace("\\s+", " "));
//	          System.out.println(nodeList.item(0).getTextContent());
//	          System.out.println(nodeList.item(0).getUserData("44.95"));
//	          System.out.println(nodeList.item(0).getFirstChild());
//	          System.out.println(nodeList.item(0).hasAttributes());
//	          System.out.println(nodeList.item(0).hasChildNodes());
//	          System.out.println(nodeList.item(0).isEqualNode(nodeList.item(0)));
//	          System.out.println(nodeList.item(0).isSameNode(nodeList.item(0)));
//	          System.out.println(nodeList.item(0).compareDocumentPosition(nodeList.item(1)));
//	          System.out.println(nodeList.item(0).getClass());
//	          System.out.println(nodeList.item(0).getLastChild());
	          
	          System.out.println(nodeList.getLength());
	          
	       
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
