//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// An interface to the bag ADT that I use
//
public interface BagInterface<T> {
	
	/**
	 * Will return true if there are no items in the bag,
	 * and false otherwise
	 * @return
	 */
	public boolean isEmpty();
		//Code Here
	/**
	 * Checks if the array being used is at full capacity
	 * @return
	 * 	Returns true if the array is at full capacity
	 * 	and false otherwise
	 */
	public boolean isFull();
	
	/**
	 * Main function to add items to the bag
	 * @param newEntry
	 * 	Entry to add to the bag
	 * @return
	 *	returns true if successful
	 * 
	 */
	public boolean add(T newEntry);
		//Code here
	
	/**
	 * Searches the bag for a specified entry and stops at the first
	 * iteration found
	 * @param someEntry
	 * 	Entry to find within the bag
	 * @return
	 * 	Returns true if successful
	 */
	public boolean contains(T someEntry);
	
	/**
	 * Will find the specified entry, remove it from the bag
	 * and return in as a value
	 * @param someEntry
	 * 	The specific entry you would like to remove
	 * @return 
	 * 	This returns the specific removed entry 
	 */
	public T remove(T someEntry);
	
	/**
	 * Clears the bag of all references
	 * @return
	 * 	returns true if successful
	 */
	public void clear();
	
	/**
	 * Removes the first element in the array
	 * @return
	 * 	Returns whether the removal was successful
	 * or not
	 */
	public boolean removeFirst();
	
	/**
	 * Removes the last element in the array
	 * @return
	 * 	Returns whether the removal was successful
	 * or not
	 */
	public boolean removeLast();
	
	/**
	 * 
	 * @return
	 * 	Returns an array of all of the items in the bag
	 */
	public String toString();
	
	/**
	 * Developer use to check uninitialized parts of the array
	 * @param index
	 * 
	 */
	public T peek(int index);
	
	/**
	 * Will get the current amou8nt of items in the bag
	 * 
	 * @return 
	 * 	Returns size of the array
	 */
	public int getCurrentSize();
	
}
