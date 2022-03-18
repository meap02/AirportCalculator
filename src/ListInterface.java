//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Lays out the functions of a list
//

public interface ListInterface<T> {
	
	public boolean add(T data);
	
	public void add(int index, T data);
	
	public T remove(int index);
	
	public boolean remove(T data);
	
	public T set(int index, T newData);
	
	public T get(int index);
	
	public boolean contains(T anEntry);
	
	public int size();
	
	public boolean isEmpty();
	
	public void clear();
}
