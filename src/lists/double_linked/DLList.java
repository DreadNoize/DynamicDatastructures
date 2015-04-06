package lists.double_linked;

import lists.SizeMismatchException;

/**
 * This double linked generic list of type T knows only its front and rear
 * nodes. It can do most basic list operations like app - and prepending, adding
 * and deleting at indices, indexOf, clone and toString.
 * Constructor @params can be varargs of values.
 */

public class DLList<T> {
	private int size;

	private Node<T> front;
	private Node<T> rear;

	@SafeVarargs
	public DLList(T... vs) {
		for (T value : vs) {
			append(value);
		}
	}

	public Node<T> getRear() {
		return rear;
	}

	private void setRear(Node<T> rear) {
		this.rear = rear;
		if (rear != null)
			rear.next = null;
		countElems();
	}

	public int getSize() {
		return size;
	}

	private void countElems() {
		int counter = 0;
		for (Node<T> it = front; it != null; it = it.next) {
			counter++;
		}
		if (size != counter)
			throw new SizeMismatchException("size: " + size + "actual size: " + counter + " ");
		size = counter;
	}

	private void updateRear() {
		for (Node<T> it = front; it != null; it = it.next) {
			if (it.next == null)
				setRear(it);
		}
		countElems();
	}

	public boolean isEmpty() { return front == null; }

	public void append(T data) {
		Node<T> toAppend = new Node<T>(data);
		if (isEmpty()) {
			front = toAppend;
			size++;
			setRear(toAppend);
		} else {
			toAppend.setPrev(getRear());
			getRear().setNext(toAppend);
			size++;
			setRear(toAppend);
		}
	}
	
	public void prepend(T data) {
		Node<T> toPrepend = new Node<T>(data);
		toPrepend.next = front;
		if (toPrepend.next == null) {
			setRear(toPrepend);
		}
		front.setPrev(toPrepend);
		front = toPrepend;
		size++;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("elements: " + size
				+ ": ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
			for (Node<T> it = front; it.next != null; it = it.next) {
				result.append(it + ", ");
			}
			result.append(getRear());
		}
		return result.toString();
	}
	
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
			throw new lists.IndexOutUfBoundsException("Couldn't get Node at " + index);
		}
	}
	public boolean contains(T data) {
		if (size == 0)
			return false;
		for (Node<T> it = front; it != null; it = it.next) {
			if (data.equals(it.getData())) {
				return true;
			}
		}
		return false;
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
			throw new lists.ElementNotFoundException(data.toString());
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
			throw new lists.InsertAfterNullException();
		Node<T> toInsert = new Node<T>(data);

		if (it.next == null) {
			setRear(toInsert);
		} else {
			it.next.prev = toInsert;
			toInsert.next = it.next;
			size++;
		}
		toInsert.prev = it;
		it.next = toInsert;
	}

	public void addAtIndex(int index, T data) {
		if (index == 0) {
			prepend(data);
		} else {
			Node<T> it = getNode(index - 1);
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
				Node<T> toDelete = getNode(index);
				toDelete.next.prev = toDelete.prev;
				toDelete.prev.next = toDelete.next;
				size--;
			}
		}
	}
	
	public void deleteLast() {
		setRear(getRear().prev);
		size--;
	}

	public void delete_byVal(T data) { delete_byIndex(indexOf(data)); }

	public void reverse() {
		for (Node<T> it = rear; it != null; it = it.prev) {
			it.next = it.prev;
			/*
			 * Because iteration happens over the it.prev pointer,it cannot be
			 * modified from within the loop! otherwise it wont terminate.
			 * 
			 * Does it have to be another loop?
			 */
		}
		Node<T> tmp = rear;
		rear = front;
		front = tmp; // swap end pointers
		setPrevPointers();
	}

	private void setPrevPointers() {
		Node<T> tmp = null;
		for (Node<T> it = front; it != null; it = it.next) {
			it.prev = tmp;
			tmp = it;
		}
	}
	public DLList<T> concat(DLList<T> other) {
		DLList<T> connedInit = this.clone();
		DLList<T> connedTail = other.clone();
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
	
	public DLList<T> subList(int start, int end) {
		if (start >= 0 && end > start && end <= size) {
			DLList<T> sublist = this.clone();
			sublist.setRear(sublist.getNode(end - 1));
			sublist.front = sublist.getNode(start);
			sublist.front.prev = null;
			return sublist;
		}
		System.out.println("Invalid slice!");
		return null;
	}
	
	public T head() { return this.front.getData(); }

	public DLList<T> tail() { return subList(1, size); }
	
	public DLList<T> clone() {
		DLList<T> clone = new DLList<T>();
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

	public DLList<T> cloneRec() {
		DLList<T> clone = new DLList<>();
		clone.front = front.cloneRec();
		clone.updateRear();
		clone.countElems();
		return clone;
	}
}
