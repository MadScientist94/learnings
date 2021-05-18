package com.jo.XmlToPojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

/**
 * Hello world!
 *
 */
public class App 
{
	/**
	* This function writes serializes the Java object into XML and writes it
	* into an XML file.
	*/
	public static void serializeToXML() {
	    try {
	        XmlMapper xmlMapper = new XmlMapper();

	        
	     // create a list of other phones
	        List<String> otherPhones = Arrays.asList("OnePlus 6T", "OnePlus 5T", "OnePlus 5");

	        // create the manufacturer object
	        Manufacturer manufacturer = new Manufacturer("OnePlus", "China", otherPhones);

	        // serialize our new Object into XML string
	        String xmlString = xmlMapper
	          .writeValueAsString(new PhoneDetails("OnePlus", "6.4", "6/64 GB", manufacturer));

	        // write to the console
	        System.out.println(xmlString);
	        
	        // serialize our Object into XML string
//	        String xmlString = xmlMapper.writeValueAsString(new PhoneDetails("OnePlus", "6.4", "6/64 GB"));
//
//	        // write to the console
//	        System.out.println(xmlString);

	        // write XML string to file
	        File xmlOutput = new File("f:\\serialized.xml");
	        FileWriter fileWriter = new FileWriter(xmlOutput);
	        fileWriter.write(xmlString);
	        fileWriter.close();
	    } catch (JsonProcessingException e) {
	        // handle exception
	    } catch (IOException e) {
	        // handle exception
	    }
	}
//	public static void deserializeFromXML() {
//	    try {
//	        XmlMapper xmlMapper = new XmlMapper();
//
//	        // read file and put contents into the string
//	        String readContent = new String(Files.readAllBytes(Paths.get("to_deserialize.xml")));
//
//	        // deserialize from the XML into a Phone object
//	        PhoneDetails deserializedData = xmlMapper.readValue(readContent, PhoneDetails.class);
//
//	        // Print object details
//	        System.out.println("Deserialized data: ");
//	        System.out.println("\tName: " + deserializedData.getName());
//	        System.out.println("\tMemory: " + deserializedData.getMemory());
//	        System.out.println("\tDisplay Size: " + deserializedData.getDisplaySize());
//	    } catch (IOException e) {
//	        // handle the exception
//	    }
//	}
	public static void main(String[] args) {
	    System.out.println("Serializing to XML...");
	    serializeToXML();
	    
//	    System.out.println("Deserializing from XML...");
//	    deserializeFromXML();
	}
}

@JsonPropertyOrder({ "internal_memory", "display_size", "phone_name","manufacturer" })
 class PhoneDetails {
	 
	 @JsonProperty("phone_name")
	    private String name;

	    @JsonProperty("display_size")
	    private String displaySize;

	    @JsonProperty("internal_memory")
	    private String memory;
	    @JsonProperty("manufacturer")
	    private Manufacturer manufacturer;
	PhoneDetails(String n, String d,String m,Manufacturer ma){
		this.name=n;
		this.displaySize=d;
		this.memory=m;
		this.manufacturer=ma;
	}
	    public String getName() {
			return name;
		}
		public Manufacturer getManufacturer() {
			return manufacturer;
		}
		public void setManufacturer(Manufacturer manufacturer) {
			this.manufacturer = manufacturer;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDisplaySize() {
			return displaySize;
		}
		public void setDisplaySize(String displaySize) {
			this.displaySize = displaySize;
		}
		public String getMemory() {
			return memory;
		}
		public void setMemory(String memory) {
			this.memory = memory;
		}

}
//define the order of elements
@JsonPropertyOrder({ "manufacturer_name", "country", "other_phones" })
 class Manufacturer {
 @JsonProperty("manufacturer_name")
 private String name;

 @JsonProperty("country")
 private String country;

 // new annotation
 @JacksonXmlElementWrapper(localName="other_phones")
 private List<String> phone;

Manufacturer(String n,String c,List<String> p){
	this.name=n;
	this.country=c;
	this.phone=p;
			
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public List<String> getPhone() {
	return phone;
}

public void setPhone(List<String> phone) {
	this.phone = phone;
}



}
