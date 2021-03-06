package checkedEH;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.util.Date;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class LangEH extends Thread {
	 public LangEH() {
	        super();
	        System.out.println("An instance of the " + LangEH.class + " class was created!");
	    }
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CloneNotSupportedException {
		
		LangEH eh=new LangEH();
//		? ReflectiveOperationException
				eh.roe();
//		? ClassNotFoundException
		eh.cnfe();
//		? InstantiationException
		eh.ie();
//		? IllegalAccessException
		eh.iae();
//		? InvocationTargetException
		eh.ite();
//		? NoSuchFieldException
		eh.nsfe();
//		? NoSuchMethodException
		eh.nsme();
//		? CloneNotSupportedException
		eh.cnse();
//		? InterruptedException
		eh.i_e();
System.out.println("end");
	}

//	? ReflectiveOperationException
	void roe() {
		
	}
//	? ClassNotFoundException
	void cnfe() throws ClassNotFoundException {
		Class.forName("java.lang.String");
		
	}
//	? InstantiationException
	void ie() {
		try {
	         // date object
	         Date d = new Date(1993);
	         Class cls = d.getClass();
	         System.out.println("Time = " + d.toString());

	         /* creates a new instance of the class represented by this 
	            Class object cls */
	         Object obj = cls.newInstance();
	         System.out.println("Time = " + obj);
	      } catch(InstantiationException e) {
	         System.out.println(e.toString());
	      } catch(IllegalAccessException e) {
	         System.out.println(e.toString());
	      }
	}
//	? IllegalAccessException
	void iae() throws InstantiationException, IllegalAccessException {
		LangEH eh= LangEH.class.newInstance();
	}
//	? InvocationTargetException
	void ite() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LangEH eh= LangEH.class.newInstance();
		Method[] m = eh.getClass().getMethods();
		try {
		m[1].invoke(eh);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
//	? NoSuchFieldException
	public String str="jothi";//if these string didnt mentioned public then the get field cannot find the field 
	void nsfe() {
		LangEH eh= new LangEH();
		Class c1=eh.getClass();
		 try {  
	          
	         Field Flds = c1.getField("str");  
	         System.out.println(" field found: " + Flds.toString());  
	      } catch(NoSuchFieldException e) {  
	         System.out.println(e.toString());  
	      }  
	}
	
//	? NoSuchMethodException
	void nsme() throws NoSuchMethodException, SecurityException {
		LangEH eh= new LangEH();
		Class c1=eh.getClass();
		Method m1= c1.getMethod("cnse");
	}
//	? CloneNotSupportedException
	public void cnse() throws CloneNotSupportedException   {
		LangEH eh= new LangEH();
		try {
		LangEH eh1= (LangEH)eh.clone();
		}catch (CloneNotSupportedException e) {
//			e.printStackTrace();
			System.out.println(e);
		}
		System.out.println("77");
	}
//	@Override
//    protected Object clone()
//        throws CloneNotSupportedException
//    {
//        return super.clone();
//    }
//	? InterruptedException
	void i_e() {
		
	}
	  @Override
	    public void run() {
	        try {
	            /* Sleep for some seconds. */
	            TimeUnit.SECONDS.sleep(10);
	        }
	        catch(InterruptedException ex) {
	            System.err.println("An InterruptedException was caught: " + ex.getMessage());
	        }
	    }
	
	
	
}
