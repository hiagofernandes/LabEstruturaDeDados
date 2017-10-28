package vetor;

import java.util.Comparator;

/**
 * Implementação de um vetor de objetos simples para exercitar os conceitos de
 * Generics.
 * 
 * @author Adalberto
 *
 */
public class Vetor<T extends Comparable<T>> {

	// O array interno onde os objetos manipulados são guardados
	private T[] arrayInterno;

	// O tamanho que o array interno terá
	private int tamanho;

	// Indice que guarda a proxima posição vazia do array interno
	private int indice;

	// O Comparators a serem utilizados
	private Comparator comparadorMaximo;
	private Comparator comparadorMinimo;

	public Vetor(int tamanho) {
		super();
		this.tamanho = tamanho;
		this.indice = -1;
		this.arrayInterno = (T[]) new Comparable[tamanho];
	}

	public void setComparadorMaximo(Comparator comparadorMaximo) {
		this.comparadorMaximo = comparadorMaximo;
	}

	public void setComparadorMinimo(Comparator comparadorMinimo) {
		this.comparadorMinimo = comparadorMinimo;
	}

	// Insere um objeto no vetor
	public void inserir(T o) {

		++indice;
		this.arrayInterno[indice] = o;

	}

	// Remove um objeto do vetor
	public T remover(T o) {
		T result = null;
		int index = indexElement(o);
		if (index != -1) {
			result = this.arrayInterno[index];
			shift(index);
			--this.indice;
		}
		return result;

	}

	private void shift(int index) {
		for (int i = index; i < this.indice; i++) {
			this.arrayInterno[i] = this.arrayInterno[i + 1];
		}
	}

	private int indexElement(T o) {
		int index = -1;
		for (int i = 0; i <= indice; i++) {
			if (this.arrayInterno[i].equals(o)) {
				index = i;
			}
		}
		return index;
	}

	// Procura um elemento no vetor
	public T procurar(T o) {
		T result = null;
		boolean found = false;
		int index = 0;
		while (index < this.indice && !(found)) {
			if (this.arrayInterno[index].equals(o)) {
				result = this.arrayInterno[index];
			}
		}
		return result;
	}

	public T maximo() {
		T result = null;
		if (!isVazio()) {
			result = this.arrayInterno[0];
			for (int i = 0; i <= this.indice; i++) {
				if (comparadorMaximo.compare(result, arrayInterno[i]) > 0) {
					result = this.arrayInterno[i];
				}
			}
		}
		return result;

	}

	public T minimo() {
		T result = null;
		if (!isVazio()) {
			result = this.arrayInterno[0];
			for (int i = 0; i <= this.indice; i++) {
				if (comparadorMinimo.compare(result, arrayInterno[i]) < 0) {
					result = this.arrayInterno[i];
				}
			}
		}
		return result;
	}

	// Diz se o vetor está vazio
	public boolean isVazio() {
		return this.indice == -1;
	}

	// Diz se o vetor está cheio
	public boolean isCheio() {
		return this.indice == (this.arrayInterno.length - 1);
	}

	class ComparadorMaximo<T extends Comparable<T>> implements Comparator<Aluno> {

		@Override
		public int compare(Aluno o1, Aluno o2) {
			return Double.compare(o1.getMedia(), o2.getMedia());
		}

	}

	class ComparadorMinimo<T extends Comparable<T>> implements Comparator<Aluno> {

		@Override
		public int compare(Aluno o1, Aluno o2) {
			return Double.compare(o1.getMedia(), o2.getMedia());
		}

	}

}
