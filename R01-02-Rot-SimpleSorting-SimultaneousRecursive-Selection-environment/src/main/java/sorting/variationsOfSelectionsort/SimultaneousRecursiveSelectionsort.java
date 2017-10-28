package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * Este algoritmo eh RECURSIVO e aplica o selectionsort na entrada selectionando
 * o menor e o maior elemento a cada iteração e colocando eles nas posições
 * corretas. Nas proximas iterações o espaço de trabalho do algoritmo deve
 * excluir as posiçoes dos elementos das iterações anteriores.
 */
public class SimultaneousRecursiveSelectionsort<T extends Comparable<T>> extends AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {

		if ((leftIndex < rightIndex) && (array != null) && (array.length > leftIndex && array.length > rightIndex)
				&& (leftIndex >= 0)) {

			int minimum = leftIndex, maximum = rightIndex;
			for (int i = leftIndex; i <= rightIndex; i++) {
				if (array[i].compareTo(array[minimum]) < 0) {
					minimum = i;
				}
			}
			if (minimum != leftIndex) {
				Util.swap(array, minimum, leftIndex);
			}
			for (int j = rightIndex; j > leftIndex; j--) {
				if (array[j].compareTo(array[maximum]) > 0) {
					maximum = j;
				}
			}
			if (maximum != rightIndex) {
				Util.swap(array, maximum, rightIndex);
			}
			sort(array, leftIndex + 1, rightIndex - 1);
		}

	}

}
