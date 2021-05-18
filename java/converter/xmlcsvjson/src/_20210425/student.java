package _20210425;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "age", "role","type","address","mark" })
class student{
	@JsonProperty("name")
	String name;
	@JsonProperty("age")
	String age;
	@JsonProperty("role")
	String role;
	@JsonProperty("type")
	String type;
	@JsonProperty("address")
	address address;
	@JsonProperty("mark")
	mark mark;
}
@JsonPropertyOrder({ "doorNo", "street", "taluk","pin" })
class address{
	@JsonProperty("doorNo")
	String doorNo;
	@JsonProperty("street")
	String street;
	@JsonProperty("taluk")
	String taluk;
	@JsonProperty("pin")
	String pin;
}
@JsonPropertyOrder({ "s1", "s2", "s3","s4","s5" })
class mark{
	@JsonProperty("s1")
	String s1;
	@JsonProperty("s2")
	String s2;
	@JsonProperty("s3")
	String s3;
	@JsonProperty("s4")
	String s4;
	@JsonProperty("s5")
	String s5;
	public String getS5() {
		return s5;
	}
	public void setS5(String s5) {
		this.s5 = s5;
	}

}