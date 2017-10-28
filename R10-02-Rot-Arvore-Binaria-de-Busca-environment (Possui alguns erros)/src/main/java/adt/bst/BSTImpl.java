package adt.bst;

import java.util.ArrayList;
import java.util.List;

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
			int direita = auxHeight((BSTNode<T>) node.getRight(), soma + 1);
			int esquerda = auxHeight((BSTNode<T>) node.getLeft(), soma + 1);

			soma = Math.max(direita, esquerda);
		}

		return soma;
	}

	@Override
	public BSTNode<T> search(T element) {

		BSTNode<T> nodeAux = this.root;

		while (nodeAux.getData() != null && !nodeAux.getData().equals(element)) {

			if (element.compareTo(nodeAux.getData()) < 0) {
				nodeAux = (BSTNode<T>) nodeAux.getLeft();
			} else {
				nodeAux = (BSTNode<T>) nodeAux.getRight();
			}

		}

		return nodeAux;

	}

	@Override
	public void insert(T element) {

		insertAux(null, this.root, element);

	}

	private void insertAux(BSTNode<T> parent, BSTNode<T> node, T element) {

		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) < 0) {
				insertAux(node, (BSTNode<T>) node.getLeft(), element);
			} else {
				insertAux(node, (BSTNode<T>) node.getRight(), element);
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
			// Primeiro caso: O no possui grau 0
			if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
				node.setData(null);
			}
			// Segundo caso: o no possui grau 1
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
				// Terceiro caso: o no possui grau 2
			} else {
				T suc = sucessor(node.getData()).getData();
				remove(suc);
				node.setData(suc);
			}
		}
	}

	@Override
	public T[] preOrder() {

		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();
		preOrderAux(this.root, list);
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i);
		}

		return result;

	}

	private void visit(List<T> list, BSTNode<T> node) {
		list.add(node.getData());
	}

	private void preOrderAux(BSTNode<T> node, List<T> list) {

		if (node.getData() != null) {
			visit(list, node);
			preOrderAux((BSTNode<T>) node.getLeft(), list);
			preOrderAux((BSTNode<T>) node.getRight(), list);
		}

	}

	@Override
	public T[] order() {

		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();
		orderAux(this.root, list);
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i);
		}

		return result;

	}

	private void orderAux(BSTNode<T> node, List<T> list) {

		if (node.getData() != null) {
			orderAux((BSTNode<T>) node.getLeft(), list);
			visit(list, node);
			orderAux((BSTNode<T>) node.getRight(), list);
		}

	}

	@Override
	public T[] postOrder() {

		T[] result = (T[]) new Comparable[size()];
		List<T> list = new ArrayList<T>();
		postOrderAux(this.root, list);
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i);
		}
		return result;

	}

	private void postOrderAux(BSTNode<T> node, List<T> list) {

		if (node.getData() != null) {
			orderAux((BSTNode<T>) node.getLeft(), list);
			orderAux((BSTNode<T>) node.getRight(), list);
			visit(list, node);
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
