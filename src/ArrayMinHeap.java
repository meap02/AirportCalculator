//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// This is used to keep track of the lowest path while in the
// getCheapestPath() method
//

public class ArrayMinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> {
	
	private T[] heap; //keep 0 empty on this one
	private int lastIndex;
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
	
	public ArrayMinHeap(int capacity) {
		if(capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a heap whose " +
					"capacity exceeds allowed maximum.");
		
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[]) new Comparable[capacity + 1];
		heap = tempHeap;
		lastIndex = 0;
		integrityOK = true;
	}
	
	public ArrayMinHeap(T[] entries) {
		this(entries.length);
		lastIndex = entries.length;
		for(int i = 0; i < entries.length; i++) {
			heap[i + 1] = entries[i];
		}
		
		for(int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) {
			reheap(rootIndex);
		}
	}
	
	public ArrayMinHeap() {
		this(DEFAULT_CAPACITY);
	}
	
	@Override
	public T removeMin() {
		checkIntegrity();
		T root = null;
		if(!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			heap[lastIndex] = null;
			lastIndex--;
			sort(1);
		}
		return root;
	}

	@Override
	public T getMin() {
		checkIntegrity();
		T root = null;
		if(!isEmpty())
			root = heap[1];
		return root;
	}

	

	@Override
	public void add(T newEntry) {
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex/2;
		while(!isEmpty() && parentIndex > 0 && newEntry.compareTo(heap[parentIndex]) < 0) {
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex/2;
		}
		heap[newIndex] = newEntry;
		lastIndex++;
		reheap(1);
	}
	
	private void sort(int index) {
		int currentIndex = index;
		if(currentIndex*2 <= lastIndex) {
			if(heap[currentIndex].compareTo(heap[currentIndex*2]) > 0) {
				T temp = heap[currentIndex];
				heap[currentIndex] = heap[currentIndex*2];
				heap[currentIndex*2] = temp;
				sort(currentIndex*2);
			}
		}
		
		if ((currentIndex*2) + 1 <= lastIndex) {
			if (heap[currentIndex].compareTo(heap[(currentIndex * 2) + 1]) > 0) {
				T temp = heap[currentIndex];
				heap[currentIndex] = heap[(currentIndex * 2) + 1];
				heap[(currentIndex * 2) + 1] = temp;
				sort((currentIndex * 2) + 1);
			} 
		}
	}

	@Override
	public boolean isEmpty() {
		return lastIndex < 1;
	}

	@Override
	public int getSize() {
		return lastIndex;
	}

	@Override
	public void clear() {
		checkIntegrity();
		while(lastIndex > -1) {
			heap[lastIndex] = null;
			lastIndex--;
		}
		lastIndex = 0;
	}
	
	private void reheap(int rootIndex) {
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = rootIndex * 2;
		while(!done && (leftChildIndex <= lastIndex)) {
			int smallerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			if(rightChildIndex <= lastIndex &&
							heap[rightChildIndex].compareTo((heap[smallerChildIndex])) < 0) {
				smallerChildIndex = rightChildIndex;
			}
			if(orphan.compareTo(heap[smallerChildIndex]) > 0) {
				heap[rootIndex] = heap[smallerChildIndex];
				rootIndex = smallerChildIndex;
				leftChildIndex = 2 * rootIndex;
			}else
				done = true;
		}
		heap[rootIndex] = orphan;
	}

	private void checkIntegrity() {
		if(!integrityOK)
			throw new SecurityException("ArrayMinHeap object is corrupt");
	}
}
