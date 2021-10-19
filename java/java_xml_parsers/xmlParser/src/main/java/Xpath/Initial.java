package Xpath;

import java.lang.instrument.Instrumentation;

public class Initial {

	private static Instrumentation instrumentation;

	public static void main(String[] args) {
		Methods m = new Methods();
		// m.read();
		String node_path = "/devices/device/name/value/w1/w2/w3/f4";
		String base_path = "/devices/device/object/measurement/value";
		String[] node_path_arr = node_path.split("/");
		String[] base_path_arr = base_path.split("/");
		String node_path_expr = "";
		String base_path_expr = "";

		int count = 0;
		boolean parse_expr = true;
		boolean dont_create_expr = true;
		while (parse_expr) {
			System.out.println(count);
			if (dont_create_expr) {
				dont_create_expr = node_path_arr[count].equals(base_path_arr[count]);
			}
			if (dont_create_expr) {
//				System.out.println("");
			
			} else {
				System.out.println(count);
				if (node_path_arr.length > count) {
					node_path_expr += "/" + node_path_arr[count];
				}
				if (base_path_arr.length > count) {
					base_path_expr += "../";
				}
			}
			count++;
			if (node_path_arr.length <= count && base_path_arr.length <= count) {
				parse_expr = false;
			}
		}
		System.out.println(base_path_expr + "." + node_path_expr);

		// System.out.println(instrumentation.getObjectSize(m));
	}

}

// File statusFile = getStatusFile((SolrSearchServiceImpl) searchService);
// boolean exists = statusFile.exists();
// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
// dbf.setNamespaceAware(true);
// dbf.setIgnoringElementContentWhitespace(true);
// dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
// updateDeadEventSegment(document, rootElement, status, clearDeadEvents);
// TransformerFactory tFactory = TransformerFactory.newInstance();
// Transformer xmlTransformer = tFactory.newTransformer();
// xmlTransformer.setOutputProperty(OutputKeys.VERSION, "1.0");
// xmlTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
// xmlTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
// xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
// DOMSource source = new DOMSource(document);
// BufferedWriter writer = null;
// try {
// writer = new BufferedWriter(new OutputStreamWriter(new
// FileOutputStream(statusFile, false), "UTF-8"));
// StreamResult result = new StreamResult(writer);
// xmlTransformer.transform(source, result);
// } finally {

// 1) Convert String to XML Document
// To convert XML string to XML Dom, we need following classes:
//
// javax.xml.parsers.DocumentBuilder : Defines the API to obtain XML DOM
// Document instances from an XML content from a variety of input sources. These
// input sources are InputStreams, Files, URLs, and SAX InputSources.
// javax.xml.parsers.DocumentBuilderFactory : Defines a factory API that enables
// applications to obtain a parser (DocumentBuilder) that produces DOM object
// trees from XML content.
// org.w3c.dom.Document : It represents the entire XML DOM. Conceptually, it is
// the root of the document tree, and provides the access to the document’s data
// further down into the tree, through factory methods.
// java.io.StringReader : Create a stream from String content. DocumentBuilder
// uses this stream to read XML content for parsing.