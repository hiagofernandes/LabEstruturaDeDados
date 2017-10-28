package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

		if ((leftIndex < rightIndex) && (array != null) && (array.length > leftIndex && array.length > rightIndex)
				&& (leftIndex >= 0)) {

			for (int i = leftIndex; i <= rightIndex; i++) {
				int minimum = i;
				for (int j = i + 1; j <= rightIndex; j++) {
					if (array[j].compareTo(array[minimum]) < 0) {
						minimum = j;
					}
				}
				if (minimum != i) {
					Util.swap(array, minimum, i);
				}
			}

		}

	}
}
