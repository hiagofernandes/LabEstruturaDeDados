package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		if ((node == null) || (node.isEmpty()))
			return;

		if (node.getParent() == null) {
			root = node;
			return;
		}

		if (node.getParent().equals(root)) {
			if (node.getParent().getLeft().equals(node)) {
				zigRight(node);
			} else {
				zigLeft(node);
			}

			root = node;

		} else if (node.getParent().getLeft().equals(node)
				&& node.getParent().getParent().getLeft().equals(node.getParent())) {

			zigZigRight(node);
			splay(node);
		} else if (node.getParent().getRight().equals(node)
				&& node.getParent().getParent().getRight().equals(node.getParent())) {

			zigZigLeft(node);
			splay(node);
		} else if (node.getParent().getLeft().equals(node)
				&& node.getParent().getParent().getRight().equals(node.getParent())) {

			zigZagRightLeft(node);
			splay(node);
		} else if (node.getParent().getRight().equals(node)
				&& node.getParent().getParent().getLeft().equals(node.getParent())) {

			zigZagLeftRight(node);
			splay(node);
		}
	}

	@Override
	public void insert(T element) {
		if (element == null)
			return;

		if (isEmpty()) {
			BSTNode<T> leftNil = new BSTNode<>();
			BSTNode<T> rightNil = new BSTNode<>();

			root.setData(element);

			root.setLeft(leftNil);
			leftNil.setParent(root);

			root.setRight(rightNil);
			rightNil.setParent(root);

		} else {
			insert(element, root);
		}
	}

	private void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			BSTNode<T> leftNil = new BSTNode<>();
			BSTNode<T> rightNil = new BSTNode<>();

			node.setData(element);

			node.setLeft(leftNil);
			leftNil.setParent(node);

			node.setRight(rightNil);
			rightNil.setParent(node);

			splay(node);

		} else if (element.compareTo(node.getData()) > 0) {
			insert(element, (BSTNode<T>) node.getRight());
		} else {
			insert(element, (BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);

		if (!node.isEmpty()) {
			splay(node);
		} else {
			splay((BSTNode<T>) node.getParent());
		}

		return node;
	}

	@Override
	public void remove(T element) {

		if ((element == null) || (isEmpty()))
			return;

		BSTNode<T> node = super.search(element, root);
		BSTNode<T> nodeParent = (BSTNode<T>) node.getParent();

		if (node.isEmpty()) {
			splay(nodeParent);
		} else {
			super.remove(element);
			splay(nodeParent);
		}
	}

	private void zigLeft(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent());
	}

	private void zigRight(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent());
	}

	private void zigZigLeft(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent().getParent());
		Util.leftRotation((BSTNode<T>) node.getParent());
	}

	private void zigZigRight(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent().getParent());
		Util.rightRotation((BSTNode<T>) node.getParent());
	}

	private void zigZagLeftRight(BSTNode<T> node) {
		Util.leftRotation((BSTNode<T>) node.getParent());
		Util.rightRotation((BSTNode<T>) node.getParent());
	}

	private void zigZagRightLeft(BSTNode<T> node) {
		Util.rightRotation((BSTNode<T>) node.getParent());
		Util.leftRotation((BSTNode<T>) node.getParent());
	}

}
