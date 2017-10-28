package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return auxHeight(root, -1);
	}

	private int auxHeight(BSTNode<T> node, int soma) {
		if (!node.isEmpty()) {
			int right = auxHeight((BSTNode<T>) node.getRight(), soma + 1);
			int left = auxHeight((BSTNode<T>) node.getLeft(), soma + 1);

			soma = Math.max(right, left);
		}

		return soma;

	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> aux = root;

		while ((aux.getData() != null) && !(aux.getData().equals(element))) {
			if (element.compareTo(aux.getData()) < 0) {
				aux = (BSTNode<T>) aux.getLeft();
			} else if (element.compareTo(aux.getData()) > 0) {
				aux = (BSTNode<T>) aux.getRight();
			}
		}

		return aux;
	}

	@Override
	public void insert(T element) {
		auxInsert(root, (BSTNode<T>) root.getParent(), element);
	}

	private void auxInsert(BSTNode<T> node, BSTNode<T> parent, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);

		} else {
			if (element.compareTo(node.getData()) < 0) {
				auxInsert((BSTNode<T>) node.getLeft(), node, element);
			} else {
				auxInsert((BSTNode<T>) node.getRight(), node, element);
			}
		}

	}

	@Override
	public BSTNode<T> maximum() {
		if (root.isEmpty()) {
			return null;
		}
		return auxMaximum(root);
	}

	private BSTNode<T> auxMaximum(BSTNode<T> node) {
		if (!node.getRight().isEmpty()) {
			return auxMaximum((BSTNode<T>) node.getRight());
		}

		return node;
	}

	@Override
	public BSTNode<T> minimum() {
		if (root.isEmpty()) {
			return null;
		}
		return auxMinimum(root);

	}

	private BSTNode<T> auxMinimum(BSTNode<T> node) {
		if (!node.getLeft().isEmpty()) {
			return auxMinimum((BSTNode<T>) node.getLeft());
		}
		return node;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;
		} else if (!node.getRight().isEmpty()) {
			return auxMinimum((BSTNode<T>) node.getRight());
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			while (parent != null && parent.getRight().equals(node)) {
				node = parent;
				parent = (BSTNode<T>) node.getParent();
			}

			return parent;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;
		} else if (!node.getLeft().isEmpty()) {
			return auxMaximum((BSTNode<T>) node.getLeft());
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			while (parent != null && parent.getLeft().equals(node)) {
				node = parent;
				parent = (BSTNode<T>) node.getParent();
			}

			return parent;
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return;
		} else {
			// Grau 0
			if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
				node.setData(null);
			}
			// Grau 1
			else if (node.getLeft().isEmpty() || node.getRight().isEmpty()) {
				if (node.getParent() != null) {
					if (!node.getParent().getLeft().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					if (node.getLeft().isEmpty()) {
						root = (BSTNode<T>) node.getRight();
					} else {
						root = (BSTNode<T>) node.getLeft();
					}
					root.setParent(null);
				}
				// Grau 2
			} else {
				T suc = sucessor(node.getData()).getData();
				remove(suc);
				node.setData(suc);
			}
		}
	}

	@Override
	public T[] preOrder() {
		T[] resultado = (T[]) new Comparable[size()];
		ArrayList<T> list = new ArrayList<T>();

		auxPreOrder(root, resultado, list);

		for (int i = 0; i < resultado.length; i++) {
			resultado[i] = list.get(i);
		}

		return resultado;
	}

	private void visit(BSTNode<T> node, ArrayList<T> list) {
		list.add(node.getData());
	}

	private void auxPreOrder(BSTNode<T> node, T[] resultado, ArrayList<T> list) {
		if (node.getData() != null) {
			visit(node, list);
			auxPreOrder((BSTNode<T>) node.getLeft(), resultado, list);
			auxPreOrder((BSTNode<T>) node.getRight(), resultado, list);
		}
	}

	@Override
	public T[] order() {
		T[] resultado = (T[]) new Comparable[size()];
		ArrayList<T> list = new ArrayList<T>();

		auxOrder(root, resultado, list);

		for (int i = 0; i < resultado.length; i++) {
			resultado[i] = list.get(i);
		}

		return resultado;
	}

	private void auxOrder(BSTNode<T> node, T[] resultado, ArrayList<T> list) {
		if (node.getData() != null) {
			auxOrder((BSTNode<T>) node.getLeft(), resultado, list);
			visit(node, list);
			auxOrder((BSTNode<T>) node.getRight(), resultado, list);
		}
	}

	@Override
	public T[] postOrder() {
		T[] resultado = (T[]) new Comparable[size()];
		ArrayList<T> list = new ArrayList<T>();

		auxPostOrder(root, resultado, list);

		for (int i = 0; i < resultado.length; i++) {
			resultado[i] = list.get(i);
		}

		return resultado;
	}

	private void auxPostOrder(BSTNode<T> node, T[] resultado, ArrayList<T> list) {
		if (node.getData() != null) {
			auxPostOrder((BSTNode<T>) node.getLeft(), resultado, list);
			auxPostOrder((BSTNode<T>) node.getRight(), resultado, list);
			visit(node, list);

		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
