package Xpath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
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
			normalized_headers = (lookup_map.get("header") == null || lookup_map.get("header").equalsIgnoreCase(""))
					? ""
					: lookup_map.get("header");

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
			File inputFile = new File("new2.xml");
			InputStream is = new FileInputStream(inputFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // instance of dom builder

			Document doc = dBuilder.parse(is);
			// dBuilder.parse(inputFile); // document object model created
			// doc.
			XPath xPath = XPathFactory.newInstance().newXPath();
			// base node from lookup    /devices/device/Objects/Object
//			NodeList node_list = (NodeList) xPath.compile(base_path_expression).evaluate(doc, XPathConstants.NODESET);
			NodeList node_list = (NodeList) xPath.compile("/devices/device/Objects/Object").evaluate(doc, XPathConstants.NODESET);
				for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				if(base_node.hasAttributes()) {
					NamedNodeMap nnm= base_node.getAttributes();
					System.out.println("get attribute");
					for (int ii=0 ;ii<nnm.getLength();ii++) {
						System.out.println("get attribute for loop");
						nnm.item(ii);
						System.out.println(nnm.item(ii).getNodeValue());
					}
					
				}
//				counters_list.add(read_header(base_node));
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		try (PrintWriter buf = new PrintWriter(new BufferedWriter(new FileWriter(new File("./test.csv"))));) {
			for (String head : normalized_headers.split(",")) {
				buf.print(head + ",");
			}
			buf.print("\n");
			for (Map<String, Object> counter_data : counters_list) {
				System.out.println(counter_data);
				for (String head : normalized_headers.split(",")) {
					if (counter_data.containsKey(head)) {
						buf.print(counter_data.get(head) + ",");
					} else {
						buf.print(",");
					}
				}
				buf.println();
			}
			buf.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> read_header(Node base_node) {
		Map<String, Object> return_map = new HashMap<String, Object>();
		for (String head : normalized_headers.split(",")) {
			if (lookup_map.containsKey(head)) {
				String[] temp = lookup_map.get(head).split(",");
				QName type = null;
				String expression = temp[0];
				
				System.out.println(expression);
				return_map.put(head, get_node_by_xpath(base_node, expression, type));
				if (head.equalsIgnoreCase("ne_datetime")) {
					if (temp.length == 3) {
						date_formatter(return_map, head, temp);
					}
				}
			}
		}
		if (lookup_map.containsKey("static_columns")) {
			String[] static_datas = lookup_map.get("static_columns").split("~!");
			for (String static_data : static_datas) {
				return_map.put(static_data.split("=")[0], static_data.split("=")[1]);
			}
		}
		return return_map;

	}

	private Object get_node_by_xpath(Object doc, String expression, QName type) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			if (expression.startsWith("number")) {
				type = XPathConstants.NUMBER;
			} else if (expression.startsWith("boolean")) {
				type = XPathConstants.BOOLEAN;
			} else {
				type = XPathConstants.STRING;
			}
			expression = expression.substring(0, expression.length() - 1).split("\\(", 2)[1];
			return xPath.compile(expression).evaluate(doc, type);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void date_formatter(Map<String, Object> return_map, String head, String[] temp) {
		try {
			String fileDate = return_map.get(head).toString();
			String giveDformat = temp[1];
			String tableDformat = temp[2];
			if (giveDformat.equalsIgnoreCase("unixTime")) {
				long fileDateTime = Long.valueOf(fileDate);
				if (fileDate.length() == 10) {
					fileDateTime = fileDateTime * 1000;
				}
				Date unixTimeDate = new java.util.Date(fileDateTime);
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
				//dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(dateTimeFormat.parse(dateTimeFormat.format(unixTimeDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return_map.put("ne_datetime", dateTimeFormat.format(unixTimeDate));
				return_map.put("ne_date", dateFormat.format(unixTimeDate));
				return_map.put("ne_hour", c.getTime().getHours());
				return_map.put("ne_minute", c.getTime().getMinutes());
			} else {
				SimpleDateFormat givenDateFormat = new SimpleDateFormat(giveDformat);
				Date date = givenDateFormat.parse(fileDate);
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				return_map.put("ne_datetime", dateTimeFormat.format(date));
				return_map.put("ne_date", dateFormat.format(date));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

}
