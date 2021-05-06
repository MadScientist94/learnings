 
 # Design Pattern
 
 ##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a software design pattern is a general, reusable solution to a commonly occurring problem within a given context in software design. <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;It is not a finished design that can be transformed directly into source or machine code. Rather, it is a description or template for how to solve a problem that can be used in many different situations. Design patterns are formalized best practices that the programmer can use to solve common problems when designing an application or system.

<br>

 #### Design patterns had originally been categorized into 3 sub-classifications based on kind of problem they solve.<br/> 
##### 1. **CREATIONAL :** These patterns provide the capability to create objects based on a required criterion and in a controlled way. 
##### 2. **STRUCTURAL :** These patterns are about organizing different classes and objects to form larger structures and provide new functionality. 
##### 3. **BEHAVIORAL :** These patterns are about identifying common communication patterns between objects and realize these patterns.

<br> 

### **Singleton**
#####				Define a class that has only one instance and provide a global point of access to it <br><br>A class must ensure that only single instance should be created and single object can be used by all the other classes<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;eg:  practical use Singleton patterns are used in logging, caches, thread pools, configuration settings, device driver objects.

#### **How to acheive singleton**
 1. make the constructor as private 
 2. create a private static variable of type singleton
 3. create a synchronized static method as public with return type as Singleton Class

#### **There are two forms of instantiation**
		1. early
		2. Lazy 
### disadvantage:
#### The global state of stateful singletons is preserved between test cases. That has several serious implications:

##### 1. Order of the tests now matters<br>2. Tests can have unwanted side effects caused by singleton<br>3. You cannot run multiple tests in parallel<br>4. Multiple invocations of the same test case can result in different results

### **Factory design pattern :**
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Define an interface for creating a single object, but let subclasses decide which class to instantiate.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. Factory Method lets a class defer instantiation to subclasses.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. fdp is specified by achitectural implementaions by users<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;eg: calender, resourse bundle,number format 

Disadvantage:<br>
#####  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. One disadvantage of the Factory Method pattern is that it can expand the total number of classes in a system. Every concrete Product class also requires a concrete Creator class.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. Makes code more difficult to read as all of your code is behind an abstraction that may in turn hide abstractions.




### **proto type design pattern**
##### prototype design pattern describes how to solve recurring design to design flexible and rerusable code

##### It is used when the type of objects to create is determined by a prototypical instance, which is cloned to produce new objects. This pattern is used to:

##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. avoid subclasses of an object creator in the client application, like the factory method pattern does.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. avoid the inherent cost of creating a new object in the standard way (e.g., using the 'new' keyword) when it is prohibitively expensive for a given application.

https://www.geeksforgeeks.org/prototype-design-pattern/

### Disadvantages of Prototype Design Pattern

##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Overkill for a project that uses very few objects and/or does not have an underlying emphasis on the extension of prototype chains.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. It also hides concrete product classes from the client<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. Each subclass of Prototype must implement the clone() operation which may be difficult, when the classes under consideration already exist. Also implementing clone() can be difficult when their internals include objects that don’t support copying or have circular references.


### **builder pattern:**
##### The intent of the Builder design pattern is to separate the construction of a complex object from its representation. By doing so, the same construction process can create different representations.


#### **The Builder design pattern solves problems like:**

##### 1. How can a class (the same construction process) create different representations of a complex object?<br>2. How can a class that includes creating a complex object be simplified?

**The Builder design pattern describes how to solve such problems:**

##### 1. Encapsulate creating and assembling the parts of a complex object in a separate Builder object.<br>2. A class delegates object creation to a Builder object instead of creating the objects directly.<br>3. builder patern handles large number of parameters and builder paattern is immutable

eg: sttring builder,document builder.

### Advantages
#### Advantages of the Builder pattern include:

##### 1. Allows you to vary a product's internal representation.<br>2. Encapsulates code for construction and representation.<br>3.Provides control over steps of construction process.
### Disadvantages
#### Disadvantages of the Builder pattern include:

##### 1. A distinct ConcreteBuilder must be created for each type of product.<br>2. Builder classes must be mutable.<br>3. May hamper/complicate dependency injection.

```java
// builder.java
// lunchorder.java
package designpattern.creational.builder;

public class LunchOrder {

	public static class Builder {
		private String bread;
		private String condiments;
		private String dressing;
		private String meat;

		public Builder() {

		}

		public LunchOrder build() {
			return new LunchOrder(this);
		}

		public Builder bread(String bread) {
			this.bread = bread;
			return this;
		}

		public Builder condiments(String condiments) {
			this.condiments = condiments;
			return this;
		}

		public Builder dressing(String dressing) {
			this.dressing = dressing;
			return this;
		}

		public Builder meat(String meat) {
			this.meat = meat;
			return this;
		}

	}

	private String bread;
	private String condiments;
	private String dressing;
	private String meat;

	public String getBread() {
		return bread;
	}

	public String getCondiments() {
		return condiments;
	}

	public String getDressing() {
		return dressing;
	}

	public String getMeat() {
		return meat;
	}

	public LunchOrder(Builder builder) {
		this.bread = builder.bread;
		this.condiments = builder.condiments;
		this.dressing = builder.dressing;
		this.meat = builder.meat;
	}

}

// builderlunchorder.java
package designpattern.creational.builder;

public class BuilderLunchOrder {

	public static void main(String[] args) {
		LunchOrder.Builder builder = new LunchOrder.Builder();
		builder.bread("wheat").condiments("lettuce").meat("chicken");
		LunchOrder lunchOrder = builder.build();
		System.out.println(lunchOrder.getBread());
		System.out.println(lunchOrder.getCondiments());
		System.out.println(lunchOrder.getDressing());
		System.out.println(lunchOrder.getMeat());
	}

}
```









### **Creational patterns**

|Name      |Description |
|----------|-----------|
|Singleton	|Ensure a class has only one instance, and provide a global point of access to it.|
|Prototype	|Specify the kinds of objects to create using a prototypical instance, and create new objects from the 'skeleton' of an existing object, thus boosting performance and keeping memory footprints to a minimum.|
|Factory method	|Define an interface for creating a single object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.|
|Abstract factory|	Provide an interface for creating families of related or dependent objects without specifying their concrete classes.|
|Builder	|Separate the construction of a complex object from its representation, allowing the same construction process to create various representations.|
|Dependency Injection	|A class accepts the objects it requires from an injector instead of creating the objects directly.|
|Lazy initialization	|Tactic of delaying the creation of an object, the calculation of a value, or some other expensive process until the first time it is needed. This pattern appears in the GoF catalog as "virtual proxy", an implementation strategy for the Proxy pattern.|
|Multiton	|Ensure a class has only named instances, and provide a global point of access to them.|
|Object pool	|Avoid expensive acquisition and release of resources by recycling objects that are no longer in use. Can be considered a generalisation of connection pool and thread pool patterns.|
|Resource acquisition is initialization (RAII)|	Ensure that resources are properly released by tying them to the lifespan of suitable objects.|
