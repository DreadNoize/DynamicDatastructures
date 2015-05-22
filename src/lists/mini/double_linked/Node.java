package double_linked;

public class Node<T> {
	private T data;
	Node<T> next; // no access modifier: package level access. (?better way¿)
	Node<T> prev;

	public Node(T data, Node<T> prev, Node<T> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	public Node(T data) { this.data = data; }

	public T getData() { return data; }

	public void setData(T data) {
		if (data != null) {
			this.data = data; }
	}

	public void setPrev(Node<T> prev) {
		if (prev != null) {
			this.prev = prev;
			prev.next = this; // An object has access to private features of same class
		}
	}

	public void setNext(Node<T> next) {
		if (next != null) {
			this.next = next;
			next.prev = this;
		}
	}

	public Node<T> getNext() { return next; }
	
	public Node<T> getPrev() { return prev; }

	@Override
	public String toString() {
		return data.toString();
	}

}