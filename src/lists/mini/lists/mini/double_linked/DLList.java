package lists.mini.double_linked;

public class DLList<T> {
    private int size;

    private Node<T> front;
    private Node<T> rear;

    public DLList(T... vs) {
        for (T value : vs) add(value);
    }

    public int getSize() { return size;	}

    public boolean isEmpty() { return front == null; }

    public void add(T data) {
        Node<T> toAppend = new Node<T>(data);
        if (isEmpty()) {
            front = rear = toAppend;
        } else {
            toAppend.setPrev(rear);
            rear.setNext(toAppend);
            rear = toAppend;
        }
        size++;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("elements: " + size
                + ": ");
        if (isEmpty()) {
            result.append("List is empty!");
        } else {
            result.append("[ ");
            for (Node<T> it = front; it.next != null; it = it.next) { // don't go over rear because of formatting
                result.append(it + ", ");
            }
            result.append(rear + "]"); // add rear without comma in the end!
        }
        return result.toString();
    }

    public Node<T> get(int index) { // Return Node<T> and not T. Otherwise unable to reuse this method in remove()!
        if (index < size && index >= 0) { // if isEmpty(), index is not less than size
            if (index < size / 2) {
                Node<T> it = front;
                for (int i = 0; i < index; i++) it = it.next;
                return it;
            } else {
                Node<T> it = rear;
                for (int i = size; i > index; i--) it = it.prev;
                return it;
            }
        }
        throw new IllegalArgumentException("Size was " + size + " and index was " + index);
    }
}
