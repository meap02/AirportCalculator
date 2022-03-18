//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Used to lay out the functions that the dictionary will need to perform
//

import java.util.Iterator;

public interface DictionaryInterface <K , V> {
	
	public V add(K key, V value);
	
	public V remove(K key);
	
	public V getValue(K key);
	
	public boolean contains(K key);
	
	public boolean isEmpty();
	
	public void clear();
	
	public int getSize();
	
	public Iterator<K> getKeyIterator();
	
	public Iterator<V> getValueIterator();
}
