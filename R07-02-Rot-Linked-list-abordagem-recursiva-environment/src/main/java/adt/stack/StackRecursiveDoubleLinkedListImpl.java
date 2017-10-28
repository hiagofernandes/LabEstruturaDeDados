package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		
		if (element == null)
			return;

		if (!isFull()) {
			this.top.insert(element);
		} else
			throw new StackOverflowException();

	}

	@Override
	public T pop() throws StackUnderflowException {
		if (!isEmpty()) {
			T element = top();
			this.top.removeLast();
			return element;
		} else
			throw new StackUnderflowException();
	}

	@Override
	public T top() {
		if (!isEmpty())
			return top.toArray()[top.size() - 1];
		return null;
	}

	@Override
	public boolean isEmpty() {
		return top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return top.size() == this.size;
	}

}
