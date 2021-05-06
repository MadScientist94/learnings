The java.lang.Class.getDeclaredConstructor() method returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object.


~~~ java

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class xyz{
	private xyz(){
		System.out.println("constructor called");
	}
}

Public class Abc{
public static void main(String []args){
Constructor<Xyz> constructor = Xyz.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        //these line makes the constructor accesible
        Xyz xyz = constructor.newInstance();
        // triggers the constructor
        
}    
}
~~~

if these line not avail then the code will provide below mentioned exception:
        constructor.setAccessible(true);

~~~java
Exception in thread "main" java.lang.IllegalAccessException: Class working_converters.CsvToXml can not access a member of class working_converters.xyz with modifiers "private"
	at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:102)
	at java.lang.reflect.AccessibleObject.slowCheckMemberAccess(AccessibleObject.java:296)
	at java.lang.reflect.AccessibleObject.checkAccess(AccessibleObject.java:288)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:413)
	at working_converters.CsvToXml.main(CsvToXml.java:23)
~~~