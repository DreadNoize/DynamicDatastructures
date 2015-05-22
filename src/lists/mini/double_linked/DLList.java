package double_linked;

import java.util.Iterator;

public class DLList<T> implements Iterable<T> {
    private int size;

	private Node<T> front;
	private Node<T> rear;

	public DLList(T... vs) { // varargs
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
		Node<T> toAdd = new Node<>(data);
		if (isEmpty()) {
			front = rear = toAdd;
		} else {
			toAdd.setPrev(rear); // also handles setting rear.next to toAdd
			rear = toAdd;
		}
		size++;
	}

	/** @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html#setLength(int)">setLength docs</a> **/
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("# of elements is " + size + ". ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
            for (T it: this) {
				result.append(it + ", ");
			}
		}
		result.setLength(result.length() - 2); // remove last comma in 0(1)
		return result.toString();
	}

	public String printTypes() { // sinnvoll wenn T Object ist; sonst vielleicht noch bei vererbung
		StringBuilder result = new StringBuilder("# of elements is " + size + ". ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
			for (T it : this) {
				result.append(it.getClass().getSimpleName() + ", ");
			}
		}
		result.setLength(result.length() - 2); // remove last comma in 0(1)
		return result.toString();
	}

	private Node<T> getNode(int index) {
		if (0 <= index && index < (size / 2)) { // first half
			Node<T> it = front;
			while (index > 0) {
				it = it.next;
				index--; // use index as counter
			}
			return it;
		} else if (index >= (size / 2) && index < size) { //second half
			Node<T> it = rear;
			index = size - index - 1; // distance from wanted node to rear
			while (index > 0) {
				it = it.prev;
				index--; // use index as counter
			}
			return it;
		} // arrived here? if's above cover entire range → must be invalid index
		throw new IndexOutOfBoundsException(
				"Index out of bounds: index was " + index
				+ " and size was " + size); // ungraceful fail
	}

	public T get(int index) {
		return getNode(index).getData();
	}
    /** remove a given node. Needed for Iterator **/
	private void removeNode (Node<T> toRemove) {
		if (toRemove == front && toRemove == rear) { // single node
            front = rear = null;
        }
        else if (toRemove == front) {
            toRemove.next.prev = null;  // detach front
            front = toRemove.next;      // set new front
        }
        else if (toRemove == rear) {
            toRemove.prev.next = null;  // detach rear
            rear = toRemove.prev;       // set new rear
        }
        else {                          // prev != null && next != null
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }
    size--;
	}

	public void remove(int index) {
		removeNode(getNode(index)); // if index is invalid: getNode will err.
	}

	class DLLIterator implements Iterator<T> {
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
				throw new java.util.NoSuchElementException();
			if (current == null && front != null) {
				current = front;
			} else {
				if (current.next == null)
					throw new java.util.NoSuchElementException();
				current = current.next;
			}
			return current.getData();
		}

		@Override
		public void remove() {
			removeNode(current);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}
}
