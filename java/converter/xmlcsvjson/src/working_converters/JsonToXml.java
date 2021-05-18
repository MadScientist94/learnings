package working_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonToXml {

	public static void main(String[] args) {
		reader r= new reader();
		ObjectMapper objectMapper = new ObjectMapper();
		 ObjectMapper xmlMapper = new XmlMapper();
	    JsonNode tree = null;
	    String jsonAsXml = null;
		String json=r.read("f:\\student.json").toString();
		System.out.println("json to xml");
		 
	     try {
			tree = objectMapper.readTree(json);
			jsonAsXml = xmlMapper.writer().withRootName("students").writeValueAsString(tree);
	     } catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	     System.out.println(jsonAsXml);
	     r.write("f:\\JsonToXml.xml",jsonAsXml );

	}

}
