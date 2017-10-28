package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {

		int result = number;

		if (result % 2 == 0)
			result++;

		while (!Util.isPrime(result)) {
			result += 2;
		}

		return result;

	}

	@Override
	public void insert(T element) {

		if (element != null && search(element) == null) {

			int hashKey = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			if (table[hashKey] == null) {
				table[hashKey] = new LinkedList<T>();
			}
			if (!((LinkedList<T>) table[hashKey]).isEmpty()) {
				COLLISIONS++;
			}
			((LinkedList<T>) table[hashKey]).add(element);
			elements++;

		}

	}

	@Override
	public void remove(T element) {

		if (element != null && search(element) != null) {
			int hashKey = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			((LinkedList<T>) table[hashKey]).remove(element);
			elements--;
		}

	}

	@Override
	public T search(T element) {

		T result = null;

		if (element != null) {
			int hashKey = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			if (table[hashKey] != null && ((LinkedList<T>) table[hashKey]).contains(element)) {
				result = element;
			}
		}
		return result;

	}

	@Override
	public int indexOf(T element) {

		int index = -1;

		int hashKey = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
		if (table[hashKey] != null && ((LinkedList<T>) table[hashKey]).contains(element)) {
			index = hashKey;
		}

		return index;
	}

}
