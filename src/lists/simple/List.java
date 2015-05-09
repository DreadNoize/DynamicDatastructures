package lists.simple;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This simple generic list of type T knows only its front and rear nodes. It
 * can do most basic list operations like app - and prepending, adding and
 * deleting at indices, indexOf, clone and toString. 
 * Constructor @params can be varargs of values.
 * */

public class List<T> implements Iterable<T>{
	private int size;

	private Node<T> front;
	private Node<T> rear;

	@SafeVarargs
	public List(T... vs) {
		for (T value : vs) {
			append(value);
		}
	}

	public Node<T> getRear() { return rear; }

	private void setRear(Node<T> newRear) {
		if (this.rear != null) {
			if (newRear != null)
				this.rear.next = newRear;
			this.rear = newRear;
			newRear.next = null;
			countElems();
		} else {
			updateRear();
			setRear(newRear);
		}
	}
	
	public int getSize() { return size;	}

	/** Resets the size variable, useful after sublisting or setting new rear */
	private void countElems() {
		int counter = 0;
		for (@SuppressWarnings("unused") T it : this) {
			counter++;
		}
		if(size != counter) //this is usually intended!
			//System.out.println("size: " + size + " actual size: " + counter + " ");
		size = counter;
	}

	/** resets the rear pointer to first element that has next == null */
	private void updateRear() {
		for (Node<T> it = front; it != null; it = it.next) {
			if (it.next == null)
				this.rear = it;	
		}
		countElems();
	}

	public boolean isEmpty() { return front == null; }

	/**
	 * Makes a new front element with @param data of matching type
	 * */
	public void append(T data) {
		Node<T> toAppend = new Node<T>(data);
		if (isEmpty()) {
			front = toAppend;
			size++;
			rear = toAppend;
		} else {
			getRear().setNext(toAppend);
			size++;
			setRear(toAppend);
		}
	}

	/**
	 * Makes a new rear element with @param data of matching type
	 * */
	public void prepend(T data) {
		Node<T> toPrepend = new Node<T>(data);
		if (isEmpty()) { // toPrepend is alone
		    front = toPrepend;
		    rear = toPrepend;
		} else {
			toPrepend.setNext(front);
		    front = toPrepend;
		}
		size++;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("elements: " + size
				+ ": ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
			for (T it : this) {
				result.append(it + ", ");
			}
			result.append(getRear());
		}
		return result.toString();
	}

	/** @return array representation of list */
	public Node<T>[] toArray() {
		countElems();
		
		@SuppressWarnings("unchecked")
		Node<T>[] array = (Node<T>[]) new Node[size];
		for (int index = 0; index < size; index++) {
			array[index] = getNode(index);
		}
		return array;
	}

	public Node<T> getNode(int index) {
		if (index < size && index >= 0) {
			Node<T> it = front;
			for (int i = 0; it != null && i < index; ++i)
				it = it.next;
			return it;
		} else {
			throw new errors.IndexOutOfBoundsException("Couldn't get Node at " + index);
		}
	}

	public boolean contains(T data) {
		if (size == 0)
			return false;
		for (T it : this) {
			if (data.equals(it)) {
				return true;
			}
		}
		return false;
	}

	public T minimum() {
		if (front.getData() instanceof Comparable) {
			System.out.println(front);
		}
		return front.getData();
	}

	public int indexOf(T data) {
		int index = 0;
		for (Node<T> it = front; it != null; it = it.next) {
			if (it.getData().equals(data)) {
				break;
			} else {
				index++;
			}
		}
		if (index >= size) {
			throw new errors.ElementNotFoundException(data.toString());
		}
		return index;
	}

	public int countOccurence(T data) {
		int counter = 0;
		for (Node<T> it = front; it != null; it = it.next) {
			if (data.equals(it.getData()))
				counter++;
		}
		return counter;
	}

	public int[] search(T data) {
		int currInd = 0;
		int[] indices = new int[countOccurence(data)];
		for (int i = 0; i < size; i++) {
			if (getNode(i).getData().equals(data))
				indices[currInd++] = i;
		}
		return indices;
	}
	
	public void insertAfterNode(Node<T> it, T data) {
		if (it == null)
			throw new errors.InsertAfterNullException();
		Node<T> toInsert = new Node<T>(data);

		if (it.next == null) { // inserted is last
			setRear(toInsert);
		} else {
			toInsert.next = it.next;
			size++;
		}
		it.next = toInsert;
	}

	public void addAtIndex(int index, T data) {
		if (index == 0) {
			prepend(data);
		} else {
			if ((index - 1) > size)
				throw new errors.IndexOutOfBoundsException((index - 1) + " is larger than size: " + size);
			Node<T> it = getNode(index - 1);
			if (it != null)
				insertAfterNode(it, data);
		}
	}

	public void delete_byIndex(int index) {
		if (index >= 0 && index < size) {
			if (index == 0) {
				front = front.next;
				size--;
			} else {
				Node<T> toDeletePrev = getNode(index - 1);
				toDeletePrev.next = toDeletePrev.next.next;
				size--;
				if (toDeletePrev.next == null) {
					setRear(toDeletePrev);
				}
			}
		}
	}

	public void deleteNode(Node<T> toDelete) {
		if (toDelete != null && !isEmpty()) {
			for (Node<T> it = front; it != null; it = it.next) {
				if (it.next == toDelete) {
					it.next = toDelete.next;
					size--;
				}
			}
		}
	}

	public void delete_byVal(T data) { delete_byIndex(indexOf(data)); }

	public void reverse() {
		Node<T> nextEntry, head;
		Node<T> temp = null;
		if (!isEmpty()) {
			head = front;
			while (head.next != null) {
				nextEntry = head.next;
				head.next = temp;
				temp = head;
				head = nextEntry;
			}
			head.next = temp;
			temp = front;
			front = getRear();
			setRear(temp);
		}
	}

	public List<T> concat(List<T> other) {
		List<T> connedInit = this.clone();
		List<T> connedTail = other.clone();
		if (other.size == 0) {
			return connedInit;
		} else {
			connedInit.rear.next = connedTail.front;
			connedInit.rear = connedTail.rear;
			connedInit.countElems();
			return connedInit;
		}
	}

	public List<T> tail() { return subList(1, size); }

	public List<T> subList(int start, int end) {
		if (start >= 0 && end > start && end <= size) {
			List<T> sublist = this.clone();
			sublist.setRear(sublist.getNode(end - 1));
			sublist.front = sublist.getNode(start);
			return sublist;
		}
		throw new errors.InvalidSliceException(size + ", " + start + ", " + end);
	}

	public List<T> clone() {
		List<T> clone = new List<T>();
		if (size <= 0) {
			return clone;
		} else if (size == 1) {
			clone.append(front.getData());
			return clone;
		} else {
			for (Node<T> it = front; it != null; it = it.next) {
				clone.append(it.getData());
			}
			return clone;
		}
	}

	public List<T> cloneRec() {
		List<T> clone = new List<>();
		clone.front = front.cloneRec();
		clone.updateRear();
		clone.size = size;
		return clone;
	}

	class SimpleIterator implements Iterator<T> {
		Node<T> current = null;

		@Override
		public boolean hasNext() {
			if (current == null) {
				return front != null;
			}
			return current.next != null;
		}

		@Override
		public T next() {
			if (isEmpty())
				throw new NoSuchElementException();
			if(current == null && front != null){
				current = front;
			} else {
				if (current.next == null)
					throw new NoSuchElementException();
				current = current.next;
			}
			return current.getData();
		}

		@Override
		public void remove() {
			deleteNode(current);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new SimpleIterator();
	}
}
