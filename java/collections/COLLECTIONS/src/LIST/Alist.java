package LIST;

import java.util.*;

public class Alist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//constructor without parameter
List l= new ArrayList();
l.add("ant");
//constructor with collection object
List l1= new ArrayList(l);
// constructor with initial memory size
List l2= new ArrayList(10);

System.out.println(l.size());
System.out.println(l2.size());
System.out.println(l1.size());
	
//	add(int index, Object element)
//l.add(88,"first"); ==> these line throug error because initial mem size of array list is 10
//l.add(2,"j");=> same error
// there fore index is used only to insert the data with in collection not out of it
//l2.add(5,"d");
l.add(0,"inserted");
System.out.println(l.size());
System.out.println(l);
//	add(Object o)
l.add(1);
l.add("map");
l.add(new Integer(6));
System.out.println(l);
//	addAll(Collection C)
l1.add(l);// add blindly add entire collection as single entry
List l3 = new ArrayList();
l3.addAll(l1);
l1.addAll(l);// add all merges the list
//while adding we reffeering the address of collection 
//System.out.println(l);

System.out.println(l1);
//	addAll(int index, Collection C)
l1.addAll(2, l);
l2.add("cat");
l2.addAll(l1);
System.out.println(l1);
System.out.println(l2);
System.out.println(l2.get(2));
//	clear()
l.clear();
l1.clear();
System.out.println(l1);
//	clone()

//	contains?(Object o)
System.out.println(l2.contains("maqp"));
System.out.println(l2.contains("map"));
//	ensureCapacity?(int minCapacity)

//	forEach?(Consumer<? super E> action)
//
//	get?(int index)

System.out.println(l2.get(2));
List ll=(List)l2.get(2);//getting empty array because we cleared the collection list l which address saved in these index 2
//so now the list l is empty hence the out put is empty
l.add("ant");
l.add("inserted");
//System.out.println(ll.get(2)); => array index oout of bound

//	indexOf(Object O)
//System.out.println(l2.indexOf((Object)l3));
System.out.println(l2.indexOf("ant"));
//	isEmpty?()
System.out.println(l1.isEmpty());
System.out.println(l2.isEmpty());
//	lastIndexOf(Object O)
System.out.println(l2.lastIndexOf("ant"));
//	listIterator?()
ListIterator li= l2.listIterator();
System.out.println(li.next());
System.out.println(li.next());
System.out.println(li.next());
//	listIterator?(int index)
 li= l2.listIterator(2);
 System.out.println(li.next());
System.out.println(li.next());
//	remove?(int index)

System.out.println(l2);
l2.remove(2);
System.out.println(l2);
//	remove?(Object o)
l2.remove("ant");
System.out.println(l2);
//	removeAll?(Collection c)
l2.removeAll(l);
System.out.println(l2);
//	removeIf?(Predicate filter)

//	removeRange?(int fromIndex, int toIndex)
//((ArrayList) l2).removeRange(2,5);
//	retainAll?(Collection<?> c)
l.add(1);
l2.retainAll(l);
System.out.println(l2);
//	set?(int index, E element)
l2.set(1,"inserted");
System.out.println(l2);
//	size?()
System.out.println(l1.size());
System.out.println(l2.size());
//	spliterator?()



//	subList?(int fromIndex, int toIndex)
List<String> l4= l2.subList(1, 2);
System.out.println(l4);
//	toArray()
l4.add("tea");
l4.add("mug");
Object [] arr=  l4.toArray();
System.out.println(arr[2]);
//	toArray(Object[] O)
String [] st= new String[l4.size()];
l4.toArray(st);
System.out.println(st[2]);
//	trimToSize()
l4.size();
//l4.trimToSize();

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
}