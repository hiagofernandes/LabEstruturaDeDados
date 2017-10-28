package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		boolean empty = false;
		if (this.getData() == null)
			empty = true;
		return empty;
	}

	@Override
	public int size() {
		if (isEmpty())
			return 0;
		return 1 + getNext().size();
	}

	@Override
	public T search(T element) {

		if (element == null)
			return null;

		if (isEmpty())
			return null;
		else {
			if (this.getData() == element)
				return getData();
			else
				return getNext().search(element);
		}
	}

	@Override
	public void insert(T element) {

		if (element == null)
			return;

		if (isEmpty()) {
			setData(element);
			setNext(new RecursiveSingleLinkedListImpl<T>());
		} else {
			getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {

		if (element == null)
			return;

		if (!isEmpty()) {
			if (getData() == element) {
				setData(getNext().getData());
				setNext(getNext().getNext());
			} else {
				getNext().remove(element);
			}
		}

	}

	@Override
	public T[] toArray() {
		int count = 0;
		T[] array = (T[]) new Object[size()];

		if (isEmpty())
			return array;

		toArray(array, this, count);
		return array;
	}

	private void toArray(T[] array, RecursiveSingleLinkedListImpl<T> node, int count) {

		if (!node.isEmpty()) {
			array[count] = node.getData();
			count++;
			toArray(array, node.getNext(), count);
		}

	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
