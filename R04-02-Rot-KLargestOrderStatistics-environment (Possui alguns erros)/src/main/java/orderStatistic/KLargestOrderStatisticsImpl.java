package orderStatistic;

import util.Util;

/**
 * Uma implementacao da interface KLargest que usa estatisticas de ordem para
 * retornar um array com os k maiores elementos de um conjunto de dados/array.
 * 
 * A k-esima estatistica de ordem de um conjunto de dados eh o k-esimo menor
 * elemento desse conjunto. Por exemplo, considere o array [4,8,6,9,12,1]. A 3a
 * estatistica de ordem eh 6, a 6a estatistica de ordem eh 12.
 * 
 * Note que, para selecionar os k maiores elementos, pode-se pegar todas as
 * estatisticas de ordem maiores que k.
 * 
 * Requisitos do algoritmo: - DEVE ser in-place. Voce pode modificar o array
 * original - Voce DEVE usar alguma ideia de algoritmo de ordenacao visto em
 * sala para calcular estatisticas de ordem.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class KLargestOrderStatisticsImpl<T extends Comparable<T>> implements KLargest<T> {

	@Override
	public T[] getKLargest(T[] array, int k) {

		if (k > array.length)
			return null;

		T[] larger = (T[]) new Comparable[array.length - k];

		T element = orderStatistics(array, k);

		int index = 0;

		for (int i = k; i < array.length; i++) {
			if (array[i].compareTo(element) > 0)
				larger[index++] = array[i];
		}

		return larger;

	}

	/**
	 * Metodo que retorna a k-esima estatistica de ordem de um array, usando a
	 * ideia de algum algoritmo de ordenacao visto em sala. Caso nao exista a
	 * k-esima estatistica de ordem, entao retorna null.
	 * 
	 * Obs: o valor de k deve ser entre 1 e N.
	 * 
	 * @param array
	 * @param k
	 * @return
	 */
	public T orderStatistics(T[] array, int k) {

		// Validacao
		if (k > array.length)
			return null;

		// Aplicacao da logica do selection sort para encontrar o elemento da
		// k-esima estatistica
		for (int i = 0; i < k; i++) {
			int minimum = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[minimum]) <= 0) {
					minimum = j;
				}
			}
			if (minimum != i) {
				Util.swap(array, minimum, i);
			}
		}
		return array[k - 1];

	}
}
