//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Lays out what a heap should be able to do
//


public interface HeapInterface<T extends Comparable<? super T>> {
	
	public void add(T newEntry);
	
	public boolean isEmpty();
	
	public int getSize();
	
	public void clear();
}
