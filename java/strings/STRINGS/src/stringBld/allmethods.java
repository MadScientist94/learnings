package stringBld;

public class allmethods {

	public static void main(String[] args) {
		StringBuilder sb=new StringBuilder("Hello ");
//append()
		sb.append("Java");//now original string is changed  
		System.out.println(sb);//prints Hello Java  
		
//insert(start index, args)
		sb=new StringBuilder("Hello ");
		sb.insert(1,"Java");//now original string is changed  
		System.out.println(sb);//prints HJavaello  
		
//replace(start index, end index, args)
		sb=new StringBuilder("Hello");
		sb.replace(1,3,"Java");
		System.out.println(sb);//prints HJavalo  
		
// delete()
		sb=new StringBuilder("Hello");
		sb.delete(1,3);
		System.out.println(sb);//prints Hlo  
//reverse()
		sb=new StringBuilder("Hello");
		sb.reverse();
		System.out.println(sb);//prints olleH  
		
//capacity()
		sb=new StringBuilder();
		System.out.println(sb.capacity());//default 16  
		sb.append("Hello");
		System.out.println(sb.capacity());//now 16  
		sb.append("java is my favourite language");
		System.out.println(sb.capacity());//now (16*2)+2=34 i.e (oldcapacity*2)+2  
		sb.append("j");
		
		System.out.println(sb.capacity());
//
		StringBuilder s1 = new StringBuilder("java");
		String s2 = "a";
		String s3 = "a";
		StringBuilder s4 = s1;
		System.out.println(s2==s3);
		sb= new StringBuilder("java");
		sb.append("vaj$").delete(0, 3).deleteCharAt(sb.length()-1);
		System.out.println(sb);//prints olleH  
	}

}
