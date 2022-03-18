//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Used in most of my traversals to represent the order that the vertices should come in
//

import java.lang.reflect.Array;

public class LinkedQueue<T> implements QueueInterface<T> {
	
	private Node head;
	private Node tail;
	private int numberOfEntries;
	
	public LinkedQueue() {
		head = null;
		tail = null;
		numberOfEntries = 0;
	}

	@Override
	public boolean enqueue(T data) {
		Node newNode = new Node(data, null);
		if(isEmpty()) 
			head = newNode;
		else {
			tail.setNext(newNode);
		}
		numberOfEntries++;
		tail = newNode;
		return true;
	}

	@Override
	public T dequeue() {
		T front = getFront();
		head.setData(null);
		head = head.getNext();
		numberOfEntries--;
		
		if(head == null) 
			tail = null;
		return front;
	}

	@Override
	public T getFront() {
		if(isEmpty())
			throw new RuntimeException("The queue is empty");
		else
			return head.getData();
	}

	@Override
	public boolean isEmpty() {
		return (head == null) && (tail == null);
	}
	
	@Override
	public int getSize() {
		return numberOfEntries;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
	}

	@Override
	public T[] toArray() {
		Node currentNode = head;
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) Array.newInstance(Object.class, numberOfEntries);
		for(int i = numberOfEntries-1; i >= 0; i--) {
			temp[i] = currentNode.getData();
			currentNode = currentNode.getNext();
		}
		return temp;
	}
	
	@Override
	public String toString() {
		String temp = "[";
		T[] array = toArray();
		for(int i = 0; i < array.length - 1; i++) {
			temp = temp.concat(String.valueOf(array[i]) + ",");
		}
		temp = temp.concat(String.valueOf(array[array.length-1]) + "]");
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
		
		public void setNext(Node next) {
			this.next = next;
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
}
