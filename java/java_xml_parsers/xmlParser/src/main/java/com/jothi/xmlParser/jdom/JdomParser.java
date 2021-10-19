package com.jothi.xmlParser.jdom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JdomParser {
void parse() {
	try {
	         File inputFile = new File("./src/main/java/com/jothi/xmlParser/xmlParser/input.xml");
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(inputFile);
	         System.out.println("Root element :" + document.getRootElement().getName());
	         Element classElement = document.getRootElement();

	         List<Element> studentList = classElement.getChildren();
	         System.out.println("----------------------------");

	         for (int temp = 0; temp < studentList.size(); temp++) {    
	            Element student = studentList.get(temp);
	            System.out.println("\nCurrent Element :" 
	               + student.getName());
	            Attribute attribute =  student.getAttribute("rollno");
	            System.out.println("Student roll no : " 
	               + attribute.getValue() );
	            System.out.println("First Name : "
	               + student.getChild("firstname").getText());
	            System.out.println("Last Name : "
	               + student.getChild("lastname").getText());
	            System.out.println("Nick Name : "
	               + student.getChild("nickname").getText());
	            System.out.println("Marks : "
	               + student.getChild("marks").getText());
	         }
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
}


void modify() {
try {
    File inputFile = new File("./src/main/java/com/jothi/xmlParser/xmlParser/input.xml");
    SAXBuilder saxBuilder = new SAXBuilder();
    Document document = saxBuilder.build(inputFile);
    System.out.println("Root element :" + document.getRootElement().getName());
    Element classElement = document.getRootElement();

    List<Element> student = classElement.getChildren("student");
    System.out.println("----------------------------");

    for (int temp = 0; temp < student.size(); temp++) {    
       Element studentsElement = student.get(temp);
       System.out.println("\nCurrent Element :" + studentsElement.getName());
//       Attribute attribute = studentElement.getAttribute("company");
//       System.out.println("company : " + attribute.getValue() );
       List<Element> studentList = studentsElement.getChildren();
//       System.out.println(carNameList);
       for (int count = 0; count < studentList.size(); count++) {	 
          Element studentElement = studentList.get(count);
          System.out.print(studentElement.getQualifiedName()+ " : ");
          System.out.println(studentElement.getText());
//          System.out.print("car type : ");
//          Attribute typeAttribute = carElement.getAttribute("type");
//          
//          if(typeAttribute != null)
//             System.out.println(typeAttribute.getValue());
//          else {
//             System.out.println("");
//          }
       }
    }
 } catch(JDOMException e) {
    e.printStackTrace();
 } catch(IOException ioe) {
    ioe.printStackTrace();
 }}



void cd() {
	List Class= new ArrayList(); 
	List student= new ArrayList(); 
	Map details= new LinkedHashMap();
	details.put("student rollno",1);
	details.put("firstname","jo");
	details.put("lastname","v");
	details.put("nickname","jothi");
	details.put("mark",60);
	
	student.add(details);
	details= new LinkedHashMap();
	details.put("student rollno",2);
	details.put("firstname","hang");
	details.put("lastname","pimp");
	details.put("nickname","ant");
	details.put("mark",78);

	student.add(details);
	details= new LinkedHashMap();
	
	details.put("student rollno",3);
	details.put("firstname","antony");
	details.put("lastname","stark");
	details.put("nickname","iron");
	details.put("mark",99);
	

	student.add(details);
	Class.add(student);
	System.out.println(Class);
	try{
        //root element
        Element classElement = new Element("class");
        Document doc = new Document(classElement);

        //supercars element
        Element supercarElement = new Element("student");
        supercarElement.setAttribute(new Attribute("company","Ferrari"));

        //supercars element
        Element carElement1 = new Element("carname");
        carElement1.setAttribute(new Attribute("type","formula one"));
        carElement1.setText("Ferrari 101");

        Element carElement2 = new Element("carname");
        carElement2.setAttribute(new Attribute("type","sports"));
        carElement2.setText("Ferrari 202");

        supercarElement.addContent(carElement1);
        supercarElement.addContent(carElement2);

        doc.getRootElement().addContent(supercarElement);

        XMLOutputter xmlOutput = new XMLOutputter();

        // display ml
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, System.out); 
     } catch(IOException e) {
        e.printStackTrace();
     }
}


}
