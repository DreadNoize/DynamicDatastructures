package lists.simple;

/**
 * This simple generic list of type T knows only its front and rear nodes. It
 * can do most basic list operations like app - and prepending, adding and
 * deleting at indices, indexOf, clone and toString. 
 * Constructor @params can be varargs of values.
 * */

public class List<T> {
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

	private void setRear(Node<T> rear) {
		if (this.rear != null) {
			if (rear != null)
				this.rear.next = rear;
			this.rear = rear;
			rear.next = null;
			countElems();
		} else {
			updateRear();
			setRear(rear);
		}
	}
	
	public int getSize() { return size;	}

	private void countElems() {
		int counter = 0;
		for (Node<T> it = front; it != null; it = it.next) {
			counter++;
		}
		if(size != counter) //this is usually intended!
			//System.out.println("size: " + size + " actual size: " + counter + " ");
		size = counter;
	}

	private void updateRear() {
		for (Node<T> it = front; it != null; it = it.next) {
			if (it.next == null)
				this.rear = it;	
		}
		countElems();
	}

	public boolean isEmpty() { return front == null; }

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

	public void prepend(T data) {
		Node<T> toPrepend = new Node<T>(data);
		if (isEmpty()) {
		    front = toPrepend;
		    rear = toPrepend;
		} else {
			toPrepend.setNext(front);
		    front = toPrepend;
		    
		}
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
			throw new lists.IndexOutOfBoundsException("Couldn't get Node at " + index);
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
				throw new lists.IndexOutOfBoundsException((index - 1) + " is larger than size: " + size);
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

	public T head() { return this.front.getData(); }

	public List<T> tail() { return subList(1, size); }

	public List<T> subList(int start, int end) {
		if (start >= 0 && end > start && end <= size) {
			List<T> sublist = this.clone();
			sublist.setRear(sublist.getNode(end - 1));
			sublist.front = sublist.getNode(start);
			return sublist;
		}
		throw new lists.InvalidSliceException(size + ", " + start + ", " + end);
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

}
