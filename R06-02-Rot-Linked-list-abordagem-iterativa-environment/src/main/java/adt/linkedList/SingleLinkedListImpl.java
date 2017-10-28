package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> auxNode = head;
		while (!auxNode.isNIL()) {
			size++;
			auxNode = auxNode.next;
		}
		return size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> auxNode = head;

		while (!auxNode.isNIL() && !auxNode.getData().equals(element)) {
			auxNode = auxNode.next;
		}

		return auxNode.getData();
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, new SingleLinkedListNode<T>());

		if (head.isNIL()) {
			head = newNode;
		} else {
			SingleLinkedListNode<T> auxNode = head;

			while (!auxNode.next.isNIL()) {
				auxNode = auxNode.next;
			}

			newNode.next = auxNode.next;
			auxNode.next = newNode;
		}
	}

	@Override
	public void remove(T element) {
		if (head.getData() == (element)) {
			head = head.next;
		} else {
			SingleLinkedListNode<T> auxNode = head;
			SingleLinkedListNode<T> previous = new SingleLinkedListNode<T>();

			while ((!auxNode.isNIL()) && (!auxNode.getData().equals(element))) {
				previous = auxNode;
				auxNode = auxNode.next;
			}

			if (!auxNode.isNIL()) {
				previous.next = auxNode.next;
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] newArray = (T[]) new Object[this.size()];

		SingleLinkedListNode<T> auxNode = head;
		int count = 0;

		while (!auxNode.isNIL()) {
			newArray[count] = auxNode.getData();
			auxNode = auxNode.next;
			count++;
		}

		return newArray;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}
