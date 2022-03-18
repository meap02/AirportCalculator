//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Lays out the functions for a stack to implement

public interface StackInterface<T> {
	
	/**
	 * Will push a value to the top of the stack
	 * @return
	 * 	Wether the operation was successful
	 * @param
	 * 	The value to be added to the stack
	 */
	public boolean push(T someValue);
	
	/**
	 * Will remove the last item put in the stack and return it
	 * @return
	 * 	Last item in the stack
	 */
	public T pop();
	
	/**
	 * Will return the last item put un the stack without removing it
	 * @return
	 * 	Last item in the stack
	 */
	public T peek();
	
	/**
	 * Will tell the user if the stack is empty or not
	 * 
	 */
	public boolean isEmpty();
	
	/**
	 * Will clear all values from the stack
	 */
	public void clear();
	
	public int getCurrentSize();
	
	public T[] toArray();
	
	
	public String toString();
	
}
