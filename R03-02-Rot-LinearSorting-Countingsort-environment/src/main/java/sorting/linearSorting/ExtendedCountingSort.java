package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		// Validacao
		if (array == null)
			return;
		if (leftIndex > rightIndex)
			return;
		if (leftIndex < 0 || rightIndex < 0)
			return;
		if (array.length < rightIndex)
			return;
		if (array.length == 0)
			return;

		if (leftIndex < rightIndex) {

			// Busca pelo maior elemento do array
			int maximumElement = searchGreaterElement(array, leftIndex, rightIndex);

			// Busca pelo menor elemento do array
			int minimumElement = searchSmallElement(array, leftIndex, rightIndex);

			// Tamanho do array que sera usado para a contagem de ocorrencias
			int tamanho = maximumElement - minimumElement;

			int shift = minimumElement;

			// Criacao do vetor auxiliar para contagem da ocorrencia dos
			// elementos
			int[] contadores = new int[tamanho + 1];
			// Contagem da frenquencia utilizando o shift
			for (int i = leftIndex; i <= rightIndex; i++) {
				contadores[array[i] - shift]++;
			}

			// Soma dos elementos da frequencia
			for (int i = 1; i < contadores.length; i++) {
				contadores[i] += contadores[i - 1];
			}

			// Array utilizado para a ordenacao
			Integer[] sorting = new Integer[array.length];

			for (int i = rightIndex; i >= leftIndex; i--) {
				contadores[array[i] - shift]--;
				sorting[contadores[array[i] - shift] + leftIndex] = array[i];
			}

			// Copia os elementos do array "sorting" para o original.
			for (int i = leftIndex; i <= rightIndex; i++) {
				array[i] = sorting[i];
			}

		}
	}

	// Metodo que busca pelo maior elemento dentro do intervalo passado 
	private int searchGreaterElement(Integer[] array, int leftIndex, int rightIndex) {
		Integer max = array[leftIndex];
		for (int i = leftIndex; i <= rightIndex; i++) {
			if (array[i].compareTo(max) > 0) {
				max = array[i];
			}
		}
		return max.intValue();
	}

	// Metodo que busca pelo menor elemento dentro do intervalo passado 
	private int searchSmallElement(Integer[] array, int leftIndex, int rightIndex) {
		Integer min = array[leftIndex];
		for (int i = leftIndex; i <= rightIndex; i++) {
			if (array[i].compareTo(min) < 0) {
				min = array[i];
			}
		}
		return min.intValue();
	}

}