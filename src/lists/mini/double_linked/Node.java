package lists.mini.double_linked;

public class Node<T> {
    private T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(T data, Node<T> prev, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Node(T data) { this.data = data; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data;	}

    public void setPrev(Node<T> prev) { this.prev = prev; }
    public void setNext(Node<T> next) { this.next = next; }

    @Override
    public String toString() { return data.toString(); }
}
