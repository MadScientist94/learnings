package Xpath;

import java.io.File;
//import java.lang.instrument.Instrumentation;
import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Methods {
	Instrumentation instrumentation;
	
		public void read() {
			System.out.println(Runtime.getRuntime().totalMemory());
			 try {
		         File inputFile = new File("mondial.xml");
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder;

		         dBuilder = dbFactory.newDocumentBuilder();
System.out.println(Runtime.getRuntime().totalMemory());
//System.out.println(Runtime.getRuntime().freeMemory());
//System.out.println(Runtime.getRuntime().maxMemory());
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         System.out.println(Runtime.getRuntime().totalMemory());
		         XPath xPath =  XPathFactory.newInstance().newXPath();

		         String expression = "mondial//country[1]";
		          NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
				            doc, XPathConstants.NODESET);
		       
		          System.out.println(Runtime.getRuntime().totalMemory());
		         //		         String expression = "catalog/book";
//		          NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
//				            doc, XPathConstants.NODESET);
		         System.out.println(nodeList.getLength());
//		         System.out.println(nodeList.item(0).getTextContent());
//		         nodeList=null;
//		         System.gc();
//		         Thread.sleep(3000);
		         System.out.println(Runtime.getRuntime().totalMemory());
		         Map m= new HashMap();
//		        
		         for (int i=0; i<nodeList.getLength();i++) {
		        	 Node e=  nodeList.item(i);
		        	 recursive(e,m);
//		        	 System.out.println(e.getAttributes().item(0).getNodeName() +" : "+e.getAttributes().item(0).getTextContent());
////		        	 System.out.println(e.hasChildNodes());
//		        	 if (e.hasChildNodes()) {
//		        	NodeList nl=	 e.getChildNodes();
//		        	for (int j=0 ;j<nl.getLength();j++) {	 
//		        		if(nl.item(j).getNodeName()!="#text")
//		        		{System.out.print(nl.item(j).getNodeName()+" : ");
//		        	
//		        		
//		        		System.out.println(nl.item(j).getTextContent().replaceAll("\n", "").replaceAll("\\s+", " ").replace("\t", " "));
//		        		
//		        		}
////		        		else
////		        			System.out.println(nl.item(j).getNodeValue());
//		        	if (j==nl.getLength()-1)
//		        		System.out.println();
//		        	}
//		        	 
//		        	 }
//		        	 
//		        	 
		         }
//		         expression = "catalog/book/description/text()";
//		         nodeList = (NodeList) xPath.compile(expression).evaluate(
//				            doc, XPathConstants.NODESET);
//		         System.out.println(nodeList.getLength());
//		         
//			        
//		         for (int i=0; i<nodeList.getLength();i++) {
//		        	 Node e=  nodeList.item(i);
//		        	 System.out.println(e.getTextContent().replaceAll("\n", "").replaceAll("\\s+", " ").replace("\t", " "));
//		        	 }
//		        	 
//		        	 
		         System.out.println(m);
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		}

void recursive(Node n,Map<String,Object> mes) {
	if(n.hasAttributes()) {
		Map m1 =new  HashMap();
		for(int i=0; i<n.getAttributes().getLength();i++) {
			System.out.println(n.getAttributes().item(i).getNodeName());
			m1.put(n.getAttributes().item(i).getNodeName().toString(), n.getAttributes().item(i).getNodeValue().toString());
		mes.put("attribute", m1);
		
		}
	}
	
	if(n.hasChildNodes()) {
		Map m2 =new  HashMap();
		Map ne= new HashMap();
		for(int i=0; i<n.getChildNodes().getLength();i++) {
			 Node e=  n.getChildNodes().item(i);
			 recursive(e,ne);
			 m2.put(n.getNodeName(),ne);
		}
		mes.put("child", m2);
	}
	
}
}
