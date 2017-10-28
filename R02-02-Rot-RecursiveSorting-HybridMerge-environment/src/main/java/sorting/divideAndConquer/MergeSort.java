package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

		if (leftIndex > rightIndex)
			return;
		if (array == null)
			return;
		if (leftIndex < 0 || rightIndex < 0)
			return;
		if (array.length < rightIndex)
			return;
		if (array.length == 0)
			return;

		if (leftIndex < rightIndex) {

			int middle = leftIndex + (rightIndex - leftIndex) / 2;

			sort(array, leftIndex, middle);
			sort(array, middle + 1, rightIndex);
			merge(array, leftIndex, middle, rightIndex);

		}

	}

	private void merge(T[] array, int leftIndex, int middle, int rightIndex) {

		T[] aux = Arrays.copyOf(array, array.length);

		int i = leftIndex;
		int j = middle + 1;
		int k = leftIndex;

		while (i <= middle && j <= rightIndex) {

			if (aux[i].compareTo(aux[j]) < 0) {
				array[k] = aux[i];
				i++;
			} else {
				array[k] = aux[j];
				j++;
			}
			k++;
		}

		while (i <= middle) {
			array[k] = aux[i];
			i++;
			k++;
		}

		while (j <= rightIndex) {
			array[k] = aux[j];
			j++;
			k++;
		}

	}
}
