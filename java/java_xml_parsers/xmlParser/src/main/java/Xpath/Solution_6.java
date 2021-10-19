package Xpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Solution_6 {
	class inclas{
		int row_index=0;
		int object_index=0;
		int  base_index=0;
	}

	/** base path expression from lookup */
	static String base_path_expression = null;
	static String row_selection_node=null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new TreeMap();

	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		Solution_6 x = new Solution_6();
		x.init();
	
	}


	private void init() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
         
//        Document doc = db.parse(new FileInputStream(new File("in.xml")));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setNamespaceAware(true);

    	DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc=builder.newDocument();
        Element element = doc.getDocumentElement();
        Node node = doc.createElement("newnode");
        element.appendChild(node);
		
//		Document doc = null;
//		NodeList n;
//		try {
//			File inputFile = new File("device_sample.xml");
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
//			 doc = dBuilder.parse(new InputSource(new StringReader("<can><van><man>1</man></van></can>")));
//			}catch (Exception e) {e.printStackTrace();
//			}
//			
//		XPath xPath = XPathFactory.newInstance().newXPath();
//		try {
//		n=(NodeList) xPath.compile("//can").evaluate(doc,XPathConstants.NODESET );
//		System.out.println(n.item(0).getNodeName());
//		} catch (XPathExpressionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		readLookup();
		readXml();
	
	
	
	
	
	}
	/** reads lookup file */
	private void readLookup() {
		File lookup_file = new File("lookup.txt"); // create instance of file
		Map<String, String> temp_lookup_map = new TreeMap();
		try (FileReader fr = new FileReader(lookup_file); // reads the file
				BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
		) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] line_split = line.split(",", 2);
					if (line_split.length >= 2)
						temp_lookup_map.put(line_split[0], line_split[1]);
				}
			}
			lookup_map.putAll(temp_lookup_map);

			base_path_expression = (lookup_map.get("row_selection_node") == null
					|| lookup_map.get("row_selection_node").isEmpty()) ? "/"
							: lookup_map.get("row_selection_node");
			normalized_headers = (lookup_map.get("header") == null || lookup_map.get("header").isEmpty()
					) ? "" : lookup_map.get("header");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/** reads raw file */
	private void readXml() {
		List<Map<String, String>> counters_list = new ArrayList<Map<String, String>>();
		inclas obj= new inclas(); 
			Document doc = xml_file_to_dom("device_sample.xml");
			
			NodeList node_list =  (NodeList) get_node_by_xpath( doc,"//device",XPathConstants.NODESET);
			for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				obj.row_index=i;
				row_parsing(base_node);
//				counters_list.add(read_header(base_node));
			}

		for (Map<String, String> counter_data : counters_list) {
			System.out.println(counter_data);
		}
	}

	private Document xml_file_to_dom(String file)  {
		Document doc=null;
		try {
		File inputFile = new File("device_sample.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
		doc = dBuilder.parse(inputFile);
		}catch (Exception e) {e.printStackTrace();
		}
		return doc;
	}
private Object get_node_by_xpath(Document doc,String expression,QName type) {
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	try {
		return xPath.compile(expression).evaluate(doc,type );
	} catch (XPathExpressionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return null;
}
private Document xml_to_doc(Node n) {
//	Document doc = (Document) new DOMSource(n);
//	
//	

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);

	DocumentBuilder builder = null;
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	Node firstDocImportedNode = firstDoc.importNode(secondDocsNode, true);
//	firstDocNode.appendChild(firstDocImportedNode );
	
//	Document doc = builder.newDocument();
//	Node firstDocImportedNode = doc.importNode(n, true);
//	doc.appendChild(n);
//	System.out.println(doc.getChildNodes().item(0).getNodeName());
	
//	StringWriter buf = new StringWriter();
//    Transformer xform = TransformerFactory.newInstance().newTransformer().;
//    doc=xform.transform(new DOMSource(n), new StreamResult(buf));
	return null;
}
private void row_parsing( Node row_data) {
	Document doc=xml_to_doc(row_data);
	System.out.println(12);
	
	
	
	
//	
//	NodeList node_list =  (NodeList) get_node_by_xpath( doc,"//counter",XPathConstants.NODESET);
//	StringWriter buf = new StringWriter();
//	 try {
//	Transformer xform = TransformerFactory.newInstance().newTransformer();
// 
//	xform.transform(new DOMSource(doc), new StreamResult(buf));
//} catch (TransformerException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//  System.out.println(buf.toString());
}

}
