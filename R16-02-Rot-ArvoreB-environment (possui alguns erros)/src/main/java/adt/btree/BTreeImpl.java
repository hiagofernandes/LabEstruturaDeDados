package adt.btree;

import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		int resposta = -1;
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				resposta = 0;
			} else {
				resposta = 1 + height(node.children.get(0));
				// a idéia é pegar a altura do primeiro filho apenas e somar com
				// 1.
				// Atencao: Se voce usar array vai ter que ajustar o codigo aqui.
			}
		}
		return resposta;

	}

	@Override
	public BNode<T>[] depthLeftOrder() {

		LinkedList<BNode<T>> linked = new LinkedList<>();
		BNode[] tmp = new BNode[linked.size()];
		linked = depthLeftOrder(root, linked);

		return linked.toArray(tmp);
	}

	public LinkedList<BNode<T>> depthLeftOrder(BNode<T> node, LinkedList<BNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);
			for (int i = 0; i < node.getChildren().size(); i++) {
				depthLeftOrder(node.getChildren().get(i), list);
			}
		}
		return list;
	}

	@Override
	public int size() {
		return sizeAux(root);
	}

	private int sizeAux(BNode<T> node) {
		int size = 0;
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				size = node.size();
			} else {
				size = node.size();
				for (int i = 0; i < node.getChildren().size(); i++) {
					size += sizeAux(node.getChildren().get(i));
				}
			}
		}
		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		return searchAux(root, element);
	}

	private BNodePosition<T> searchAux(BNode<T> node, T element) {
		int i = 0;
		BNodePosition<T> nodPosition = new BNodePosition<T>();
		while (i <= node.elements.size() && element.compareTo(node.elements.get(i)) > 0) {
			i++;
		}
		if (i <= node.elements.size() && element.equals(node.elements.get(i))) {
			nodPosition.position = i;
			nodPosition.node = node;
			return nodPosition;
		}
		if (node.isLeaf()) {
			return new BNodePosition<T>();
		}
		return searchAux(node.children.get(i), element);
	}

	@Override
	public void insert(T element) {
		insertAux(root, element);
	}

	private void insertAux(BNode<T> node, T element) {
		if (node.isLeaf()) {
			node.addElement(element);
			if (node.elements.size() > node.getMaxKeys()) {
				node.split();
			}
		} else {
			int position = searchPositionInParent(node.getElements(), element);
			insertAux(node.getChildren().get(position), element);
		}
	}

	private int searchPositionInParent(LinkedList<T> list, T mediana) {
		int i = 0;
		while (i < list.size()) {
			if (list.get(i).compareTo(mediana) > 0) {
				return i;
			}
			i++;
		}
		return list.size();
	}

	private void split(BNode<T> node) {
		T median = node.elements.get(node.elements.size() / 2);
		int position, leftPosition, rightPosition;
		BNode<T> largest = new BNode<>(node.getMaxChildren());
		BNode<T> smaller = new BNode<>(node.getMaxChildren());
		LinkedList<BNode<T>> childrens = new LinkedList<BNode<T>>();

		saveElements(node, median, largest, smaller);

		if (node.parent == null && node.isLeaf()) {
			node.setElements(new LinkedList<T>());
			node.addElement(median);
			node.addChild(0, smaller);
			node.addChild(1, largest);
		}

		else if (node.parent == null && !node.isLeaf()) {
			childrens = node.children;
			node.setElements(new LinkedList<T>());
			node.addElement(median);
			node.setChildren(new LinkedList<BNode<T>>());
			node.addChild(0, smaller);
			node.addChild(1, largest);

			reorganizesChildrens(childrens, smaller, 0, smaller.size() + 1);
			reorganizesChildrens(childrens, largest, largest.size() + 1, childrens.size());
		}

		else if (node.isLeaf()) {
			BNode<T> toPromove = new BNode<>(node.getMaxChildren());
			toPromove.elements.add(median);
			toPromove.parent = node.parent;

			smaller.parent = node.parent;
			largest.parent = node.parent;

			position = searchPositionInParent(toPromove.parent.getElements(), median);
			leftPosition = position;
			rightPosition = position + 1;
			node.parent.children.set(leftPosition, smaller);
			node.parent.children.add(rightPosition, largest);
			toPromove.promote();
		} else {
			childrens = node.children;
			BNode<T> toPromove = new BNode<>(node.getMaxChildren());
			toPromove.elements.add(median);
			toPromove.parent = node.parent;
			smaller.parent = node.parent;
			largest.parent = node.parent;

			position = searchPositionInParent(toPromove.getElements(), median);
			leftPosition = position;
			rightPosition = position + 1;

			node.parent.children.add(leftPosition, smaller);
			node.parent.children.add(rightPosition, largest);

		}
	}

	// Guarda de acordo com a mediana os maiores e menores elementos
	private void saveElements(BNode<T> node, T median, BNode<T> largest, BNode<T> smaller) {
		int i = 0;
		while (i < node.getElements().size()) {
			if (median.compareTo(node.getElementAt(i)) < 0) {
				largest.addElement(node.getElementAt(i));
			}
			if (median.compareTo(node.getElementAt(i)) > 0) {
				smaller.addElement(node.getElementAt(i));
			}
			i++;
		}
	}

	// Reorganiza os filhos de acordo com as posicoes do pai
	private void reorganizesChildrens(LinkedList<BNode<T>> childrens, BNode<T> parent, int first, int last) {
		int position;
		int i = first;
		while (i < last) {
			position = searchPositionInParent(parent.getElements(), childrens.get(i).elements.get(0));
			parent.addChild(position, childrens.get(i));
			i++;
		}
	}

	private void promote(BNode<T> node) {
		int position = searchPositionInParent(node.parent.getElements(), node.getElementAt(0));
		node.parent.getElements().add(position, node.getElementAt(0));
		if (node.parent.size() > node.getMaxKeys()) {
			node.parent.split();
		}
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
