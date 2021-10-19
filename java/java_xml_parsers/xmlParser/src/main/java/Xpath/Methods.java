package Xpath;

import java.io.File;
import java.io.StringWriter;
//import java.lang.instrument.Instrumentation;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Methods {
	Instrumentation instrumentation;

	public void read() {
		// System.out.println(Runtime.getRuntime().totalMemory());
		
		
		
		
		
		
		
//		try {
//			Class<?> loadedMyClass;
//			URL[] urls = { new File("F:\\cng\\th1.jar").toURI().toURL() };
////			for(URL u: urls) {
////				System.out.println(u.toString());
////			}
//			URLClassLoader urlLoader = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());
//			loadedMyClass = urlLoader.loadClass("th.try1");
//			System.out.println(loadedMyClass.getName());
//			
//			Constructor<?> constructor = loadedMyClass.getConstructor(new Class[] {});			
//			Object classObject = constructor.newInstance();
//			System.out.println(classObject.toString());
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
		
		
		
		
		try {
			File inputFile = new File("device_sample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;

			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			// System.out.println(Runtime.getRuntime().totalMemory());
			XPath xPath = XPathFactory.newInstance().newXPath();
//			String expression = "format-dateTime(//device/time/text())";
			
			// String expression = "//devices/device[first()]";// not worn\king
			// String expression = "//counter[first()]";// not worn\king
			// String expression = "//devices[descendant::name[text()=" + "'" +"Dev1" + "'"
			// + "]]";
			// String expression = "//counter[descendant::name[text()!=" + "'" +"" + "'" +
			// "]]";
			// String expression = "//counter[ancestor::device/time[text()!=" + "'" +"" +
			// "'" + "]]";
			// String expression = "//counter[descendant::name[text()!=" + "'" +"" + "'" +
			// "]]";
			// String expression = "namespace:://counter";//not working
			// String expression = "//counter[child::node()]";// just picking counter
			// String expression = "//device[child::*/child::counter/name]";
			// String expression = "//devices/device/comment()";
			// String expression = "//counter/position()";//Unknown nodetype: position
			// String expression =
			// "//counter[descendant::value[normalize-space(text())!='']]";
			// String expression = "//counter[child::value[normalize-space(text())!='']]";
			// String expression = "//counter[child::value[position()!=last()]]";
			// String expression = "//counter/name[contains(text(),'counter1')]/..";
			// String expression = "//counter/name[starts-with(text(),'counter')]/..";
			// String expression = "//counter/name[ends-with(text(),'1')]/..";//Could not
			// find function: ends-with
			// String expression = "//counter[last()]";
			// String expression = "(//counter)[3]";// it takes all counters first then
			// provide index so if two main tag contain three sub tage in each main tag
			// if we check sub tag counter we get max of 3 only but by grouping tha output
			// then provide index all sub tags are grouped no all sub tags are commonly
			// indexed
			// String expression = "(//counter)[5]/node()";//returns nodes of the selection
			// System.out.println(xPath.compile("sum(//counter/value)").evaluate(doc,
			// XPathConstants.NUMBER));
			// System.out.println(xPath.compile("ceiling(//counter/value)").evaluate(doc,
			// XPathConstants.NUMBER));
			// System.out.println(xPath.compile("round(//counter/value)").evaluate(doc,
			// XPathConstants.NUMBER));
			// System.out.println(xPath.compile("floor(//counter/value)").evaluate(doc,
			// XPathConstants.NUMBER));

			// <==============================================================================================>
			 String expression = "//counter/../..";
			 NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc,
			 XPathConstants.NODESET);
			 String expression2 = "//device";
			 NodeList nodeList2 = (NodeList) xPath.compile(expression2).evaluate(doc,
					 XPathConstants.NODESET);
			 Node n=nodeList.item(0);
			 Node n2=nodeList.item(0);
			 System.out.println(n.equals(n2));
			 System.out.println(n.getNodeName());
			 System.out.println("res");
//			 NodeList nodeList2=(NodeList)xPath.compile("./node()").evaluate(n,XPathConstants.NODESET);
//			 Node n2=nodeList2.item(1);
//			 System.out.println(n2.getNodeName());
//			 local-name(node-set?)
//			 System.out.println(xPath.compile("./node()/nodename").evaluate(n,XPathConstants.STRING));
				
			 System.out.println((xPath.compile("./node()[position()=4  ]/text()").evaluate(n,XPathConstants.STRING)));
			 System.out.println((xPath.compile("./value[position()=1]/text()").evaluate(n,XPathConstants.STRING)));
			 
				
//			 System.out.println(((NodeList)xPath.compile("./child[position()=1]").evaluate(n,XPathConstants.NODESET)).item(1).getNodeType());
//			 System.out.println(xPath.compile("./child::text()[position=1]").evaluate(n,XPathConstants.STRING));
//			 System.out.println(xPath.compile("./child::text()[position=2]").evaluate(n,XPathConstants.STRING));
//			 System.out.println(xPath.compile("./child::text()[position=3]").evaluate(n,XPathConstants.STRING));
//			 System.out.println(xPath.compile("./child[position=4]/text()").evaluate(n,XPathConstants.STRING));
//			 System.out.println(xPath.compile("./child[position=5]/text()").evaluate(n,XPathConstants.STRING));
			 System.out.println("end");
			 
			 
//			 for( int i=0;i<nodeList.getLength();i++) {
			// String s1="(//counter)["+(i+1)+"]/name/text()";
			// String s2="(//counter)["+(i+1)+"]/value/text()";
			// String ex="concat ('the value of ',"+s1+", 'is ',"+s2;
			// System.out.println( xPath.compile("concat('counter name : ',"+s1+", '& value
			// : ' ,"+s2+')').evaluate(doc, XPathConstants.STRING));
			// }
			// <==============================================================================================>

//			 String nodeList = (String) xPath.compile(expression).evaluate(doc,
//			 XPathConstants.STRING);
//			 System.out.println(nodeList);
//			 String expression = "id((//counter)[1]/name)";
//			 System.out.println( xPath.compile(expression).evaluate(doc, XPathConstants.NODE));
//			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
//			System.out.println(nodeList.getLength());

//			for (int i = 0; i < nodeList.getLength(); i++) {
//			}
			// // expression = "//counter/value[number(text())>200]";
			// nodeList = (Node)
			// System.out.println(xPath.compile(expression).evaluate(
			// nodeList, XPathConstants.NODESET));
			// System.out.println(nodeList);
			// expression = "../../@id";
			// nodeList = (Node) xPath.compile(expression).evaluate(
			// nodeList, XPathConstants.NODE);
			// System.out.println(nodeList.getNodeValue()+8); // devices device 1 counters
			// counter
			// System.out.println(nodeList.getChildNodes().item(0).getChildNodes().item(5).getChildNodes().item(5).getChildNodes().item(1).compareDocumentPosition(nodeList));
			// Node n= nodeList.getChildNodes().item(0);
			// System.out.println(n.compareDocumentPosition(nodeList));
			// System.out.println(xPath.compile("//devices").evaluate(
			// doc, XPathConstants.NODE));
			// StringWriter buf = new StringWriter();
			// Transformer xform = TransformerFactory.newInstance().newTransformer();
			// xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // optional
			// xform.setOutputProperty(OutputKeys.INDENT, "yes"); // optional
			// xform.transform(new DOMSource(nodeList), new StreamResult(buf));
			// System.out.println(buf.toString()); // your string
			// Map m= new HashMap();
			//
			// for (int i=0; i<nodeList.getLength();i++) {
			// Node e= nodeList.;
			// recursive(e,m);
			// }
			// System.out.println(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void recursive(Node n, Map<String, Object> mes) {
		if (n.hasAttributes()) {
			Map m1 = new HashMap();
			for (int i = 0; i < n.getAttributes().getLength(); i++) {
				System.out.println(n.getAttributes().item(i).getNodeName());
				m1.put(n.getAttributes().item(i).getNodeName().toString(),
						n.getAttributes().item(i).getNodeValue().toString());
				mes.put("attribute", m1);

			}
		}

		if (n.hasChildNodes()) {
			Map m2 = new HashMap();
			Map ne = new HashMap();
			for (int i = 0; i < n.getChildNodes().getLength(); i++) {
				Node e = n.getChildNodes().item(i);
				recursive(e, ne);
				m2.put(n.getNodeName(), ne);
			}
			mes.put("child", m2);
		}

	}
}
