package lists.single_linked;

/**
 * Created by ra on 02.04.15. Simple generic linked list.
 */

public class Node<T> {
	public Node<T> next;
	private T data;

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}

	public void setData(T data) { this.data = data;	}

	public void setNext(Node<T> next) { this.next = next; }

	public T getData() { return data; }

	@Override
	public String toString() { return data.toString(); }

	public Node<T> cloneRec() {
		if (this.next != null) {
			return new Node<T>(data, next.cloneRec());
		} else {
			return new Node<T>(data, null);
		}
	}

}
