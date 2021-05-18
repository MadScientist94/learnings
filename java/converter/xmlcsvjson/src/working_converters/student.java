package working_converters;


public class student{
	String name, age, role, type;
	address address;
	mark mark;
	student(String n, String a, String r, String t,address a1,mark m ){
		this.name=n;
		this.age=a;
		this.role=r;
		this.type=t;
		this.address=a1;
		this.mark=m;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public address getAddress() {
		return address;
	}
	public void setAddress(address address) {
		this.address = address;
	}
	public mark getMark() {
		return mark;
	}
	public void setMark(mark mark) {
		this.mark = mark;
	}
}

class address{
	String doorNo;
	String street;
	String taluk;
	String pin;
	address(String d, String s, String t, String p){
		this.doorNo=d;
		this.street=s;
		this.taluk=t;
		this.pin=p;
	}
	public String getDoorNo() {
		return doorNo;
	}
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTaluk() {
		return taluk;
	}
	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
}


class mark{
	private String s1;
	private String s2;
	private String s3;
	private String s4;
	private String s5;
mark(String s1,String s2, String s3, String s4,String s5){
	this.s1=s1;
	this.s2=s2;
	this.s3=s3;
	this.s4=s4;
	this.s5=s5;
}
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	public String getS5() {
		return s5;
	}
	public void setS5(String s5) {
		this.s5 = s5;
	}
	
}

