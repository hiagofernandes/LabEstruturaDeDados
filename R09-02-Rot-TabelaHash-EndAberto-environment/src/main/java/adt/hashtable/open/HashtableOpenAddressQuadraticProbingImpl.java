package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {

		if (super.isFull())
			throw new HashtableOverflowException();

		int prob = 0;
		int hashKey = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, prob);

		if (element != null) {

			while (prob != table.length - 1) {
				if (search(element) != null)
					break;
				if (table[hashKey] == null || table[hashKey] instanceof DELETED) {
					table[hashKey] = element;
					elements++;
				} else {
					prob++;
					hashKey = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, prob);
					COLLISIONS++;
				}
			}

		}

	}

	@Override
	public void remove(T element) {
		if (element != null) {
			if (search(element) != null) {
				table[indexOf(element)] = new DELETED();
				elements--;
			}
		}
	}

	@Override
	public T search(T element) {
		T result = null;
		if (element != null) {
			if (indexOf(element) != -1) {
				result = element;
			}
		}
		return result;
	}

	@Override
	public int indexOf(T element) {
		if (element != null) {
			int probi = 0;
			int hashKey = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probi);
			while (probi != table.length - 1) {
				if (table[hashKey] == null) {
					return -1;
				}
				if (table[hashKey].equals(element)) {
					return hashKey;
				} else {
					probi++;
					hashKey = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probi);
				}
			}
		}
		return -1;
	}
}
