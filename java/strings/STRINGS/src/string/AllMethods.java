package string;

import java.nio.charset.Charset;
import java.util.Locale;

public class AllMethods {

	public static void main(String[] args) {
		AllMethods all = new AllMethods();
		all.stringCreation();
		all.first10();
		all.second10();
		all.third10();
		all.fourth10();
	}
	
	void stringCreation() {
		byte[] b_arr = {71, 101, 101, 107, 115};
		Charset cs = Charset.defaultCharset();// default encoding for windows platform[windows-1252]
		
		String s_byte =new String(b_arr); //Geeks
		
		String s_byte_char = new String(b_arr, cs); //Geeks
		
//		String s = new String(b_arr,"US-ASCII");
		
		String s1 = new String(b_arr, 1, 3); // eek
		
		String s2 = new String(b_arr, 1, 3, cs); // eek
//		
//		String s3 = new String(b_arr, 1, 4, "US-ASCII"); // eeks
		
		char char_arr[] = {'G', 'e', 'e', 'k', 's'};
		String s4 = new String(char_arr); //Geeks
		
		String s5 = new String(char_arr , 1, 3); //eek
		
		String s6 = new String(b_arr, 1, 3); //eek
		
		StringBuffer s_buffer = new StringBuffer("Geeks");
		String s7 = new String(s_buffer); //Geeks
		
		StringBuilder s_builder = new StringBuilder("Geeks");
		String s8 = new String(s_builder); //Geeks
				System.out.println(s_byte+"\n"+s_byte_char+"\n"+s1+"\n"+s2+"\n"+"\n"+s4+"\n"+s5+"\n"+s6+"\n"+s7+"\n"+s8+"\n");
		
	}

	void first10() {
	String s="MadScientist";
	String s1 ="this is a new day with pleasant morning";
	//int length()
	System.out.println("MadScientist".length());
	System.out.println(s.length());
	System.out.println();
	
	
	//char charAt(int i)
	System.out.println("MadScientist".charAt(3));
	System.out.println(s.charAt(2));
	System.out.println();
	
	//string substring(int i)
	System.out.println("MadScientist".substring(3));
	System.out.println(s.substring(2));
	System.out.println();
	
	//string substring(int i,int j)
	System.out.println("MadScientist".substring(3,6));
	System.out.println(s.substring(2,6));
	System.out.println();
	
	
	//String concat(String str)
	System.out.println("mad".concat("scientist"));
	System.out.println(s.concat(" 1"));
	System.out.println();
	
	//int indexOf (String s)
	System.out.println("this is a new day with pleasant morning".indexOf("day"));
	System.out.println(s1.indexOf("day"));
	System.out.println(s1.indexOf("ea"));
	System.out.println();
	
	//int indexOf (String s, int i)
	System.out.println("this is a new day with pleasant morning".indexOf("day",16));
	System.out.println(s1.indexOf("day",3));
	System.out.println(s1.indexOf("a",8));//8 15 25
	System.out.println(s1.indexOf("a",9));
	System.out.println(s1.indexOf("a",15));
	System.out.println(s1.indexOf("a",25));
	System.out.println(s1.indexOf("a",27));
	System.out.println();
	
	//Int lastIndexOf( String s):
	System.out.println("this is a new day with pleasant morning".lastIndexOf("a"));
	System.out.println(s1.lastIndexOf("a"));
	System.out.println();
	
	//boolean equals( Object otherObj)
	System.out.println("MadScientist".equals("MadScientist"));
	System.out.println(s.equals("MadScientist"));
	System.out.println(s.equals("madscientist \n\n"));
	System.out.println();
	
	//boolean  equalsIgnoreCase (String anotherString): 
	System.out.println(s.equalsIgnoreCase("MadScientist"));
	System.out.println(s.equalsIgnoreCase("madscientist"));
	System.out.println();
		
	}

