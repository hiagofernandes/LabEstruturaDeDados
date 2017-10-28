package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insertFirst(T element) {

		if (element == null)
			return;

		if (!isEmpty()) {

			RecursiveDoubleLinkedListImpl<T> node = new RecursiveDoubleLinkedListImpl<T>();

			node.setData(this.data);

			this.setData(element);

			((RecursiveDoubleLinkedListImpl<T>) this.next).previous = node;

			node.previous = this;
			node.next = this.next;
			this.next = node;

		}

		else {

			previous = new RecursiveDoubleLinkedListImpl<T>();
			previous.next = this;

			this.next = new RecursiveDoubleLinkedListImpl<T>();
			((RecursiveDoubleLinkedListImpl<T>) next).previous = this;

			setData(element);
		}

	}

	@Override
	public void removeFirst() {

		if (!isEmpty()) {
			if (this.next.isEmpty()) {
				this.data = this.next.data;
			} else {
				this.data = this.next.getData();
				this.next = this.next.getNext();
				((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(this);
			}
		}

	}

	@Override
	public void removeLast() {

		if (!isEmpty()) {
			if (next.isEmpty())
				this.data = this.next.getData();
			else
				((RecursiveDoubleLinkedListImpl<T>) next).removeLast();
		}

	}

	@Override
	public void insert(T element) {

		if (element == null)
			return;

		if (isEmpty()) {
			this.data = element;
			this.previous = new RecursiveDoubleLinkedListImpl<>();
			this.next = new RecursiveSingleLinkedListImpl<>();
		}
		else if (this.next.isEmpty()) {
			this.next = new RecursiveDoubleLinkedListImpl<>(element, new RecursiveSingleLinkedListImpl<>(), this);
		}
		else {
			this.next.insert(element);
		}

	}

	@Override
	public void remove(T element) {

		if (element == null)
			return;

		if (!isEmpty()) {
			if (this.data.equals(element)) {
				if (this.next.isEmpty()) {
					this.data = null;
					this.next = null;
				} else {
					this.data = this.next.getData();
					this.next = this.next.getNext();
				}
			} else if (!this.next.isEmpty()) {
				this.next.remove(element);
			}

		}

	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
