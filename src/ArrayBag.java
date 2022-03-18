
//
// Name: Just, Kyle
// Homework: 1
// Due: 2/18/2021
// Course: cs-2400-03-sp21
//
// Description:
//	An abstract data type used to collect data in a bag,
//	capable of adding, removing, and viewing items in the bag.
//
import java.lang.reflect.Array;

public class ArrayBag<T> implements BagInterface<T> {
	
	
//////////////////////////////////////////////////////////////////////////////
//							Fields										   //
////////////////////////////////////////////////////////////////////////////
	private final int DEFAULT_CAPAITY = 25;
	private T[] bag;
	private boolean integrityOK;
	private final int MAX_CAPACITY = 1000;
	/*
	 * I use the currentIndex variable as the indicator of 
	 * the next open slot I could put something in, so when 
	 * I have currentIndex - 1  it will give me the last 
	 * filled space in the array
	 */
	private int currentIndex;
	
	
//////////////////////////////////////////////////////////////////////////////
//							Constructors								   //
////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public ArrayBag(int capacity) {
		integrityOK = false;
		if (capacity <= MAX_CAPACITY) {
			//get around the problem of no creation of generic arrays
			this.bag = (T[]) Array.newInstance(Object.class, capacity);
			currentIndex = 0;
			integrityOK = true;
		}else
			throw new IllegalStateException("Attempt to create a bag whose " +
											"capacity exceeds allowed maximum.");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayBag() {
		this.bag = (T[]) Array.newInstance(Object.class, DEFAULT_CAPAITY);
		currentIndex = 0;
		integrityOK = true;

	}
	
//////////////////////////////////////////////////////////////////////////////
//							Methods										   //
////////////////////////////////////////////////////////////////////////////

	
	@Override
	public boolean isEmpty() {
		checkIntegrity();
		if(currentIndex == 0) 
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isFull() {
		checkIntegrity();
		if(currentIndex >= bag.length-1)
			return true;
		else
			return false;
	}

	@Override
	public boolean add(T newEntry) {
		checkIntegrity();
		if(!isFull()) {
			bag[currentIndex] = newEntry;
			currentIndex++;
			return true;
		}else
			expand(20);
			return add(newEntry);
	}

	@Override
	public boolean contains(T someEntry) {
		checkIntegrity();
		int pointer = 0;
		/*
		 * this will iterate through the array until the specified entry is found
		 * or it reaches the end of the array and returns false
		 */
		while(pointer < currentIndex) {
			if(bag[pointer].equals(someEntry)) {
				return true;
			}else
				pointer++;
		}
		return false;
	}

	@Override
	public T remove(T someEntry) {
		checkIntegrity();
		int pointer = 0;
		/*
		 * if the entry is found then the remove function 
		 * will search for it and return it. If contains()
		 * is false, then it skips the remove search entirely
		 * and just returns null.
		 */
		if(contains(someEntry)) {
			while(pointer < bag.length) {
				if(bag[pointer].equals(someEntry)) {
					/*
					 * this little combo just moves the entry to be removed
					 * to the end of the line that way we don't have a null
					 * in the middle of the array
					 */
					T temp = bag[pointer];
					bag[pointer] = bag[currentIndex-1];
					bag[currentIndex-1] = null;
					currentIndex--;
					return temp;
				}else
					pointer++;
			}
		}	
		return null;
	}
	
	@Override
	public boolean removeLast() {
		checkIntegrity();
		if(!isEmpty()) {
			bag[currentIndex-1] = null;
			currentIndex--;
			return true;
		}else
			return false;
	}
	
	@Override
	public boolean removeFirst() {
		checkIntegrity();
		if(!isEmpty()) {
			bag[0] = bag[currentIndex-1];
			bag[currentIndex-1] = null;
			currentIndex--;
			return true;
		}else
			return false;
	}

	@Override
	public void clear() {
		checkIntegrity();
		for(int i=0;i <= currentIndex; i++) {
			bag[i] = null;
		}
		currentIndex = 0;
	}

	@Override
	public String toString() {
		checkIntegrity();
		String temp = new String("{");
		for(int i=0;i<currentIndex-1;i++) {
			temp = temp.concat(String.valueOf(bag[i]) + ", ");
		}
		temp = temp.concat(String.valueOf(bag[currentIndex-1])+"}");
		return temp;
	}

	@Override
	public T peek(int index) {
		checkIntegrity();
		T temp = bag[index];
		return temp;
	}

	@Override
	public int getCurrentSize() {
		checkIntegrity();
		return currentIndex;
	}

	/**
	 * 	Used to make the bag larger in the case of an overflow
	 * 
	 * @param increase
	 * 	The amount of slots that will be added to
	 * the end of the array
	 */
	private void expand(int increase) {
		if (bag.length + increase <= MAX_CAPACITY) {
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) Array.newInstance(Object.class, bag.length + increase);
			for (int i = 0; i < bag.length; i++) {
				temp[i] = bag[i];
			}
			bag = temp;
		}else
			throw new IllegalStateException("Attempt to expand a bag whose " +
									"capacity exceeds allowed maximum.");
	}
	
	private void checkIntegrity() {
		if(!integrityOK) {
			throw new SecurityException("ArrayBag Object is corrupt");
		}
	}

	


		
	
}//END OF CLASS
