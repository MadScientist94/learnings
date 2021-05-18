package _20210425;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
@JsonPropertyOrder({ "student" })
public class students {
	@JacksonXmlElementWrapper
List<student> student=new ArrayList<student>();

	public List<student> getStudent() {
		return student;
	}

	public void setStudent(List<student> student) {
		this.student = student;
	}
}
