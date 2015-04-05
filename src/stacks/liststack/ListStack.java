package stacks.liststack;

import lists.simple.Node;

public class ListStack<T> {
	private Node<T> sp;
	private int size;

	public ListStack() {
		sp = null;
		size = 0;
	}

	public void push(T dataValue) {
		Node<T> newEntry = new Node<T>(dataValue);
		newEntry.next = sp;
		sp = newEntry;
		++size;
	}

	public void pop() {
		assert sp != null : "Stack empty";
		sp = sp.next;
		--size;
	}

	public T peek() {
		assert sp != null : "Stack empty";
		return sp.getData();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Node<T> it = sp; it != null; it = it.next) {
			result.append(it);
		}
		return result.toString();
	}
}