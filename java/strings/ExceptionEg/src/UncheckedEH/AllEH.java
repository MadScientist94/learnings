package UncheckedEH;

import java.sql.Date;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.Test;

class a{
	
}
class b extends a{
	
}
public class AllEH {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AllEH eh= new AllEH();
		//Array index out of bound
		eh.arr();
		//array index out of bound with out exception handling
//		eh.arr1();
		//String index out of bound
		eh.str();
		//Arithmetic exception
		eh.arith();
		//null point exception
		eh.nullpoint();
		// number format eception
		eh.numformat();
		//ArrayStoreException
		eh.ase();
		// class cast exception
		eh.cce();
		//IllegalStateException
		eh.ise();
		//IllegalArgumentException		
//	eh.iae();
		//• NegativeArraySizeException
		eh.nase();
		//SecurityException
		eh.se();
		//ConcurrentModificationExceptionExample 
		eh.cme();
		//nosuchelementexception
		eh.nse();
		//InputMismatchException
		eh.ime();
		//MissingResourseException
		eh.mre();
//eh.ecnp();
	}
	
	void arith()
	{
	      try{
	         int num1=30, num2=0;
	         int output=num1/num2;
	         System.out.println ("Result: "+output);
	      }
	      catch(ArithmeticException e){
	         System.out.println ("You Shouldn't divide a number by zero");
	      }
	      
	      
	   }
	void arr() {
		try{
	        int a[]=new int[10];
	        //Array has only 10 elements
	        a[11] = 9;
	      }
	      catch(ArrayIndexOutOfBoundsException e){
	         System.out.println ("ArrayIndexOutOfBounds");
	      }
	}
	void arr1() {
		
	        int a[]=new int[10];
	        //Array has only 10 elements
	        a[11] = 9;
	     
	         System.out.println ("ArrayIndexOutOfBounds");
	      
	}
	void numformat() {
		try{
			 int num=Integer.parseInt ("XYZ") ;
			 System.out.println(num);
		      }catch(NumberFormatException e){
			  System.out.println("Number format exception occurred");
		      }
	}

	void str() {
		try{
			 String str="beginnersbook";
			 System.out.println(str.length());;
			 char c = str.charAt(0);
			 c = str.charAt(40);
			 System.out.println(c);
		      }catch(StringIndexOutOfBoundsException e){
			  System.out.println("StringIndexOutOfBoundsException!!");
		       }
	}

	void nullpoint() {
		try{
			String str=null;
			//when we try to fetch null value of an object
			System.out.println (str.length());
		}
	        catch(NullPointerException e){
			System.out.println("NullPointerException..");
		}

	}

	void ase(){
		try {
			Object a[] = new Double[2];
			// This will throw ArrayStoreException
			a[0] = 4;
			}
			catch (ArrayStoreException e) {
			// When caught, print the ArrayStoreException
			System.out.println("ArrayStoreException found: "+ e);
			}

	}
	
	void cce() {
		try {
		a obj= new a();
		b obj1=(b)obj;
		}
		catch(ClassCastException e) {
			System.out.println("class  cast exception found: "+ e);
		}
	}
		
enum myenum{
	a,b,c;
}

void ecnp() {
	try {
	 System.out.println(myenum.valueOf("d")); 
	}
	catch (Exception e) {
		System.out.println("class  cast exception found: "+ e);

	}
	}
	
	void ise() {
		try {
		 //Instantiating an ArrayList object
		 ArrayList<String> list = new ArrayList<String>();
		//populating the ArrayList
		 list.add("apples");
		list.add("mangoes");
		//Getting the Iterator object of the ArrayList
		ListIterator<String> it = list.listIterator();
		//Removing the element without moving to first position
		 it.remove();
		}
		catch (IllegalStateException e) {
			System.out.println("IllegalStateException :"+e);
		}
	}
	void iae() {try {
		Scanner sc = new Scanner(System.in);
	      System.out.println("Enter your date of birth in JDBC escape format (yyyy-mm-dd) ");
	      String dateString = sc.next();
	      Date date = Date.valueOf(dateString);
	      System.out.println("Given date converted int to an object: "+date);
	}
	catch(IllegalArgumentException e) {
		System.out.println(e);
	}
	}
void nase() {
	int[]a;
	try {
		a=new int[-1];
	}
	catch(NegativeArraySizeException e) {
		System.out.println(e);
	}
}

void se() {
	try{
	Test.main1();
	}
	catch(SecurityException e) {
		System.out.println(e);
	}
	}

void cme() {
List<String> myList = new ArrayList<String>();
  myList.add("1");
  myList.add("2");
  myList.add("3");
  myList.add("4");
  myList.add("5");
  Iterator<String> it = myList.iterator();
  try {
  while (it.hasNext()) {
  String value = it.next();
  System.out.println("List Value:" + value);
  if (value.equals("3"))
    myList.remove(value); //this line gives
           // error
  }
}
  catch(ConcurrentModificationException e) {
	  System.out.println(e);
  }
}

void nse() {
	try {
	 Set exampleleSet = new HashSet();  
     Hashtable exampleTable = new Hashtable();  
//     exampleleSet.iterator().next();                         //This throws NoSuchElementException  
     exampleTable.elements().nextElement();      //as there are no elements in our Set  
	}catch(NoSuchElementException e) {
		System.out.println(e);
		//and HashTable but we are trying to access the    
	}
                                                                                      //elements.                               

}
void ime() {
	 Scanner scanner = new Scanner(System.in);
     try {
        System.out.println("Enter Integer Value: ");
        Integer inputInt = scanner.nextInt(); // input : "1.1"  
        System.out.println(inputInt);

} 
     catch (InputMismatchException ex) {
        System.out.println("We have given input as float expecting integer "+ ex);
     }

}
void mre() {
	try {
	ResourceBundle myResources = 
            ResourceBundle.getBundle("com.javacodegeeks.examples.resources");
String name = myResources.getString("name");
String surname = myResources.getString("surname");
String age = myResources.getString("age");
System.out.println(String.format("Hello! I'm %s %s, %s years old",name, surname, age));
	}catch(MissingResourceException e) {
		System.out.println(e);
	}
}























}
