package lists.polymorph;

public class Node {
	public Node next;
	public Node prev;
	private Object data;

	public Node(Object data) {
		this.data = data;
	}

	public Node(Object data, Node prev, Node next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	public void setData(Object data) { this.data = data;	}

	public void setNext(Node next) { this.next = next; }
	
	public void setPrev(Node prev) { this.prev = prev; }

	public Object getData() { return data; }
	
	@Override
	public String toString() { return data.toString(); }

	public int index() {
		int counter = 0;
		for(Node it = this; it != null; it = it.prev) {
			counter++;
		}
		return counter;
	}

	public void connect(Node neighbour) {
		if (neighbour != null)
			if (next != null) {
				Node tmp = this.next;
				this.next = neighbour;
				neighbour.prev = this;

				neighbour.next = tmp;
				tmp.prev = neighbour;
			} else {
				this.next = neighbour;
				neighbour.prev = this;
			}
	}

	public Node cloneRec() {
		if (this.next != null) {
			return new Node(data, this, next.cloneRec());
		} else {
			return new Node(data, this, null);
		}
	}

}
