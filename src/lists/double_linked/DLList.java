package lists.double_linked;

/**
 * Created by ra on 01.04.15. Simple generic linked list.
 */

public class DLList<T> {
	private int numberOfElems;

	private Node<T> front;
	private Node<T> rear;

	public DLList() {
		front = null;
		setRear(null);
	}

	public Node<T> getFront() {
		return front;
	}

	public DLList(Node<T>... ns) {
		for (Node<T> node : ns) {
			append(node.getData());
		}
	} // deprecate?

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
	}

	public int getNumberOfElems() {
		return numberOfElems;
	}

	private void countElems() {
		int counter = 0;
		for (Node<T> it = front; it != null; it = it.next) {
			counter++;
		}
		numberOfElems = counter;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("elements: " + numberOfElems
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

	public boolean contains(T data) {
		if (numberOfElems == 0)
			return false;
		for (Node<T> it = front; it != null; it = it.next) {
			if (data.equals(it.getData())) {
				return true;
			}
		}
		return false;
	}

	public Node<T> getNode(int index) {
		if (index < numberOfElems && index >= 0) {
			Node<T> it = front;
			for (int i = 0; it != null && i < index; ++i)
				it = it.next;
			return it;
		} else {
			System.out.println("Index out of bounds: " + index);
			return null;
		}
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
		assert index < numberOfElems : data + " not contained in list!";
		return index < numberOfElems ? index : -1; // very ugly if used to
													// Haskell 'Maybe a'. Throw
													// exception?

	}

	public void append(T data) {
		Node<T> toAppend = new Node<T>(data);
		if (isEmpty()) {
			front = toAppend;
			setRear(toAppend);
		} else {
			toAppend.setPrev(getRear());
			getRear().setNext(toAppend);
			setRear(toAppend);
		}
		numberOfElems++;
	}

	public void prepend(T data) {
		Node<T> toPrepend = new Node<T>(data);
		toPrepend.next = front;
		if (toPrepend.next == null) {
			setRear(toPrepend);
		}
		front.setPrev(toPrepend);
		front = toPrepend;
		numberOfElems++;
	}

	public void insertAfterNode(Node<T> it, T data) {
		assert it != null : "cannot insert after null!";
		Node<T> toInsert = new Node<T>(data);

		if (it.next == null) {
			setRear(toInsert);
		} else {
			it.next.prev = toInsert;
			toInsert.next = it.next;
		}
		toInsert.prev = it;
		it.next = toInsert;
		numberOfElems++;
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

	public void deleteLast() {
		setRear(getRear().prev);
		numberOfElems--;
	}

	public void delete_byIndex(int index) {
		if (index >= 0 && index < numberOfElems) {
			if (index == 0) {
				front = front.next;
				front.prev = null;
				numberOfElems--;
			} else if (index == numberOfElems - 1) {
				deleteLast();
			} else {
				Node<T> toDelete = getNode(index);
				toDelete.next.prev = toDelete.prev;
				toDelete.prev.next = toDelete.next;
				numberOfElems--;
			}
		}
	}

	public void delete_byVal(T data) {
		delete_byIndex(indexOf(data));
	}

	public void reverse() {
		for (Node<T> it = rear; it != null; it = it.prev) {
			it.next = it.prev;
			/*
			 * Because iteration happens over the it.prev pointer, we cannot
			 * modify it from within the loop! otherwise it wont terminate. We
			 * have to do another loop?
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

	// below be dragons! (Anywhere be dragons, really.)

	public DLList<T> clone() {
		DLList<T> clone = new DLList<T>();
		if (numberOfElems <= 0) {
			return clone;
		} else if (numberOfElems == 1) {
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
		clone.front = front.cloneRec(this);

		clone.countElems();
		return clone;
	}

	public DLList<T> concat(DLList<T> other) {
		DLList<T> connedInit = this.clone();
		DLList<T> connedTail = other.clone();
		if (other.numberOfElems == 0) {
			return connedInit;
		} else {
			connedInit.rear.next = connedTail.front;
			connedInit.rear = connedTail.rear;
			connedInit.countElems();
			return connedInit;
		}

	}

	private void makeRear(Node<T> last) {
		if (!(last.next == null)) { // do nothing if last is already rear
			last.next = null;
			this.rear = last;
			countElems();
		}
	}

	public DLList<T> subList(int start, int end) {
		if (start >= 0 && end > start && end <= numberOfElems) {
			DLList<T> sublist = this.clone();
			sublist.makeRear(sublist.getNode(end - 1));
			// System.out.println(sublist.rear);
			sublist.front = sublist.getNode(start);
			return sublist;
		}
		System.out.println("Invalid slice!");
		return null;
	}

}
