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
import java.util.TreeMap;

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

public class Solution_2 {
	/** base path expression from lookup */
	static String base_path_expression = null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new TreeMap();

	public static void main(String[] args) {
		Solution_2 x = new Solution_2();
		x.init();
	}

	private void init() {
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
		List<Map<String,String>> counters_list = new ArrayList<Map<String,String>>();
		try {

			File inputFile = new File("device_sample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
			Document doc = dBuilder.parse(inputFile); // document object model created
			XPath xPath = XPathFactory.newInstance().newXPath();
			// base node from lookup
			NodeList node_list = (NodeList) xPath.compile(base_path_expression).evaluate(doc, XPathConstants.NODESET); 
			for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				counters_list.add(read_header(base_node));
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
for(Map<String, String> counter_data : counters_list) {
	System.out.println(counter_data);
}
	}

	Map<String,String> read_header(Node base_node) {
		Map<String,String> counter_map=new TreeMap<String,String>();
		for(String header : normalized_headers.split(",")) {
			if(lookup_map.containsKey(header)) {
				counter_map.put(header,node_value_or_node_chooser(base_node, lookup_map.get(header)));
			}
		}
		if(lookup_map.containsKey("static_columns")) {
			for(String data : lookup_map.get("static_columns").split("~!")) {
				String [] d=data.split("=");
				counter_map.put(d[0],d[1]);
			}
		}
	return counter_map;
	}

	String node_value_or_node_chooser(Node current_node, String flow_string) {
		String [] flow_String_array=flow_string.split("~!~",2);
		String first_line = flow_String_array[0];
		String new_flow_string = flow_String_array.length==2?flow_String_array[1]:flow_String_array.length==1?flow_String_array[0]:null;
		if (first_line.equals("parent")) {
			return move_to_parent_node(current_node, new_flow_string);
		}
		if (first_line.equals("child")) {
			return move_to_child_node(current_node, new_flow_string);
		}
		if (first_line.equals("text")) {

			return get_text(current_node);
		}
		if (first_line.startsWith("att~=")) {
			String att_name = first_line.split("~=")[1];
			return get_att(current_node, att_name);
		}
		return null;
	}

	String move_to_parent_node(Node current_node, String flow_string) {
		String[] node_name_value_type = flow_string.split("~!~",2);
		Node new_node = current_node;
		while (!new_node.getNodeName().equals(node_name_value_type[0])) {
			new_node = new_node.getParentNode();
		}
		if (node_name_value_type[0].equals(new_node.getNodeName())) {
			return node_value_or_node_chooser(new_node, node_name_value_type[1]);
		}

		return "";
	}

	String move_to_child_node(Node current_node, String flow_string) {
		String[] node_name_value_type = flow_string.split("~!~", 2);
		Node new_node = current_node;
		NodeList nl = new_node.getChildNodes();
		int count = 0;
		int nl_len = nl.getLength();
		while (!node_name_value_type[0].equals(new_node.getNodeName())) {
			if (!nl.item(count).getNodeName().equals("#text"))
				new_node = nl.item(count);
			++count;
			if (count >= nl_len || new_node.getNodeName().equals(node_name_value_type[0])) {
				break;
			}
		}
		if (node_name_value_type[0].equals(new_node.getNodeName())) {
			return node_value_or_node_chooser(new_node,node_name_value_type.length==2? node_name_value_type[1]: node_name_value_type.length==1? node_name_value_type[0]:null);
		}
		return null;
	}

	String get_text(Node current_node) {
		if (current_node.getChildNodes().getLength() == 1)
			return current_node.getTextContent();
		return null;
	}

	String get_att(Node current_node, String att_name) {
		if (current_node.hasAttributes()) {
			NamedNodeMap att = current_node.getAttributes();
			for (int i = 0; i < att.getLength(); i++) {

				if (att.item(i).toString().split("=")[0].equals(att_name))
					return att.item(i).toString().split("=")[1];
			}
		}

		return null;
	}

}
