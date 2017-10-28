package sorting.divideAndConquer.hybridMergesort;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do
 * MergeSort que pode fazer uso do InsertionSort (um algoritmo híbrido) da
 * seguinte forma: o MergeSort é aplicado a entradas maiores a um determinado
 * limite. Caso a entrada tenha tamanho menor ou igual ao limite o algoritmo usa
 * o InsertionSort.
 * 
 * A implementação híbrida deve considerar os seguintes detalhes: - Ter
 * contadores das quantidades de MergeSorts e InsertionSorts aplicados, de forma
 * que essa informação possa ser capturada pelo teste. - A cada chamado do
 * método de sort(T[] array) esses contadores são resetados. E a cada chamada
 * interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e
 * INSERTIONSORT_APPLICATIONS são incrementados. - O InsertionSort utilizado no
 * algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;

	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;

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

			MERGESORT_APPLICATIONS = 0;
			INSERTIONSORT_APPLICATIONS = 0;

			if (rightIndex - leftIndex >= 0 && rightIndex - leftIndex <= 4) {
				insertionSort(array, leftIndex, rightIndex);
			} else {
				mergeSort(array, leftIndex, rightIndex);
			}

		}
	}

	private void mergeSort(T[] array, int leftIndex, int rightIndex) {

		if (leftIndex < rightIndex) {

			MERGESORT_APPLICATIONS++;

			int middle = leftIndex + (rightIndex - leftIndex) / 2;

			mergeSort(array, leftIndex, middle);
			mergeSort(array, middle + 1, rightIndex);

			if (rightIndex - leftIndex <= SIZE_LIMIT) {
				insertionSort(array, leftIndex, rightIndex);
			} else {
				merge(array, leftIndex, middle, rightIndex);
			}

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

	private void insertionSort(T[] array, int leftIndex, int rightIndex) {

		INSERTIONSORT_APPLICATIONS++;

		for (int i = leftIndex; i <= rightIndex; i++) {
			T key = array[i];
			int j = i - 1;
			while ((j >= leftIndex) && (array[j].compareTo(key) > 0)) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}

	}
}
