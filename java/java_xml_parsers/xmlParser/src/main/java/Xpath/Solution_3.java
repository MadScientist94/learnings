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

//objective  
// here up to down flow
//starts from device node and go down through the flow and maps all data in the flow 
//flow will be either devices->objects->counters-> values
//or devices-> counters->values
public class Solution_3 {
	// copying few lines from solution 2
	/** base path expression from lookup */
	static String base_path_expression = null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new TreeMap();

	public static void main(String[] args) {
		Solution_3 x = new Solution_3();
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
		List<Map<String, String>> counters_list = new ArrayList<Map<String, String>>();
		try {

			File inputFile = new File("device_sample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder
			Document doc = dBuilder.parse(inputFile); // document object model created
			XPath xPath = XPathFactory.newInstance().newXPath();
			// base node from lookup
			base_path_expression = "//device";
			NodeList node_list = (NodeList) xPath.compile(base_path_expression).evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				Map base_node_map = new HashMap<String, Object>();
				map_these_node(base_node, base_node_map);
				counters_list.add(base_node_map);
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		for (Map<String, String> counter_data : counters_list) {
			System.out.println(counter_data);
		}
	}

	private Map<String, String> map_these_node(Node current_node, Map<String, Object> current_node_map) {
		// first check for presence of attributes
		// if present iterate all and annote with att-

		if (current_node.hasAttributes()) {
			NamedNodeMap attributes = current_node.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				String[] data = attributes.item(i).toString().split("=");
				map_data_adder(current_node_map, data[0], data[1]);
			}
		}

		// second check for presence of text nodes
		// if present asign to text key as a list
		if (current_node.hasChildNodes()) {
			NodeList nl = current_node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == 3) {
					if ("".equals(nl.item(i).getTextContent().replaceAll("\n", " ").replaceAll("\\s+", " ").trim())) {
						continue;
					}
				} else if (nl.item(i).getChildNodes().getLength() == 1
						&& nl.item(i).getChildNodes().item(0).getNodeType() == 3) {
					// node type 3 means text node
					map_data_adder(current_node_map, nl.item(i).getNodeName(), nl.item(i).getChildNodes().item(0)
							.getTextContent().replaceAll("\n", " ").replaceAll("\\s+", " ").trim());
				}
				// if parent element starting elemrnt close bracket and the child element open
				// bracket doesnt have space then get length is one
				// ie <parent><child></child></parent>
				// where as child element also didnt have spaces in between so it didnt have
				// nodes in between
				// if <parent><child> </child></parent> here there is a text node between child
				// element starting and ending tag
				// if <parent> <child></child></parent> here child didnt have text node but
				// parent element has a text node
				else if (nl.getLength() > 1 || (nl.getLength() == 1 && nl.item(0).getNodeType() != 3)) {
					Map<String, Object> child_node_map = new HashMap<String, Object>();
					map_these_node(nl.item(i), child_node_map);
					map_data_adder(current_node_map, nl.item(i).getNodeName(), child_node_map);
				}
			}
		}
		// check for child element node
		// if present make a key and set value as result map in list type
		return null;
	}

	void map_data_adder(Map<String, Object> base, String entry_key, Object entry) {
		if (base.containsKey(entry_key)) {
			Object obj = base.get(entry_key);
			if (obj instanceof List) {
				((List) obj).add(entry);
			} else {
				List<Object> l = new ArrayList<Object>();
				l.add(base.get(entry_key));
				l.add(entry);
				base.put(entry_key, l);
			}
		} else {
			base.put(entry_key, entry);
		}
	}

}
