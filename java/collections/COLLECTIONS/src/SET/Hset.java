package SET;

import java.util.*;

public class Hset {

	public static void main(String[] args) {
		
HashSet hs= new HashSet();
		
		
		
		
//		add(E e)
hs.add(1);
hs.add(2);
hs.add(3);
hs.add(4);
hs.add(5);
hs.add(6);
hs.add(7);
System.out.println(hs);
//		addAll​(Collection<? extends E> c)
//		addAll(collection) 
HashSet hs1= new HashSet();
hs1.addAll(hs);
System.out.println(hs1);System.out.println(hs);
//		clear()
hs1.clear();
System.out.println(hs1);System.out.println(hs);
//		contains(Object o)
System.out.println(hs.contains(3));
hs1.add(3);
hs1.add(5);System.out.println(hs);
//		containsAll(collection) 
System.out.println(hs.containsAll(hs1));System.out.println(hs);
//		retainAll(collection)
System.out.println(hs.retainAll(hs1));
System.out.println(hs);
//		remove(Object o)
System.out.println(hs.remove(3));
System.out.println(hs);
//		removeAll(collection)
System.out.println(hs.removeAll(hs1));
System.out.println(hs);
//isEmpty()
System.out.println(hs.isEmpty());
hs.add(1);
hs.add(2);
hs.add(3);
hs.add(4);
hs.add(5);
hs.add(6);
hs.add(7);
//		iterator()
//
System.out.println(hs.iterator());
//		size()
System.out.println(hs.size());
//		clone()
hs1=(HashSet)hs.clone();
System.out.println(hs1);
//		equals()
System.out.println(hs.equals(hs1));
System.out.println(hs.equals(hs.clone()));
System.out.println(hs1.remove(7));
System.out.println(hs.equals(hs1));
//		hashcode()
Set s= (Set)hs;
System.out.println(s.hashCode());

//		toArray()
Object[] ob= hs.toArray();
//		toArray​(T[] a)
Integer[] in= new Integer[hs.size()];
hs.toArray(in);
System.out.println(in);

//		toString()
System.out.println(hs.toString());

//		parallelStream()
System.out.println(hs.parallelStream());

//		removeIf​(Predicate<? super E> filter)
//System.out.println(hs.removeIf(null));
//		stream()
System.out.println(hs.stream());
	}

}
