package stacks.arraystack;

import lists.simple.Node;

public class ArrayStack<T> {
	private T[] s;
	private int sp;
	private int size;

	public ArrayStack() {
		Node<T>[] s = (Node<T>[]) new Node[256];
		size = 0;
	}

	public void push(T dataValue) {
		assert size < s.length : "Stack can take no more";
		sp++;
		s[sp] = dataValue;
		++size;
	}

	public void pop() {
		assert size > 0 : "Stack empty";
		s[sp] = null;
		--sp;
		--size;
	}

	public T peek() {
		assert size > 0 : "Stack empty";
		return s[sp];
	}

	public String toString() {
		if (size == 0)
			return "Empty stack!";
		StringBuilder result = new StringBuilder();
		for (int i = sp; i >= 0; --i) {
			result.append(s[i] + " ");
		}
		return result.toString();
	}
}