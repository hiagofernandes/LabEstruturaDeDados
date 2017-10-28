package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {

		int prob = 0;
		int hashKey = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, prob);

		if (super.isFull())
			throw new HashtableOverflowException();

		if (element != null) {

			while (prob != table.length - 1) {
				if (search(element) != null)
					break;
				if (table[hashKey] == null || table[hashKey] instanceof DELETED) {
					table[hashKey] = element;
					elements++;
				} else {
					prob++;
					hashKey = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, prob);
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
			int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probi);
			
			while (probi != table.length - 1) {
				if (table[hash] == null) {
					return -1;
				}
				if (table[hash].equals(element)) {
					return hash;
				} else {
					probi++;
					hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probi);
				}
			}
		}
		
		return -1;
	}

}
