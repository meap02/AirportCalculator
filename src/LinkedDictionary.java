//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Used to identify which airport codes identify to which vertex, along with another
// one to determine the full name of the airport
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedDictionary<K, V> implements DictionaryInterface<K, V> {
	
	Entry head;
	int numOfEntries;
	
	public LinkedDictionary() {
		head = new Entry();
		numOfEntries = 0;
	}

	@Override
	public V add(K key, V value) {
		
		if (key == null || value == null) //Catches nulls passed into the argument
			throw new IllegalArgumentException("Cannot add null to a dictionary.");
		else if(contains(key)) //Catches duplicates passed
			throw new IllegalArgumentException("Cannot add duplicate keys to a dictionary.");
		else {
			Entry newEntry = new Entry(key, value, head);
			head = newEntry;
			numOfEntries++;
		}
		return value;
	}

	@Override
	public V remove(K key) {
		V temp = null;
		if(key != null && contains(key)) {
			Entry removal = getReferenceTo(key);
			temp = removal.getValue();
			removal.setKey(head.getKey());
			removal.setValue(head.getValue());
			head = head.getNext();
			numOfEntries--;
		}
		return temp;
	}

	@Override
	public V getValue(K key) {
		return getReferenceTo(key).getValue();
	}

	@Override
	public boolean contains(K key) {
		boolean result = true;
		try {
			
			getReferenceTo(key);
			
		}catch(RuntimeException e) {
			
			result = false;
			
		}
		return result;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfEntries == 0;
	}
	
	@Override
	public void clear() {
		head = new Entry();
		numOfEntries = 0;
	}
	
	@Override
	public int getSize() {
		return numOfEntries;
	}
	
	
	private Entry getReferenceTo(K key) {
		Entry currentEntry = head;
		boolean found = false;
		while(!found && currentEntry.getKey() != null && currentEntry.getValue() != null) {
			if(currentEntry.getKey().equals(key))
				found = true;
			else
				currentEntry = currentEntry.getNext();
		}
		if(!found) 
			throw new RuntimeException("The reference " + key + " does not exist");
		else
			return currentEntry;
	}
	
	

	@Override
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	@Override
	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}
	
	
	private class KeyIterator implements Iterator<K> {
		Entry currentEntry;
		private KeyIterator() {
			currentEntry = head;
		}
		@Override
		public boolean hasNext() {
			return currentEntry.getKey() != null;
		}

		@Override
		public K next() {
			
			if (currentEntry != null) {
				K nextKey = currentEntry.getKey();
				currentEntry = currentEntry.getNext();
				return nextKey;
			}else
				throw new NoSuchElementException();
		}
		
	}
	
	private class ValueIterator implements Iterator<V> {
		Entry currentEntry;
		private ValueIterator() {
			currentEntry = head;
		}
		@Override
		public boolean hasNext() {
			return currentEntry.getValue() != null;
		}

		@Override
		public V next() {
			
			if (currentEntry != null) {
				V nextValue = currentEntry.getValue();
				currentEntry = currentEntry.getNext();
				return nextValue;
			}else
				throw new NoSuchElementException();
		}
		
	}
	@SuppressWarnings("unused")
	private class Entry{
		private K key;
		private V value;
		private Entry next;
		
		private Entry(K searchKey, V dataValue, Entry nextEntry) {
			next = nextEntry;
			key = searchKey;
			value = dataValue;
		}
		
		private Entry(K searchKey, V dataValue) {
			key = searchKey;
			value = dataValue;
		}

		public Entry() {
		}

		private V getValue() {
			return value;
		}

		@SuppressWarnings("unused")
		private void setValue(V value) {
			this.value = value;
		}

		private K getKey() {
			return key;
		}

		private Entry getNext() {
			return next;
		}

		private void setNext(Entry next) {
			this.next = next;
		}

		private void setKey(K key) {
			this.key = key;
		}
	}
}//END OF LINKED DICTIONARY
