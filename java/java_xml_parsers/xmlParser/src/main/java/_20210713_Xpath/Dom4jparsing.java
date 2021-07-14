package _20210713_Xpath;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dom4jparsing {
	static List allList=new ArrayList();
	static Map allData= new HashMap();
	static void read() {
		 try {
System.out.println(Runtime.getRuntime().totalMemory());
			 File inputFile = new File("book.xml");
	         SAXReader reader = new SAXReader();
	         Document document = reader.read( inputFile );

	         System.out.println("Root element :" + document.getRootElement().getName());

	         Element classElement = document.getRootElement();

	         List<Node> nodes = document.selectNodes("//@id" );
	         System.out.println(nodes.size());
//	         List<Node> nodes = document.selectNodes("mondial" );
//	         List<Node> nodes = document.selectNodes("mondial/country[1]" );
//	         List<Node> nodes = document.selectNodes("mondial/country[position()<5]" );
	         System.out.println("----------------------------");
	         
	         for (Node node : nodes) {
//	           if  (node.hasContent())
//	        	   System.out.println(node.asXML().toString());
//	        	System.out.println(node.toString());
	        	   
//	        	   parseNode(node);
//	     		System.out.println(node.getParent().getName());
//System.out.println(node.getPath());
//System.out.println(node.);
	        	 dom4jToDom(node.asXML().toString());
//	         
	         }
	      } catch (DocumentException e) {
	         e.printStackTrace();
	      }
	
		 System.out.println(Runtime.getRuntime().totalMemory());
	System.out.println(allData);
	}
	private static void parseNode(String string) {
		// TODO Auto-generated method stub
		try {
			String indent ="";
			org.w3c.dom.Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(string.getBytes()));
//			System.out.println(document.getFirstChild().getNodeName());
			org.w3c.dom.Node rootNode=document.getChildNodes().item(0);
			System.out.println(rootNode.getNodeName()+" : "+ (rootNode.getNodeValue()== null? "" : rootNode.getNodeValue().replaceAll("\\s+"," ").replaceAll("\n"," ")));//contry
			indent+="\t";
			if(rootNode.hasAttributes()) {//contry attribute
				NamedNodeMap att=rootNode.getAttributes();
				for (int i=0;i<att.getLength(); i++) {
					System.out.println(indent+att.item(i).getNodeName()+" : "+att.item(i).getNodeValue());
				}
			}
				
			if (rootNode.hasChildNodes()) {//name and city
				
				for(int i=0; i<rootNode.getChildNodes().getLength();i++) {
					String newIndent=indent;
					newIndent+="\t";
					org.w3c.dom.Node n = rootNode.getChildNodes().item(i);
//					if(n.hasChildNodes()) {
//						System.out.println(n.getChildNodes().item(0).getNodeValue());
//					}
//					last text n.getChildNodes().item(0).hasChildNodes());
					if(n.getNodeName()!="#text")
					System.out.println(newIndent+n.getNodeName()+" : "+ (n.getNodeValue()== null? "" : n.getNodeValue().replaceAll("\\s+"," ").replaceAll("\n"," ")));//contry
//					System.out.println(newIndent+n.getNodeName()+" : "+ (n.getTextContent()== null? "" : n.getTextContent().replaceAll("\\s+"," ").replaceAll("\n"," ")));//contry
//					
					
					newIndent+="\t";
					if(n.hasAttributes()) {//name and city attribute
						NamedNodeMap att=n.getAttributes();
//						System.out.println(att.getLength());
						for (int i1=0;i1<att.getLength(); i1++) {
//							System.out.println("k");
							if(att.item(i1).getNodeName()!="*text" && att.item(i1).hasChildNodes())
							System.out.println(newIndent+att.item(i1).getNodeName()+" : "+(att.item(i1).getNodeValue()== null? "" : att.item(i1).getNodeValue().replaceAll("\\s+"," ").replaceAll("\n"," ")));
						}
						
					}
					if(n.hasChildNodes()) {
						
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static  void parseNode(Node n) {
//		org.w3c.dom.Node nodeCopy= (org.w3c.dom.Node)n;
//		org.w3c.dom.Document w3cDom = new DOMWriter().write(n);
		System.out.println("c");
	}

private static void dom4jToDom(String n) {
	String indent ="";
	Map rowMap = new HashMap();
	org.w3c.dom.Document document;
	try {
		
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(n.getBytes()));
		org.w3c.dom.Node rootNode=document.getChildNodes().item(0);
//		recursiveParser(rootNode);
		recursiveParserToMap(rootNode,rowMap);
//		System.out.println(rowMap);\
//		System.out.println(rootNode.getParentNode().getNodeName());
		setToAllData(rowMap,rootNode.getNodeName());
		System.out.println(rootNode.getNodeName());
		System.out.println("==========================================================================================");
	} catch (SAXException | IOException | ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	System.out.println(document.getFirstChild().getNodeName());
	
}
	
private static void setToAllData(Map rowMap ,String baseName) {
	// TODO Auto-generated method stub
	List l=null;
	if(allData.get(baseName)==null||!(allData.get(baseName)instanceof List)) {
		l= new ArrayList();
	}else {
		l=(List) allData.get(baseName);
		
	}
	Map h=new HashMap();
	h.put(baseName,rowMap);
	l.add(h);
	allData.put(baseName, l);
}
private static void recursiveParser(org.w3c.dom.Node n) {
	org.w3c.dom.Node current_Node = n;
	if(current_Node.getNodeName()!="#text")
	System.out.println(current_Node.getNodeName()+"-=-=-=-=-=");
	if(current_Node.hasAttributes()) {//name and city attribute
		NamedNodeMap att=current_Node.getAttributes();

		for (int att_i=0;att_i<att.getLength(); att_i++) {
			if(att.item(att_i).getNodeName()!="#text" && att.item(att_i).hasChildNodes())
			System.out.println(att.item(att_i).getNodeName()+" : "+(att.item(att_i).getNodeValue()== null? "" : att.item(att_i).getNodeValue().replaceAll("\\s+"," ").replaceAll("\n"," ")));
		}
	}
		if(current_Node.hasChildNodes()) {
			NodeList childs = current_Node.getChildNodes();
			for (int child_i=0;child_i<childs.getLength();child_i++) {
				org.w3c.dom.Node child = childs.item(child_i);
//				if(child.getNodeName()!="#text")
			
				if(!(child.hasChildNodes())) {
					if((child.getTextContent().replaceAll("\n","").replaceAll("\\s+","").trim()).length()>1) {
						System.out.println(child.getTextContent().replaceAll("\n"," ").replaceAll("\\s+"," ").replace("<br/>", "{}").trim()+"l");
					}
				}
				
				else
				recursiveParser(child);
//				if(child.hasChildNodes()) {
//					NodeList decendents= child.getChildNodes();
//					for (int dec_i=0;dec_i<decendents.getLength();dec_i++) {
//						org.w3c.dom.Node decendent=decendents.item(dec_i);
//						if(decendent.getNodeName()!="#text")
//						recursiveParser(decendent);
//					}
//				}
//				else
//				{
//					System.out.println(child.getTextContent());
//				}
			}
			
		}else {
//			if(current_Node.getNodeType())
//			if(current_Node.getTextContent().replaceAll("\n"," ").trim()!="")
			System.out.println(current_Node.getTextContent());
		}
		
	}
	
	
	private static void addAtt(org.w3c.dom.Node n,Map m) {
		List l = null;
		if (m.get("attribute")==null||! (m.get("attribute") instanceof List)) {
			l=new ArrayList();
//			m.put("attribute", l);
		}
		else if (m.get("attribute") instanceof List) {
			l=(List) m.get("attribute");
		}
		Map h=new HashMap();
		h.put(n.getNodeName(),n.getNodeValue());
		l.add(h);
		
		m.put("attribute", l);

	}
private synchronized static void recursiveParserToMap(org.w3c.dom.Node n,Map row) {
	org.w3c.dom.Node current_Node = n;
	if(row==null) {
		row = new HashMap();
	}
	if(current_Node.hasAttributes()) {//name and city attribute
		NamedNodeMap att=current_Node.getAttributes();
		Map attMap= new HashMap();
		for (int att_i=0;att_i<att.getLength(); att_i++) {
			
			if(att.item(att_i).getNodeName()!="#text" && att.item(att_i).getNodeType()==2) {
				addAtt(att.item(att_i),row);
			}
		}
	}
		if(current_Node.hasChildNodes()) {
			NodeList childs = current_Node.getChildNodes();
			for (int child_i=0;child_i<childs.getLength();child_i++) {
				org.w3c.dom.Node child = childs.item(child_i);
				if(!(child.hasChildNodes())) {
					if((child.getTextContent().replaceAll("\n","").replaceAll("\\s+","").trim()).length()>1) {
						Map childMapWithName= new HashMap();
						childMapWithName.put("Text_Value",child.getTextContent().replaceAll("\n"," ").replaceAll("\\s+"," ").replace("<br/>", "{}").trim() );
						addChild(childMapWithName,row);
//						System.out.println(child.getTextContent().replaceAll("\n"," ").replaceAll("\\s+"," ").replace("<br/>", "{}").trim()+"l");
					}
				}
				
				else {
					Map childMap = new HashMap();
					recursiveParserToMap(child,childMap);
					Map childMapWithName= new HashMap();
					childMapWithName.put(child.getNodeName(), childMap);
					addChild(childMapWithName,row);
				}
//				recursiveParser(child);
			}
			
		}else  {
			System.out.println(current_Node.getTextContent());
		}
		
		
//		System.out.println(row);
		
	}
private static void addChild(Map childMap, Map row) {
	List l = null;
	if (row.get("child")==null||! (row.get("child") instanceof List)) {
		l=new ArrayList();
	}
	else if (row.get("child") instanceof List) {
		l=(List) row.get("child");
	}
	l.add(childMap);
	
	row.put("child", l);
	
}
	
	
	
}




