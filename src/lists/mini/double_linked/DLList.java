package double_linked;

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
        StringBuilder result = new StringBuilder("elements: " + size + "; ");
        if (isEmpty()) {
            result.append("List is empty!");
        } else {
            result.append("[");
            for (Node<T> it = front; it.next != null; it = it.next) { // don't go over rear because of formatting
                result.append(it + ", ");
            }
            result.append(rear + "]"); // add rear without comma in the end!
        }
        return result.toString();
    }

    private Node<T> getNode(int index) {
        if(0 <= index && index < size) {
        	Node<T> it = front;
            while (index > 0) {
                it = it.next;
                index--;
            }
            return it;
        }
        throw new IndexOutOfBoundsException("Index out of bounds: index was " + index + " and size was " + size); // ungraceful fail
    }

    public T get(int index) {
    	return getNode(index).getData();
    }
    /*
    Der gc löscht ja objekte auf die keine referenz mehr besteht. prüft er dabei ob die referenzen aus dem nirvana kommen?
    Wenn aus irgendeinem grund eine node nicht collected wird und der nachfolger dieser node gelöscht wird, zeigt die erste noch immer mit next auf die zuletzt gelöschte.
    Warum sollte der gc die node nicht löschen? Keine ahnung, aber man sagt ihm seltsames verhalten nach. Die gc-zeilen können gerne weg, es macht nicht viel sinn...
     */
    public void remove(int index) {
    	Node<T> toRemove = getNode(index); // if index is invalid: get() will throw error.
    	if (toRemove == front && toRemove == rear) {
            front = rear = null;
        }
        else if (toRemove == front) {
            toRemove.next.prev = null;  // detach front
            front = toRemove.next;      // set front
            toRemove.next = null;       // necessary for gc?
        }
        else if (toRemove == rear) {
            toRemove.prev.next = null;  // detach rear
            rear = toRemove.prev;       // set rear
            toRemove.prev = null;       // necessary for gc?
        }
        else {
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
            toRemove.prev = toRemove.next = null; // gc?
        }
    size--;
    }
}