	void second10() {
		
//		 int compareTo( String anotherString): 
		String str1 = "String method tutorial";
	       String str2 = "compareTo method example";
	       String str3 = "String method tutorial";

	       int var1 = str1.compareTo( str2 );
	       System.out.println("str1 & str2 comparison: "+var1);//-16

	       int var2 = str1.compareTo( str3 );
	       System.out.println("str1 & str3 comparison: "+var2);//0

	       int var3 = str2.compareTo("CompareTo method example");
	       // here first letter is changed the difference is capital and small letter
	       // C ascii value 67  c ascii value 99    99-67=32;
	       System.out.println("str2 & string argument comparison: "+var3);//32
		
//		int compareToIgnoreCase( String anotherString): 
	       int var4 = str2.compareToIgnoreCase("CompareTo method example");
	       System.out.println("str2 & string argument comparison: "+var4);//0
// String toUpperCase
	       String str4= str1.toUpperCase();
	       System.out.println(str1);
	       System.out.println(str4);
// String toLowerCase
	       System.out.println(str4.toLowerCase());
	       
//String trim():
	       String trimStr="    ant man got shrinked     ";
	       
	       System.out.println(trimStr.trim());
	       System.out.println(trimStr);
// String replace (char oldChar, char newChar)
	       System.out.println(str2.replace('m','w'));
//public int codePointAt(int index)
	       System.out.println(str2.codePointAt(4));
//public int codePointBefore(int index) 
	       System.out.println(str2.codePointBefore(4));
//public int codePointCount(int start_index, int end_index)
	       System.out.println(str2.codePointCount(2,6));
//public CharSequence subSequence(int start_index, int end_index)
	       System.out.println(str2.subSequence(2, 6));
	       
	       
	       
	       
	       
	       
	       
	}

	void third10() {
//public boolean contains(CharSequence char_seq)
		String s="maleableobject";
		System.out.println(s.contains("eab"));
//public boolean contentEquals(CharSequence char_seq) 
		System.out.println(s.contains("eab"));
		
		String s1="superman";
//public boolean endsWith(String suf)
		System.out.println(s1.endsWith("man"));
//public boolean startsWith(String pre) 
		System.out.println(s1.startsWith("sup"));
//public void getChars(int start, int end, char[] destination, int destination_start)
		char[] destination= new char[s1.length()];
		s1.getChars(2, 6, destination, 3);
		System.out.println(destination);
//public char[] toCharArray()
		char[] ch=s1.toCharArray();
		System.out.println(ch);
//public int hashCode()
		System.out.println("aab".hashCode());
		// formula to calculate s[0]*31^(n-1) + s[1]*31^(n-2) + ... 
//public String intern() 
		String s2="superman";
		System.out.println("cat".intern());
//public boolean isEmpty() 
		String sa="";
		System.out.println(sa.isEmpty());
		 sa="a";
		System.out.println(sa.isEmpty());
		
//public static String format(String f, Object? arguments) 
		String ss = "geeksforgeeks";
		  
        // format()
        String ss1 = String.format("%s : %d", ss, 10);
        System.out.println(ss1);
  
        String ss2 = String.format("%s = %f ",
                                  "Value of PI is", Math.PI);
        System.out.println(ss2);
        System.out.println(Locale.getDefault());
        
//public static String format(Locale l, String f, Object? arguments)
        
	}

	void fourth10() {
//public boolean matches(String reg_exp) 
		String capacitor="supercapacitor";
		System.out.println(capacitor.matches(".*or"));
		System.out.println(capacitor.matches("(.*)apa(.*)"));
//public boolean regionMatches(int start_OString, String another, int start_AString, int no_of_char) 
//public boolean regionMatches(boolean ignore_case, int start_OString, String another, int start_AString, int no_of_char) 
		
//public String[] split(String reg_exp) 
		String s1="ant cat bat sat vat";
		System.out.println(s1.split("\\s")[0]);
//public String[] split(String reg_exp, int limit)
		 String[] geek_arr1 = s1.split("\\s+", 2);
	       //creates array of length 2
		System.out.println(geek_arr1[0]);
		
	        geek_arr1 = s1.split("\\s+", 4);
		//creates array of length 4
		System.out.println(geek_arr1[3]);
		CharSequence [] a= {"sd","sdf","red"};
//public static String join(CharSequence de_limiter, CharSequence? elements) 
		System.out.println(String.join(",","sd","sdf","red" ));
//public static String join(CharSequence de_limiter, Iterable elements)
		System.out.println(String.join(",",a ));
//public String replaceAll(String reg_exp, String replacement) 
		System.out.println(s1.replaceAll("a", "aaa"));
//public String replaceFirst(String reg_exp, String replacement) 
		System.out.println(s1.replaceFirst("a", "aaa"));
		
		
//https://www.geeksforgeeks.org/java-lang-string-class-java-set-2/
	
	
	
	}
	
	
	
	
}
