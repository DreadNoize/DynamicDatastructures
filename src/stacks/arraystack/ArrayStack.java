package stacks.arraystack;

import lists.simple.Node;

public class ArrayStack<T> {
	private Node<T>[] stack;
	private int sp;
	private int size;

	@SuppressWarnings("unchecked")
	public ArrayStack() {
		this.stack = (Node<T>[]) new Node[256];
		size = 0;
	}

	public void push(T dataValue) {
		assert size < stack.length : "Stack can take no more";
		sp++;
		stack[sp] = new Node<T> (dataValue);
		++size;
	}

	public void pop() {
		assert size > 0 : "Stack empty";
		stack[sp] = null;
		--sp;
		--size;
	}

	public Node<T> peek() {
		assert size > 0 : "Stack empty";
		return stack[sp];
	}

	public String toString() {
		if (size == 0)
			return "Empty stack!";
		StringBuilder result = new StringBuilder();
		for (int i = sp; i >= 0; --i) {
			result.append(stack[i] + " ");
		}
		return result.toString();
	}
}