package LIST;

import java.util.*;

public class Llist {

	public static void main(String[] args) {
		List l = new LinkedList();
		
//		add(int index, E element)
//l.add(1,9);// index  exception
		l.add(0,9);
System.out.println(l);
//		add(E e)
l.add(8);
//		addAll(int index, Collection<E> c)
List l1= new LinkedList();
l1.addAll(l);
System.out.println(l1);
//		addAll(Collection<E> c)
l.addAll(l1);
System.out.println(l);
//		addFirst(E e)
LinkedList l2= new LinkedList();
l2.addAll(l1);
l2.addFirst(2);
//		addLast(E e)
l2.addLast(4);
System.out.println(l2);
//		clear()
l.clear();
//		clone()
LinkedList l3=(LinkedList)l2.clone();
System.out.println(l3);
//		contains(Object o)
l3.contains(8);
l3.containsAll(l1);

//		descendingIterator()
Iterator i=l3.descendingIterator();
while(i.hasNext()) {
	System.out.println(i.next());
}
//		element()
System.out.println(l3.element());
//		get(int index)
System.out.println(l3.get(2));
//		getFirst()
System.out.println(l3.getFirst());
//		getLast()
System.out.println(l3.getLast());
//		indexOf(Object o)
System.out.println(l3.indexOf(8));
//		lastIndexOf(Object o)
l1.lastIndexOf(8);
//		listIterator(int index)
//
//		offer(E e)
l3.offer(67);
//		offerFirst(E e)
l3.offerFirst(99);
//		offerLast(E e)
l3.offerLast(11);
System.out.println(l3);
//		peek()
System.out.println(l3.peek());
//		peekFirst()
System.out.println(l3.peekFirst());
//		peekLast()
System.out.println(l3.peekLast());
//		poll()
System.out.println(l3);
System.out.println(l3.poll());
//		pollFirst()
System.out.println(l3);
System.out.println(l3.pollFirst());
//		pollLast()
System.out.println(l3);
System.out.println(l3.pollLast());
System.out.println(l3);
//		pop()
System.out.println(l3.pop());
//		push(E e)
System.out.println(l3);
l3.push(001);
System.out.println(l3);
//		remove()
System.out.println(l3.remove());
//		remove(int index)
System.out.println(l3.remove(2));
//		remove(Object o)
System.out.println(l3.remove(new Integer(67)));
//		removeFirst()
l3.add(2);
l3.add(23);
l3.add(2);
l3.add(21);
l3.add(2);
l3.add(2);
System.out.println(l3);
//		removeFirstOccurrence(Object o)
System.out.println(l3.removeFirstOccurrence(2));
//		removeLast()
System.out.println(l3.removeLast());
System.out.println(l3);
//		removeLastOccurrence(Object o)
System.out.println(l3.removeLastOccurrence(2));
//		set(int index, E element)
System.out.println(l3.set(3, 5));
//		size()
System.out.println(l3.size());
//		spliterator()
System.out.println(l3.spliterator());
//		toArray()
System.out.println(l3.toArray());
Object[] ob= l3.toArray();
//		toArray(T[] a)
Integer [] inty= new Integer[l3.size()];
System.out.println(l3.toArray(inty));
System.out.println(inty[0]);
//		toString()
System.out.println(l3.toString());

	}

}
