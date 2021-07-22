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

import javax.xml.namespace.QName;
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

public class Solution_8 {
	/** base path expression from lookup */
	static String base_path_expression = null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new TreeMap();

	public static void main(String[] args) {
		Solution_8 x = new Solution_8();
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
					|| lookup_map.get("base_path_expression").equalsIgnoreCase("")) ? "/"
							: lookup_map.get("base_path_expression");
			normalized_headers = (lookup_map.get("header") == null 
					|| lookup_map.get("header").equalsIgnoreCase("")) ? "" : lookup_map.get("header");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/** reads raw file */
	private void readXml() {
		List<Map<String, Object>> counters_list = new ArrayList<Map<String, Object>>();
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
		for (Map<String, Object> counter_data : counters_list) {
			System.out.println(counter_data);
		}
	}
	
	
	private Map<String, Object> read_header(Node base_node) {
		Map<String,Object> return_map= new HashMap<String,Object>();
		for(String head : normalized_headers.split(",")) {
			if(lookup_map.containsKey(head)) {
				QName type =null;
				String  expression= lookup_map.get(head);
				if(expression.contains("String")) {
					type = XPathConstants.STRING;
				}else if(expression.contains("number")) {
					type = XPathConstants.NUMBER;
				}else if(expression.contains("boolean")) {
					type = XPathConstants.BOOLEAN;
				}
				expression=expression.replace(')',' ').split("\\(")[1];
				return_map.put(head,get_node_by_xpath(base_node,expression,type) );
				if(	type == XPathConstants.NUMBER) {
					System.out.println(return_map.get(head) instanceof Integer);
				}
			}
		}
		
		return return_map;
	
	}
	private Object get_node_by_xpath(Object doc,String expression,QName type) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			return xPath.compile(expression).evaluate(doc,XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
