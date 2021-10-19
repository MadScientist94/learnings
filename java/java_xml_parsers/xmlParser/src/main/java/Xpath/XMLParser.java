package Xpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	/** base path expression from lookup */
	static String base_path_expression = null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new HashMap();

	public static void main(String[] args) {
		XMLParser x = new XMLParser();
		x.init();
	}

	private void init() {
		readLookup();
		readXml();
	}

	/** reads lookup file */
	private void readLookup() {
		File lookup_file = new File("lookup.txt"); // create instance of file
		Map<String, String> temp_lookup_map = new HashMap();
		try (FileReader fr = new FileReader(lookup_file); // reads the file
				BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
		) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] line_split = line.split(",", 2);
					System.out.println(line_split.length);
					if (line_split.length >= 2)
						temp_lookup_map.put(line_split[0], line_split[1]);
					System.out.println(temp_lookup_map);
				}
			}
			lookup_map.putAll(temp_lookup_map);

			base_path_expression = (lookup_map.get("base_path_expression") == null
					|| lookup_map.get("base_path_expression").isEmpty()
					|| lookup_map.get("base_path_expression").equals("")) ? "/"
							: lookup_map.get("base_path_expression");
			normalized_headers = (lookup_map.get("header") == null || lookup_map.get("header").isEmpty()
					|| lookup_map.get("header").equals("")) ? "" : lookup_map.get("header");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/** reads raw file */
	private void readXml() {
List<String> l= new ArrayList<String>();
		try {

			File inputFile = new File("device_sample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
			Document doc = dBuilder.parse(inputFile); // document object model created
			XPath xPath = XPathFactory.newInstance().newXPath();
			System.out.println(base_path_expression);
			NodeList node_list = (NodeList) xPath.compile(base_path_expression).evaluate(doc, XPathConstants.NODESET); // base
																														// node
																														// from
																														// lookup
			System.out.println(node_list.getLength());
			for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				l.add(parse_base_node(base_node));
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		l.stream().forEach(d-> {System.out.println(d);});

	}

	private String parse_base_node(Node base_node) {
		String rowString=null;
		if (normalized_headers != null || normalized_headers != "") {
			rowString="";
			for (String header : normalized_headers.split(",")) {
				if (lookup_map.containsKey(header)) {
					 rowString+=header+"="+dependent_node(base_node, lookup_map.get(header))+"~!";
				}
			}
		}
	return rowString;
	}

	private String dependent_node(Node base_node, String header_value_node) {

		if (header_value_node.startsWith("parent")) {
			return parent_node_value(base_node, header_value_node.split("~!~", 2)[1]);
		} else if (header_value_node.startsWith("child")) {
			return child_node_value(base_node, header_value_node.split("~!~", 2)[1]);
		}
		return null;
	}

	private String child_node_value(Node base_node, String header_value_node) {
		String[] node_name_value_type = header_value_node.split("~!~");
		Node current_node = base_node;
//		System.out.println("child starts");
		NodeList nl = current_node.getChildNodes();
		int count = 0;
		int nl_len = nl.getLength();
		while (!node_name_value_type[0].equals(current_node.getNodeName())) {
			if (!nl.item(count).getNodeName().equals("#text"))
				current_node = nl.item(count);
			++count;
			if (count >= nl_len || current_node.getNodeName().equals(node_name_value_type[0])) {
				break;
			}
		}
		if(node_name_value_type[1].equals("text")) {
			return get_text_value(current_node);
		}else if(node_name_value_type[1].startsWith("att-")) {
			return get_att_value(current_node, node_name_value_type[1].split("-",2)[1]);
		}
	return null;	
	}

	private String get_text_value(Node current_node) {
		return current_node.getTextContent();
		
		
	}
	private String get_att_value(Node current_node,String att_name) {
		if(current_node.hasAttributes()) {
		NamedNodeMap att= current_node.getAttributes();
		for(int i=0; i<att.getLength();i++) {

		if(att.item(i).toString().split("=")[0].equals(att_name))
			return att.item(i).toString().split("=")[1];
		}
		}
		
		return null;
	}

	private String parent_node_value(Node base_node, String header_value_node) {
		String[] node_name_value_type = header_value_node.split("~!~");
		Node current_node = base_node;
		while(!current_node.getNodeName().equals(node_name_value_type[0])) {
		current_node =  current_node.getParentNode();
		}
		
		if(node_name_value_type[1].startsWith("att")) {
			return get_att_value(current_node, node_name_value_type[1].split("-",2)[1]);
		}
		return null;
	}
}
