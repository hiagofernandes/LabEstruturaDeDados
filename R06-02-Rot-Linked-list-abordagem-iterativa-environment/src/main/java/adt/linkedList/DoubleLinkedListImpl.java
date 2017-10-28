package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		this.head = new DoubleLinkedListNode<T>();
		this.last = (DoubleLinkedListNode<T>) head;
	}

	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> newHeadNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) head,
				new DoubleLinkedListNode<T>());

		((DoubleLinkedListNode<T>) head).previous = newHeadNode;

		if (head.isNIL()) {
			last = newHeadNode;
		}

		head = newHeadNode;

	}

	@Override
	public void removeFirst() {
		if (!head.isNIL()) {
			head = head.next;

			if (head.isNIL()) {
				last = new DoubleLinkedListNode<T>();
			}

			((DoubleLinkedListNode<T>) head).previous = new DoubleLinkedListNode<T>();
		}
	}

	@Override
	public void removeLast() {
		if (!last.isNIL()) {
			last = last.previous;

			if (last.isNIL()) {
				head = last;
			}

			last.next = new DoubleLinkedListNode<T>();
		}
	}

	@Override
	public void insert(T element) {
		DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), last);

		last.next = newLast;

		if (last.isNIL()) {
			head = newLast;
		}

		last = newLast;

	}

	@Override
	public void remove(T element) {
		if (head.getData() == (element)) {
			removeFirst();
		} else if (last.getData() == (element)) {
			removeLast();
		} else {
			SingleLinkedListNode<T> aux = head;

			while (!aux.isNIL() && !aux.data.equals(element)) {
				aux = aux.next;
			}

			if (!aux.isNIL()) {
				((DoubleLinkedListNode<T>) aux).previous.next = aux.next;
				((DoubleLinkedListNode<T>) aux.next).previous = ((DoubleLinkedListNode<T>) aux).previous;
			}
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
