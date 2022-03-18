//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Used in the other half of my traversals as well as the
// getCheapestPath() method
//


import java.lang.reflect.Array;

public class LinkedStack<T> implements StackInterface<T> {
	
	private int numberOfEntries;
	private Node head;
	
	public LinkedStack() {
		this.head = null;
		this.numberOfEntries = 0;
	}
	
	@Override
	public boolean push(T someValue) {
		Node newNode = new Node(someValue, head);
		head = newNode;
		numberOfEntries++;
		return true;
	}

	@Override
	public T pop() {
		if (!isEmpty()) {
			T temp = head.getData();
			head = head.getNext();
			numberOfEntries--;
			return temp;
		}else
			return null;
	}

	@Override
	public T peek() {
		return head.getData();
	}

	@Override
	public boolean isEmpty() {
		if(head == null)
			return true;
		else
			return false;
	}

	@Override
	public void clear() {
		head = null;
		numberOfEntries = 0;
	}
	
	@Override
	public int getCurrentSize() {
		return numberOfEntries;
	}
	
	@Override
	public T[] toArray() {
		Node currentNode = head;
		@SuppressWarnings("unchecked")
		T[] temp = (T[] )Array.newInstance(Object.class, numberOfEntries);
		
		while(currentNode != null) {
			int i = 0;
			temp[i] = currentNode.getData();
			i++;
			currentNode = currentNode.getNext();
		}
		return temp;
	}
	
	@Override
	public String toString() {
		Node currentNode = head;
		String temp = "[";
		while(currentNode.next != null) {
			temp = temp.concat(String.valueOf(currentNode.getData())+ ",");
			currentNode = currentNode.getNext();
		}
		temp = temp.concat(String.valueOf(currentNode.getData()) + "]");
		return temp;	
	}
	
	
	
	private class Node {
		Node next;
		T data;

		public Node(T data, Node next) {
			this.data = data;
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
		
		@SuppressWarnings("unused")
		public void setData(T data) {
			this.data = data;
		}
	}//END OF NODE
}//END OF CLASS
