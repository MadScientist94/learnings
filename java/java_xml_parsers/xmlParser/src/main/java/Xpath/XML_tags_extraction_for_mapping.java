package Xpath;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML_tags_extraction_for_mapping {
	public static void main(String args[]) {
		try {
	

		File inputFile = new File("device_sample.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
		Document doc = dBuilder.parse(inputFile); // document object model created
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList node_list = (NodeList) xPath.compile("/").evaluate(doc, XPathConstants.NODESET); // base
																													// node
																													// from
																													// lookup
		System.out.println(node_list.getLength());
		System.out.println(node_list.item(0).getChildNodes().item(0).getNodeName());
//		for (int i = 0; i < node_list.getLength(); i++) {
//			Node base_node = node_list.item(i);
//		}
	} catch (SAXException | IOException e) {
		e.printStackTrace();
	} catch (ParserConfigurationException e1) {
		e1.printStackTrace();
	} catch (XPathExpressionException e2) {
		e2.printStackTrace();
	}
}
	
	
}