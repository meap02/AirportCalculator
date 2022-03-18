//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// This is used to keep the edgeList inside of each vertex.
// Is liked so that an vertex can have an infinite number of edges
// without having to resize like an array queue
//

import java.lang.reflect.Array;
import java.util.Iterator;


public class LList<T> implements ListInterface<T> {
	
	private Node head;
	private int numOfEntries;

	public LList() {
		initializeDataFields();
	}

	@Override
	public boolean add(T data) {
		try {
			Node newNode = new Node(data, head);
			head = newNode;
			numOfEntries++;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void add(int index, T data) {
		if(index <= numOfEntries) {
			Node prevNode = getNodeAt(index);
			Node nextNode = getNodeAt(index -1);
			Node newNode = new Node(data,nextNode);
			prevNode.setNext(newNode);
			numOfEntries++;
		}else
			add(data);
		
	}

	@Override
	public T remove(int index) {
		return null;
	}

	@Override
	public boolean remove(T data) {
		Node currentNode = head; //Start at the first node
		boolean found = false; 
		while(!found && currentNode != null) { //If the node isnt found or we get to the end of the list
			if(currentNode.getData().equals(data)) { 
				found = true; //found data!
				/*
				 * Preserve the head data, overwrite the
				 * data to be removed
				 */
				currentNode.setData(head.getData());
				head = head.getNext();
				numOfEntries--;
			}else
				currentNode = currentNode.getNext();
		}
		
		if(!found)
			throw new RuntimeException("The entry " + data + " does not exist");
		else
			return found;
	}

	@Override
	public T set(int index, T newData) {
		Node temp = getNodeAt(index);
		if(temp != null) {
			temp.setData(newData);
			return newData;
		} else
			return null;
	}

	@Override
	public T get(int index) {
		return getNodeAt(index).getData();
	}

	@Override
	public boolean contains(T anEntry) {
		Node currentNode = head;
		boolean found = false;
		while(!found && currentNode != null) {
			if(currentNode.getData().equals(anEntry))
				found = true;
			else
				currentNode = currentNode.getNext();
		}
		return found;
	}

	@Override
	public int size() {
		return numOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return numOfEntries == 0;
	}
	
	@Override
	public void clear() {
		initializeDataFields();
	}
	
	public Iterator<T> getLinkedListIterator() {
		return new LinkedListIterator();
	}
	
	private Node getNodeAt(int index) {
		if (index <= numOfEntries) {
			Node currentNode = head;
			for (int i = numOfEntries; i > index; i--) {
				currentNode = currentNode.getNext();
			}
			return currentNode;
		}else
			throw new RuntimeException("Index requested does not exist");
	}
	
	private void initializeDataFields() {
		head = null;
		numOfEntries = 0;
	}
	
	@Override
	public String toString() {
		String temp = "";
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(Object.class, numOfEntries);
		Node currentNode = head;
		for(int i = numOfEntries - 1; i >= 0; i--) {
			array[i] = currentNode.getData();
			currentNode = currentNode.getNext();
		}
		for(T element: array) {
			temp = temp.concat(element.toString() + " ");
		}
		return temp;
	}
	
	private class LinkedListIterator implements Iterator<T> {
		
		Node currentNode;
		StackInterface<Node> stack;
		
		private LinkedListIterator() {
			currentNode = head;
			stack = new LinkedStack<>();
			while(currentNode != null) {
				stack.push(currentNode);
				currentNode = currentNode.getNext();
			}
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			return stack.pop().getData();
		}
		
	}

	private class Node {
		Node next;
		T data;

		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}

		@SuppressWarnings("unused")
		public Node(T data) {
			this(data, null);
		}
		
		public Node getNext() {
			return next;
		}

		public T getData() {
			return data;
		}
		
		public void setData(T data) {
			this.data = data;
		}
	}//END OF NODE
}//END OF LList CLASS
