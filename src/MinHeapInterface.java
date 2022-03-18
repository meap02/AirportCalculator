//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Lays out the functions for a minHeap to have
//

public interface MinHeapInterface<T extends Comparable<? super T>> extends HeapInterface<T> {
	
	public T removeMin();
	
	public T getMin();
}
