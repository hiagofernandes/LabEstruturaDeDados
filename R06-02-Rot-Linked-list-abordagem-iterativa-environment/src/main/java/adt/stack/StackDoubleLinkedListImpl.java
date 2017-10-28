package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element != null) {
			if (this.isFull()) {
				throw new StackOverflowException();
			} else {
				top.insertFirst(element);
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (this.isEmpty()) {
			throw new StackUnderflowException();
		} else {
			T elementoRemovido = top.toArray()[0];
			top.removeFirst();
			return elementoRemovido;
		}
	}

	@Override
	public T top() {
		if (this.isEmpty()) {
			return null;
		} else {
			return top.toArray()[0];
		}
	}

	@Override
	public boolean isEmpty() {
		return (top.size() == 0);
	}

	@Override
	public boolean isFull() {
		return (top.size() == this.size);
	}
}
