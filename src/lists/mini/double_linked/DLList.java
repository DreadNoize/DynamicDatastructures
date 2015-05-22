package double_linked;

public class DLList<T> {
    private int size;

	private Node<T> front;
	private Node<T> rear;

	public DLList(T... vs) {
		for (T value : vs)
			add(value);
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return front == null && rear == null;
	}

	public void add(T data) {
		Node<T> toAdd = new Node<T>(data);
		if (isEmpty()) {
			front = rear = toAdd;
		} else {
			toAdd.setPrev(rear); // also handles setting rear.next to this
			rear = toAdd;
		}
		size++;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("elements: " + size + "; ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
			result.append("[");
            for (Node<T> it = front; it.next != null; it = it.next) { // don't go over rear because of formatting
				result.append(it + ", ");
			}
           result.append(rear + "]"); // add rear without comma in the end!
		}
		return result.toString();
	}

	private Node<T> getNode(int index) {
		if (0 <= index && index < size) {
			Node<T> it = front;
			while (index > 0) {
				it = it.next;
				index--;
			}
			return it;
		}
		throw new IndexOutOfBoundsException("Index out of bounds: index was "
				+ index + " and size was " + size); // ungraceful fail
	}

	public T get(int index) {
		return getNode(index).getData();
	}

	public void remove(int index) {
    	Node<T> toRemove = getNode(index); // if index is invalid: get() will throw error.
    	if (toRemove == front && toRemove == rear) {
            front = rear = null;
        }
        else if (toRemove == front) {
            toRemove.next.prev = null;  // detach front
            front = toRemove.next;      // set front
        }
        else if (toRemove == rear) {
            toRemove.prev.next = null;  // detach rear
            rear = toRemove.prev;       // set rear
        }
        else {
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }
    size--;
    }
}
