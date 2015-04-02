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
		rear = null;
	}

	public List(Node<T>... ns) {
		for (Node<T> node : ns) {
			append(node.getData());
		}
	}

	public List(T... vs) {
		for (T value : vs) {
			append(value);
		}
	}

	public int getNumberOfElems() {
		return numberOfElems;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void append(T data) {
		Node<T> n = new Node<T>(data);
		if (isEmpty()) {
			front = n;
			rear = n;
		} else {
			rear.setNext(n);
			rear = n;
			n.setNext(null); // redundant?
		}
		numberOfElems++;
	}

	public void display() {
		System.out.printf("%d elements: ", numberOfElems);
		if (isEmpty()) {
			System.out.println("List is empty!");
		} else {
			for (Node<T> it = front; it.next != null; it = it.next) {
				System.out.print(it + ", ");
			}
			System.out.println(rear);
		}
	}

	public void insert(Node<T> it, T dataVal) {
		assert it != null : "cannot insert after null!";
		Node<T> toInsert = new Node<T>(dataVal);

		if (it.next == null) {
			rear = toInsert;
		} else {
			toInsert.next = it.next;
		}
		it.next = toInsert;
		numberOfElems++;
	}

	public void addAt(int index, T dataVal) {
		if (index == 0) {
			prepend(dataVal);
		} else {
			Node<T> it = front;
			for (int i = 1; it != null && i < index; ++i)
				it = it.next;
			if (it != null)
				insert(it, dataVal);
		}
	}

	public void prepend(T data) {
		Node<T> toPrepend = new Node<T>(data);
		toPrepend.next = front;
		if (toPrepend.next == null) {
			rear = toPrepend;
		}
		front = toPrepend;
		numberOfElems++;
	}

	// public void delete_byVal(T dataVal) {
	// if (front != null && front.getData().equals(dataVal)) {
	// front = front.next;
	// if (front == null) {
	// rear = null;
	// }
	// numberOfElems--;
	// } else if (front != null) {
	// Node<T> it = front;
	// while (it.next != null) {
	// Node<T> n = it.next;
	// if (n.getData().equals(dataVal)) {
	// if (n.next == null) {
	// rear = it;
	// }
	// it.next = n.next;
	// numberOfElems--;
	// break;
	// }
	// }
	// }
	// }

}
