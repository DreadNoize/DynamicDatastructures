package lists.polymorph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This double linked {@link PolymorphicList} list only knows its front and rear
 * nodes. The nodes can contain elements of any complex type. It can do most
 * basic list operations like app - and prepending, adding and deleting at
 * indices, indexOf, clone and toString.
 * Constructor @params can be varargs of values.
 * */

public class PolymorphicList implements Iterable{
	private int size;

	private Node front;
	private Node rear;

	@SafeVarargs
	public PolymorphicList(Object... vs) {
		for (Object value : vs) {
			append(value);
		}
	}

	public Node getRear() { return rear; }

	private void setRear(Node newRear) {
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

	public int getSize() { return size; }

	/** Resets the size variable, useful after sublisting or setting new rear */
	private void countElems() {
		int counter = 0;
		for (@SuppressWarnings("unused") Object it : this) {
			counter++;
		}
		if (size != counter) //this is usually intended!
			//System.out.println("size: " + size + "actual size: " + counter + " ");
		size = counter;
	}

	/** resets the rear pointer to first element that has next == null */
	private void updateRear() {
		for (Node it = front; it != null; it = it.next) {
			if (it.next == null)
				setRear(it);
		}
		countElems();
	}

	public boolean isEmpty() { return front == null; }

	/**
	 * Makes a new rear element with @param data of matching type
	 * */
	public void append(Object data) {
		Node toAppend = new Node(data);
		if (isEmpty()) {
			front = toAppend;
			size++;
			rear = toAppend;
			} else {
			toAppend.setPrev(getRear());
			getRear().setNext(toAppend);
			size++;
			setRear(toAppend);
		}
	}

	/**
	 * Makes a new front element with @param data of matching type
	 * */
	public void prepend(Object data) {
		Node toPrepend = new Node(data);
		if (isEmpty()) { // toPrepend is alone
		    front = toPrepend;
		    rear = toPrepend;
		} else {
			toPrepend.setNext(front);
		    front.setPrev(toPrepend);
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
			for (Object it : this) {
				result.append(it + ", ");
			}
			result.append(getRear());
		}
		return result.toString();
	}

	/** @return array representation of list */
	public Node[] toArray() {
		countElems();

		Node[] array = new Node[size];
		for (int index = 0; index < size; index++) {
			array[index] = getNode(index);
		}
		return array;
	}

	public Node getNode(int index) {
		if (index < size && index >= 0) {
			Node it = front;
			for (int i = 0; it != null && i < index; ++i)
				it = it.next;
			return it;
		} else {
			throw new errors.IndexOutOfBoundsException("Couldn't get Node at " + index);
		}
	}

	public boolean contains(Object data) {
		if (size == 0)
			return false;
		for (Object it : this) {
			if (data.equals(it)) {
				return true;
			}
		}
		return false;
	}

	public int indexOf(Object data) {
		int index = 0;
		for (Node it = front; it != null; it = it.next) {
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

	public int countOccurence(Object data) {
		int counter = 0;
		for (Node it = front; it != null; it = it.next) {
			if (data.equals(it.getData()))
				counter++;
		}
		return counter;
	}

	public int[] search(Object data) {
		int currInd = 0;
		int[] indices = new int[countOccurence(data)];
		for (int i = 0; i < size; i++) {
			if (getNode(i).getData().equals(data))
				indices[currInd++] = i;
		}
		return indices;
	}

	public void insertAfterNode(Node it, Object data) {
		if (it == null)
			throw new errors.InsertAfterNullException();
		Node toInsert = new Node(data);

		if (it.next == null) { // inserted is last
			setRear(toInsert);
		} else {
			it.next.prev = toInsert;
			toInsert.next = it.next;
			size++;
		}
		toInsert.prev = it;
		it.next = toInsert;
	}

	public void addAtIndex(int index, Object data) {
		if (index == 0) {
			prepend(data);
		} else {
			Node it = getNode(index - 1);
			if (it != null)
				insertAfterNode(it, data);
		}
	}

	public void delete_byIndex(int index) {
		if (index >= 0 && index < size) {
			if (index == 0) {
				front = front.next;
				front.prev = null;
				size--;
			} else if (index == size - 1) {
				deleteLast();
			} else {
				Node toDelete = getNode(index);
				toDelete.next.prev = toDelete.prev;
				toDelete.prev.next = toDelete.next;
				size--;
			}
		}
	}
	
	public void deleteNode(Node toDelete) {
		if (toDelete != null && !isEmpty()) {
			for (Node it = front; it != null; it = it.next) {
				if (it.next == toDelete) {
					it.next = toDelete.next;
					size--;
				}
			}
		}
	}

	public void deleteLast() {
		setRear(getRear().prev);
	}

	public void delete_byVal(Object data) { delete_byIndex(indexOf(data)); }

	public void reverse() {
		for (Node it = rear; it != null; it = it.prev) {
			it.next = it.prev;
			/*
			 * Because iteration happens over the it.prev pointer,it cannot be
			 * modified from within the loop! otherwise it wont terminate.
			 * 
			 * Does it have to be another loop?
			 */
		}
		Node tmp = rear;
		rear = front;
		front = tmp; // swap end pointers
		setPrevPointers();
	}

	private void setPrevPointers() {
		Node tmp = null;
		for (Node it = front; it != null; it = it.next) {
			it.prev = tmp;
			tmp = it;
		}
	}
	public PolymorphicList concat(PolymorphicList other) {
		PolymorphicList connedInit = this.clone();
		PolymorphicList connedTail = other.clone();
		if (other.size == 0) {
			return connedInit;
		} else {
			// connect(rear) doesn't touch neighbor.next
			connedInit.rear.connect(connedTail.front);
			// it also doesn't touch parentList.rear
			connedInit.rear = connedTail.rear;
			connedInit.countElems(); 
			return connedInit;
		}
	}
	
	public PolymorphicList subList(int start, int end) {
		if (start >= 0 && end > start && end <= size) {
			PolymorphicList sublist = this.clone();
			sublist.setRear(sublist.getNode(end - 1));
			sublist.front = sublist.getNode(start);
			sublist.front.prev = null;
			return sublist;
		}
		System.out.println("Invalid slice!");
		return null;
	}
	
	public PolymorphicList tail() { return subList(1, size); }
	
	public PolymorphicList clone() {
		PolymorphicList clone = new PolymorphicList();
		if (size <= 0) {
			return clone;
		} else if (size == 1) {
			clone.append(front.getData());
			return clone;
		} else {
			for (Node it = front; it != null; it = it.next) {
				clone.append(it.getData());
			}
			return clone;
		}
	}

	public PolymorphicList cloneRec() {
		PolymorphicList clone = new PolymorphicList();
		clone.front = front.cloneRec();
		clone.updateRear();
		clone.countElems();
		return clone;
	}

	class PolymorphIterator implements Iterator<Object> {
		Node current = null;

		@Override
		public boolean hasNext() {
			if (current == null) {
				return front != null;
			}
			return current.next != null;
		}

		@Override
		public Object next() {
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
	public Iterator<Object> iterator() {
		return new PolymorphIterator();
	}
}
