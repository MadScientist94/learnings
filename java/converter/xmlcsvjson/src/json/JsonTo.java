package json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonTo {
public static void main(String [] args) throws XMLStreamException, IOException {
	XmlMapper xmlMapper = new XmlMapper();
	XMLInputFactory xmlif = XMLInputFactory.newFactory();
	FileReader fr = new FileReader("f:\\planet.xml");
	XMLStreamReader xmlsr = xmlif.createXMLStreamReader(fr);
	Planet planet = xmlMapper.readValue(xmlsr, Planet.class);
	ObjectMapper jsonMapper = new ObjectMapper();
	String json = jsonMapper.writeValueAsString(planet);
	System.out.println(json);
}
}
 class Planet
{
   public String name;
   public Integer planet_from_sun;
   public Integer moons;
}