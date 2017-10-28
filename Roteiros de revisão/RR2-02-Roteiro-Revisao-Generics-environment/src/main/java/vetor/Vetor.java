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
	}

	public void setComparadorMaximo(Comparator comparadorMaximo) {
		this.comparadorMaximo = comparadorMaximo;
	}

	public void setComparadorMinimo(Comparator comparadorMinimo) {
		this.comparadorMinimo = comparadorMinimo;
	}

	// Insere um objeto no vetor
	public void inserir(T object) {
		++indice;
		this.arrayInterno[indice] = object;
	}

	// Remove um objeto do vetor
	public T remover(T object) {
		T obj = null;
		int indication = locatedIndex(object);
		if (indication != -1) {
			obj = this.arrayInterno[indication];
			this.arrayInterno[indication] = null;
		}
		return obj;
	}

	// Procura um elemento no vetor
	public T procurar(T object) {
		for (int i = 0; i < arrayInterno.length; i++) {
			if (arrayInterno[i].equals(object)) {
				return arrayInterno[i];
			}
		}
		return null;
	}

	private int locatedIndex(T object) {
		for (int i = 0; i < arrayInterno.length; i++) {
			if (object.equals(this.arrayInterno[i])) {
				return i;
			}
		}
		return -1;
	}

	// Diz se o vetor está vazio
	public boolean isVazio() {
		return this.indice == -1;
	}

	// Diz se o vetor está cheio
	public boolean isCheio() {
		return this.indice == this.tamanho;
	}

}
