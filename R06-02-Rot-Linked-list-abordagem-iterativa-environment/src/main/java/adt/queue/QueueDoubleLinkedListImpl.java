package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (element != null) {
			if (isFull()) {
				throw new QueueOverflowException();
			} else {
				list.insertFirst(element);
			}
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		} else {
			T elementoRemovido = list.toArray()[list.size() - 1];
			list.removeLast();
			return elementoRemovido;
		}
	}

	@Override
	public T head() {
		if (isEmpty()) {
			return null;
		} else {
			return list.toArray()[list.size() - 1];
		}
	}

	@Override
	public boolean isEmpty() {
		return (list.size() == 0);
	}

	@Override
	public boolean isFull() {
		return (list.size() == this.size);
	}

}
