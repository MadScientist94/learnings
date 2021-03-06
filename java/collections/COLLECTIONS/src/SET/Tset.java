package SET;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Tset {

	public static void main(String[] args) {

		TreeSet<Integer> ts= new TreeSet();
		
//		add(Object o)
ts.add(5);
ts.add(48);
ts.add(42);
ts.add(22);
//		addAll(Collection c)
TreeSet s=new TreeSet();
s.addAll(ts);
//		ceiling?(E e)
System.out.println(s.ceiling(40));//ie value greater than arg from the 
//selected value least one will be output

//		clear()
s.clear();
//		clone()
s.addAll((TreeSet)ts.clone());
System.out.println(s);
//		Comparator comparator()
//
//		contains(Object o)
s.contains(42);
//		descendingIterator?()
Iterator i=s.descendingIterator();
//		descendingSet?()
s.descendingSet();

System.out.println(s);
System.out.println(s.descendingSet());
//		first()
System.out.println(s.first());

System.out.println(s);
//		floor?(E e)
System.out.println(s.floor(23));
//		headSet(Object toElement)
System.out.println(s.headSet(23));//strictly less than toelement ie  <23
//		higher?(E e)
System.out.println(s.higher(22));
//		isEmpty()
System.out.println(s.isEmpty());
//		Iterator iterator()
System.out.println(s.iterator());
//		last()
System.out.println(s.last());
//		lower?(E e)
System.out.println(s.lower(33));

//		pollFirst?()
System.out.println(s.pollFirst());
//		pollLast?()
System.out.println(s.pollLast());
//		remove(Object o)
System.out.println(s.remove(22));
//		size()
System.out.println(s.size());
//		spliterator?()
//
s.addAll(ts);
System.out.println(s);
s.subSet(5, 48);
//		subSet(Object fromElement, Object toElement)
System.out.println(s.subSet(5,true,22,true));
//		tailSet(Object fromElement)
System.out.println(s.tailSet(22));
	
	
	}

}
