package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> x = root;
		int index;
		for (int i = this.maxHeight; i > 0; i--) {
			index = i - 1;
			while (x.forward[index] != null && x.forward[index].key < key) {
				x = x.forward[index];
			}
			update[index] = x;
		}
		x = x.forward[0];
		if (x.key == key) {
			x.value = newValue;
		} else {
			int v = height;
			x = new SkipListNode<T>(key, v, newValue);
			for (int i = 0; i < v; i++) {
				x.forward[i] = update[i].forward[i];
				update[i].forward[i] = x;
			}
		}

	}

	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> x = root;
		int index;
		for (int i = maxHeight; i > 0; i--) {
			index = i - 1;
			while (x.forward[index] != null && x.forward[index].key < key) {
				x = x.forward[index];
			}
			update[index] = x;
		}
		x = x.forward[0];
		if (x.key == key) {
			for (int i = 0; i < maxHeight; i++) {
				if (update[i].forward[i] != x) { // se não tiver apontando pro
													// nó a ser removido pare!
					break;
				}
				update[i].forward[i] = x.forward[i]; // arruma o apontador
			}
		}

	}

	@Override
	public int height() {
		if (size() != 0) {
			SkipListNode<T> aux = root.forward[0];
			int largest = 0;
			while (!aux.equals(NIL)) {
				if (aux.height() > largest) {
					largest = aux.height();
				}
				aux = aux.forward[0];
			}
			return largest;
		}
		// Uma skip sem nodes além do root e do NIL tem altura 0.
		return 0;

	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> x = root;
		int index;
		for (int i = maxHeight; i > 0; i--) {
			index = i - 1;
			while (x.forward[index] != null && x.forward[index].key < key) {
				x = x.forward[index];
			}
		}
		x = x.forward[0];
		if (x.key == key) {
			return x;
		}
		return null;

	}

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> x = root;

		while (x.forward[0] != NIL) {
			x = x.forward[0];
			size++;
		}
		return size;

	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];
		SkipListNode<T> x = root;
		int i = 0;
		while (i < size() + 2) {
			array[i] = x;
			i++;
			x = x.forward[0];
		}
		return array;

	}

}
