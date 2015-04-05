package lists.double_linked;

/**
 * Created by ra on 02.04.15. Simple generic linked list.
 */

public class Node<T> {
	public Node<T> next;
	public Node<T> prev;
	private T data;

	public Node(T data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}

	public Node(T data, Node<T> prev, Node<T> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	public void setData(T data) { this.data = data;	}

	public void setNext(Node<T> next) { this.next = next; }
	
	public void setPrev(Node<T> prev) { this.prev = prev; }

	public T getData() { return data; }
	
	public String toString() {
		return data.toString();
	}

	public int index() {
		int counter = 0;
		for(Node<T> it = this; it != null; it = it.prev) {
			counter++;
		}
		return counter;
	}

	public void connect(Node<T> neighbour) {
		if (neighbour != null)
			if (next != null) {
				Node<T> tmp = this.next;
				this.next = neighbour;
				neighbour.prev = this;

				neighbour.next = tmp;
				tmp.prev = neighbour;
			} else {
				this.next = neighbour;
				neighbour.prev = this;
			}
	}

	// still doesn't work.
	public Node<T> cloneRec(DLList<T> parent) {
		if (this.next != null) {
			return new Node<T>(data, this, next.cloneRec(parent));
		} else {
			return new Node<T>(data, this, null);
		}
	}

}
