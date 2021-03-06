import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class xmlTojson {
	 public static void main(String args[]) throws Exception {
	      try {
	         XmlMapper xmlMapper = new XmlMapper();
	         Person pojo = xmlMapper.readValue(getXmlString(), Person.class);
	         System.out.println(pojo);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }
	   private static String getXmlString() {
	      return "<person> <firstName>Adithya</firstName>"
	                    + "<lastName>Jai</lastName>"
	                    + "<address>Bangalore</address>" + "</person>";
	   }
	}
	// Person class (POJO)
	class Person {
	   private String firstName;
	   private String lastName;
	   private String address;
	   public String getFirstName() {
	      return firstName;
	   }
	   public void setFirstName(String firstName) {
	      this.firstName = firstName;
	   }
	   public String getLastName() {
	      return lastName;
	   }
	   public void setLastName(String lastName) {
	      this.lastName = lastName;
	   }
	   public String getAddress() {
	      return address;
	   }
	   public void setAddress(String address) {
	      this.address = address;
	   }
	   public String toString() {
	      return "Person[ " +
	             "firstName = " + firstName +
	             ", lastName = " + lastName +
	             ", address = " + address +
	             " ]";
	   }
	}