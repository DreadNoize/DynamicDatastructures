package lists.simple;

/**
 * Created by ra on 01.04.15. Simple generic linked list.
 */

public class List<T> {
	private int numberOfElems;

	private Node<T> front;
	private Node<T> rear;

	public List() {
		front = null;
		setRear(null);
	}

	public List(Node<T>... ns) {
		for (Node<T> node : ns) {
			append(node.getData());
		}
	} // deprecate?

	public List(T... vs) {
		for (T value : vs) {
			append(value);
		}
	}

	public Node<T> getRear() {
		return rear;
	}

	private void setRear(Node<T> rear) {
		this.rear = rear;
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
		StringBuilder result = new StringBuilder("elements: " +  numberOfElems + ": ");
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
	
	public boolean contains (T data) {
		if(numberOfElems == 0) 
			return false;
		for (Node<T> it = front; it != null; it = it.next) {
			if(data.equals(it.getData())) {
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
		front = toPrepend;
		numberOfElems++;
	}

	public void insertAfterNode(Node<T> it, T dataVal) {
		assert it != null : "cannot insert after null!";
		Node<T> toInsert = new Node<T>(dataVal);

		if (it.next == null) {
			setRear(toInsert);
		} else {
			toInsert.next = it.next;
		}
		it.next = toInsert;
		numberOfElems++;
	}

	public void addAtIndex(int index, T dataVal) {
		if (index == 0) {
			prepend(dataVal);
		} else {
			Node<T> it = getNode(index - 1);
			if (it != null)
				insertAfterNode(it, dataVal);
		}
	}

	public void delete_byIndex(int index) {
		if (index >= 0 && index < numberOfElems) {
			if (index == 0) {
				front = front.next;
				numberOfElems--;
			} else {
				Node<T> toDeletePrev = getNode(index - 1);
				toDeletePrev.next = toDeletePrev.next.next;
				if (toDeletePrev.next == null) {
					setRear(toDeletePrev);
				}
				numberOfElems--;
			}
		}
	}

	public void delete_byVal(T data) {
		delete_byIndex(indexOf(data));
	}

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

	public List<T> clone() {
		List<T> clone = new List<T>();
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

	public List<T> cloneRec() {
		List<T> clone = new List<>();
		clone.front = front.cloneRec(this);

		clone.countElems();
		return clone;
	}

	public List<T> concat(List<T> other) {
		List<T> connedInit = this.clone();
		List<T> connedTail = other.clone();
		if (other.numberOfElems == 0) {
			return connedInit;
		}
		else {
			connedInit.rear.next = connedTail.front;
			connedInit.rear = connedTail.rear;
			connedInit.countElems();
			return connedInit;
		}

	}
	
	private void makeRear(Node<T> last) {
		if(!(last.next == null)) { // do nothing if last is already rear
			last.next = null;
			this.rear = last;
			countElems();
		}
	}
	
	public List<T> subList(int start, int end) {
		if(start >= 0 && end > start && end <= numberOfElems) {
			List<T> sublist = this.clone();
			sublist.makeRear(sublist.getNode(end-1));
			sublist.front = sublist.getNode(start);
			return sublist;
		}
		System.out.println("Invalid slice!"); // exception
		return null;
	}

}
