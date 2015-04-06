package lists.polymorph;

/**
 * This double linked {@link PolymorphicList} list only knows its front and rear
 * nodes. The nodes can contain elements of any complex type. It can do most
 * basic list operations like app - and prepending, adding and deleting at
 * indices, indexOf, clone and toString.
 * Constructor @params can be varargs of values.
 * */

public class PolymorphicList {
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

	private void setRear(Node rear) {
		this.rear = rear;
		if (rear != null)
			rear.next = null;
		countElems();
	}

	public int size() { return size; }

	private void countElems() {
		int counter = 0;
		for (Node it = front; it != null; it = it.next) {
			counter++;
		}
		size = counter;
	}

	private void updateRear() {
		for (Node it = front; it != null; it = it.next) {
			if (it.next == null)
				setRear(it);
		}
		countElems();
	}

	public boolean isEmpty() { return front == null; }

	public void append(Object data) {
		Node toAppend = new Node(data);
		if (isEmpty()) {
			front = toAppend;
			setRear(toAppend);
		} else {
			toAppend.setPrev(getRear());
			getRear().setNext(toAppend);
			setRear(toAppend);
		}
		size++;
	}

	public void prepend(Object data) {
		Node toPrepend = new Node(data);
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
			for (Node it = front; it.next != null; it = it.next) {
				result.append(it + ", ");
			}
			result.append(getRear());
		}
		return result.toString();
	}
	
	public Node[] toArray() {
		int currentElems = size;
		countElems();
		if (currentElems != size) {
			throw new lists.SizeMismatchException("While trying to create array");
		}

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
			throw new lists.IndexOutUfBoundsException("Couldn't get Node at " + index);
		}
	}
	public boolean contains(Object data) {
		if (size == 0)
			return false;
		for (Node it = front; it != null; it = it.next) {
			if (data.equals(it.getData())) {
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
			throw new lists.ElementNotFoundException(data.toString());
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
			throw new lists.InsertAfterNullException();
		Node toInsert = new Node(data);

		if (it.next == null) {
			setRear(toInsert);
		} else {
			it.next.prev = toInsert;
			toInsert.next = it.next;
		}
		toInsert.prev = it;
		it.next = toInsert;
		size++;
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
	
	public void deleteLast() {
		setRear(getRear().prev);
		size--;
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
	
	public Object head() { return this.front.getData(); }

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
}
