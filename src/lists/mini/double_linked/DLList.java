package double_linked;

import java.util.Iterator;
import java.util.NoSuchElementException;

import lists.double_linked.Node;

public class DLList<T> implements Iterable<T> {
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
			rear.next = toAdd;
			rear = toAdd;
		}
		size++;
	}

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
		return result.toString();
	}

	public String printTypes() {
		StringBuilder result = new StringBuilder("# of elements is " + size + ". ");
		if (isEmpty()) {
			result.append("List is empty!");
		} else {
			for (T it : this) {
				result.append(it.getClass().getSimpleName() + ", ");
			}
		}
		return result.toString();
	}

	private Node<T> getNode(int index) {
		if (0 <= index && index < (size / 2)) {
			Node<T> it = front;
			while (index > 0) {
				it = it.next;
				index--;
			}
			return it;
		} else if (index >= (size / 2) && index < size) {
			Node<T> it = rear;
			index = size - index - 1;
			while (index > 0) {
				it = it.prev;
				index--;
			}
			return it;
		}
		throw new IndexOutOfBoundsException(
				"Index out of bounds: index was " + index
				+ " and size was " + size); // ungraceful fail
	}

	public T get(int index) {
		return getNode(index).getData();
	}

	private void removeNode (Node<T> toRemove) {
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
				throw new NoSuchElementException();
			if (current == null && front != null) {
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
			removeNode(current);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}
}
