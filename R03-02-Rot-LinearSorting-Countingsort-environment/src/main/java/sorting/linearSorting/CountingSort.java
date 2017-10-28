package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratÃ©gia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o mÃ¡ximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

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

         // Busca do maior elemento do array para inicializar o vetor
         // auxiliar
         int maximumElement = searchGreaterElement(array, leftIndex, rightIndex);

         // Criacao do vetor auxiliar para contagem da ocorrencia dos
         // elementos
         int contadores[] = new int[maximumElement + 1];

         // Contagem de ocorrencia dos elementos
         for (int i = leftIndex; i <= rightIndex; i++) {
            contadores[array[i]]++;
         }

         // Somatorio do array auxiliar
         for (int i = 1; i < contadores.length; i++) {
            contadores[i] += contadores[i - 1];
         }

         // Array utilizado para a ordenacao
         Integer sorting[] = new Integer[array.length];

         for (int i = rightIndex; i >= leftIndex; i--) {
            contadores[array[i]]--;
            sorting[contadores[array[i]] + leftIndex] = array[i];
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

}
