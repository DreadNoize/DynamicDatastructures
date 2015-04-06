package lists.polymorph;

public class Bidirect {
	private Node prev;
	private Node next;
	
	Bidirect(Node prev, Node next) {
		this.prev = prev;
		this.next = next;
	}
	
	public Node getPrev() { return prev; }
	
	public void setPrev(Node prev) { this.prev = prev; }
	
	public Node getNext() { return next; }
	
	public void setNext(Node next) { this.next = next; }
	
	@Override
	public String toString() {
		return "Bidirect connecting prev=" + prev + " and next=" + next;
	}

	public void swap () {
		Node tmp = next;
		next = prev;
		prev = tmp;
	}
	
}
