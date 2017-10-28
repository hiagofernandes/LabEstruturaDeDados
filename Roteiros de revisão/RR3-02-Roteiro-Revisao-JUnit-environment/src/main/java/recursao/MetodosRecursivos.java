package recursao;

public class MetodosRecursivos {

	public long calcularFatorial(int n) {
		return (n == 1) ? 1 : n * calcularFatorial(n - 1);
	}

	public int calcularFibonacci(int n) {
		return (n < 2) ? n : calcularFibonacci(n - 1) + calcularFibonacci(n - 2);
	}

	public int countNotNull(Object[] array) {
		return countNotNull(0, array);
	}

	private int countNotNull(int index, Object[] array) {

		int count = 0;
		if (index < array.length) {

			return (array[index] == null) ? countNotNull(index + 1, array) : ++count + countNotNull(index + 1, array);
		}
		return count;

	}

	public long potenciaDe2(int expoente) {
		return (expoente == 0) ? 1 : 2 * potenciaDe2(expoente - 1);
	}

	public double progressaoAritmetica(double termoInicial, double razao, int n) {
		return (n == 1) ? termoInicial : razao + progressaoAritmetica(termoInicial, razao, n - 1);
	}

	public double progressaoGeometrica(double termoInicial, double razao, int n) {
		return (n == 1) ? termoInicial : razao * progressaoGeometrica(termoInicial, razao, n - 1);
	}
}
