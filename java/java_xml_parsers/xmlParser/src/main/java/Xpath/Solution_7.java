package Xpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.bind.v2.runtime.property.Property;

public class Solution_7 {
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
		Solution_7 x = new Solution_7();
		x.init();
	
	}


	private void init() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {

		readLookup();
		readXml();
	
	}
	/** reads lookup file */
	private void readLookup() {
		File lookup_file = new File("lookup3.txt"); // create instance of file
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
					|| lookup_map.get("base_path_expression").isEmpty()) ? "/"
							: lookup_map.get("base_path_expression");
			row_selection_node = (lookup_map.get("row_selection_node") == null
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
			List result_list= new ArrayList();
			NodeList node_list =  (NodeList) get_node_by_xpath( doc,row_selection_node,XPathConstants.NODESET);
			for (int i = 0; i < node_list.getLength(); i++) {
				Node base_node = node_list.item(i);
				obj.row_index=i;
				List temp=row_parsing(base_node);
				if (temp!=null) {
					result_list.addAll(temp);
				}
//				counters_list.add(read_header(base_node));
			}
System.out.println(result_list);
		for (Map<String, String> counter_data : counters_list) {
			System.out.println(counter_data);
		}
	}
/**  read the row wise data nodes */
	private List row_parsing( Node row_data) {
		List row_all_base_list = new ArrayList();

		Document doc=xml_to_doc(row_data);
		NodeList node_list =  (NodeList) get_node_by_xpath( doc,base_path_expression,XPathConstants.NODESET);
		for( int i=0;i<node_list.getLength();i++) {
			Map base_map= new HashMap();
		base_element_iteration(doc,i,base_map);
		row_all_base_list.add(base_map);
		}

return row_all_base_list;
	}
	
	
	
	private void base_element_iteration(Document doc, int i, Map base_map) {
for(String head: normalized_headers.split(",")) {
	Object node_value=null ;
	if(lookup_map.containsKey(head)) {
	String expression=lookup_map.get(head);
	String data_type= expression.split("_")[0];
	String data_expression=expression.split("_")[1];
	data_expression=data_expression.replace("baseIndex", String.valueOf(i+1));
//	System.out.println(data_type+ data_expression);
	if(data_type.equalsIgnoreCase("string")) {
		node_value =  (String) get_node_by_xpath( doc,data_expression,XPathConstants.STRING);
		}

	
	
	
	base_map.put(head, node_value);
		
	}
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
		e.printStackTrace();
	}
return null;
}
/** converts  node element to dom source then convert to xml string then convert to document*/
private Document xml_to_doc(Node n) {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	 Document doc=null;
	try {
		builder = factory.newDocumentBuilder();
	StringWriter buf = new StringWriter();
    Transformer xform = TransformerFactory.newInstance().newTransformer();
    Properties props= new Properties();
	props.setProperty(OutputKeys.INDENT, "yes");
    props.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    xform.setOutputProperties(props);
    xform.transform(new DOMSource(n), new StreamResult(buf));
   doc = builder.parse(new InputSource(new StringReader(buf.toString())));
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
    return doc;
}
 


}
