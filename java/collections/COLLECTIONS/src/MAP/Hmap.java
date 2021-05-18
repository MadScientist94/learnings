package MAP;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Hmap {

	public static void main(String[] args) {
		HashMap hm= new HashMap();
		//put(K key, V value)
		//

		hm.put("can", 1);
hm.put("van",2);
hm.put("ban",3);
//putAll(Map<? extends K,? extends V> m)
//
HashMap hm1= new HashMap();
hm1.putAll(hm);;
System.out.println(hm);
hm.put("tan", 4);
System.out.println(hm1.putIfAbsent("can1", 2));
//clear()
hm1.clear();
//clone()
hm1.putAll((HashMap)hm.clone());
System.out.println(hm1);

//size()
System.out.println(hm1.size());
//values()
System.out.println(hm1.values());
//containsKey(Object key)
System.out.println(hm1.containsKey("can"));
//containsValue(Object value)
System.out.println(hm1.containsValue(3));
//entrySet()
Set s=hm.entrySet();
Iterator i= s.iterator();
System.out.println(i.next());
System.out.println(i.next());
System.out.println(i.next());

//get(Object key)
System.out.println(hm1.get("can"));
//isEmpty()
System.out.println(hm1.isEmpty());
//keySet()
System.out.println(hm1.keySet());
//merge(K key, V value, BiFunction<? super V, ? super V,? extends V> remappingFunction)
//
//remove(Object key)
System.out.println(hm1.remove("tan"));
//compute(K key, BiFunction<? super K, ? super V,? extends V> remappingFunction)
//
//computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
//
//computeIfPresent(K key, BiFunction<? super K, ? super V,? extends V> remappingFunction)
//







	}

}
