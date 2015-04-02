package lists.simple;

/**
 * Created by ra on 02.04.15. Simple generic linked list.
 */

public class Node<T> {
    public Node<T> next;
    private T data;

    public Node(T dataVal) {
        this.data = dataVal;
        this.next = null;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public String toString() {
        return data.toString();
    }
}
