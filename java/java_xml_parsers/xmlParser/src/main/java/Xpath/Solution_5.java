package Xpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

public class Solution_5 {

	// objective
	// here up to down flow
	// starts from device node and go down through the flow and maps all data in the
	// flow
	// flow will be either devices->objects->counters-> values
	// or devices-> counters->values

	// copying few lines from solution 2
	/** base path expression from lookup */
	static String base_path_expression = null;
	static String normalized_headers = null;
	static Map<String, String> lookup_map = new TreeMap();
	static Map<String, String> counter_name = new HashMap();
	static Map<String, String> counter_rename = new HashMap();
	List global_map = new ArrayList();

	public static void main(String[] args) {
		Solution_5 x = new Solution_5();
		x.init();
	}

	private void init() {
		readLookup();
		readXml();
	}

	/** reads lookup file */
	private void readLookup() {
		File lookup_file = new File("lookup2.txt"); // create instance of file
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
			if (lookup_map.containsKey("rename_column")) {
				Arrays.asList(lookup_map.get("rename_column").split("~!")).stream().forEach(data -> {
					process(data);
				});

			}
			System.out.println(counter_name);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private Object process(String data) {
		// TODO Auto-generated method stub
		if (data.contains("@#")) {
			counter_rename.put(data.split("@#")[0], data.split("@#")[1]);
			counter_name.put(data.split("@#")[0], data.split("@#")[1]);
		} else {
			counter_name.put(data, data);
		}
		return null;
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
				map_these_node(base_node, base_node_map, "");
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
			// System.out.println(counter_data);
		}
		for (Object counter_data : global_map) {
			System.out.println(counter_data);
		}
		// System.out.println(global_map.size());

	}

	private Map<String, String> map_these_node(Node current_node, Map<String, Object> current_node_map, String prefix) {
		// first check for presence of attributes
		// if present iterate all and annote with att-
		boolean flag_last_set = true;
		if (current_node.hasAttributes()) {
			NamedNodeMap attributes = current_node.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				String[] data = attributes.item(i).toString().split("=");
				// map_data_adder(current_node_map, data[0], data[1],prefix);
				map_data_adder(current_node_map, data[1],
						prefix + (prefix == "" ? "" : "_") + current_node.getNodeName() + "_att-" + data[0]);
			}
		}

		// second check for presence of text nodes
		// if present asign to text key as a list
		if (current_node.hasChildNodes()) {
			NodeList child_nodes = current_node.getChildNodes();
			for (int i = 0; i < child_nodes.getLength(); i++) {
				if (child_nodes.item(i).getNodeType() == 3) {
					if ("".equals(child_nodes.item(i).getTextContent().replaceAll("\n", " ").replaceAll("\\s+", " ")
							.trim())) {
						continue;
					}
				} else if (child_nodes.item(i).getChildNodes().getLength() == 1
						&& child_nodes.item(i).getChildNodes().item(0).getNodeType() == 3) {
					// node type 3 means text node
					// map_data_adder(current_node_map, nl.item(i).getNodeName(),
					// nl.item(i).getChildNodes().item(0)
					// .getTextContent().replaceAll("\n", " ").replaceAll("\\s+", "
					// ").trim(),prefix+current_node.getNodeName()+nl.item(i).getNodeName());
					map_data_adder(current_node_map,
							child_nodes.item(i).getChildNodes().item(0).getTextContent().replaceAll("\n", " ")
									.replaceAll("\\s+", " ").trim(),
							prefix + (prefix == "" ? "" : "_") + current_node.getNodeName() + "_"
									+ child_nodes.item(i).getNodeName());

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
				else if (child_nodes.getLength() > 1
						|| (child_nodes.getLength() == 1 && child_nodes.item(0).getNodeType() != 3)) {
					Map<String, Object> child_node_map = new HashMap();
					child_node_map.putAll(current_node_map);
					// Map<String, Object> child_node_map = new HashMap<String, Object>();
					map_these_node(child_nodes.item(i), child_node_map,
							prefix + (prefix == "" ? "" : "_") + current_node.getNodeName());
					// map_data_adder(current_node_map, nl.item(i).getNodeName(),
					// child_node_map,prefix);
					flag_last_set = false;
				}
			}
		}
		// check for child element node
		// if present make a key and set value as result map in list type
		if (flag_last_set == true) {
			additional_changes_in_parsed_map(current_node_map);
			global_map.add(current_node_map);
		}
		return null;
	}

	/** changes can be made after parsing the datas from XML file */
	private void additional_changes_in_parsed_map(Map<String, Object> current_node_map) {
		String cid = (String) current_node_map.get("counter_id");
		if (counter_rename.containsKey(cid)) {
			current_node_map.put("counter_id", counter_rename.get(cid));
		}
		if (lookup_map.containsKey("static_columns")) {
			for (String data : lookup_map.get("static_columns").split("~!", 2)) {
				String[] data_split = data.split("=");
				current_node_map.put(data_split[0], data_split[1]);
			}
		}

	}

	private void map_data_adder(Map<String, Object> current_node_map, String nodevalue, String prefix) {
		// TODO Auto-generated method stub
		if (lookup_map.values().contains(prefix)) {
			String key = lookup_map.entrySet().parallelStream().filter(val -> val.getValue().equals(prefix))
					.map(val -> val.getKey()).collect(Collectors.joining(","));
			// String key= temp!=null?temp:"";
			// System.out.println(temp+":"+key);
			current_node_map.put(key, nodevalue);
		}
		// current_node_map.put(prefix, nodevalue);
		// lookup_map.entrySet().parallelStream().forEach((Entry value)->{if()});
		// System.out.println(lookup_map.entrySet().parallelStream().filter(val->val.getValue().equals(prefix)).map(val->
		// val.getKey()).collect(Collectors.joining(",")));
		// Stream<Entry<String, String>> a=
		// lookup_map.entrySet().parallelStream().filter((val)->
		// val.getKey()==prefix).collect(Collectors.toMap(val->{(val.getKey, (val
		// ->val.getValue));
		// System.out.println(a.collect(Collectors.toMap(val.get, valueMapper)));
	}
	//
	// void map_data_adder(Map<String, Object> base, String entry_key, Object
	// entry,String prefix) {
	// System.out.println(prefix);
	// System.out.println(entry_key+": "+entry);
	// if (base.containsKey(entry_key)) {
	// Object obj = base.get(entry_key);
	// if (obj instanceof List) {
	// ((List) obj).add(entry);
	// } else {
	// List<Object> l = new ArrayList<Object>();
	// l.add(base.get(entry_key));
	// l.add(entry);
	// base.put(entry_key, l);
	// }
	// } else {
	// base.put(entry_key, entry);
	// }
	// }

}
