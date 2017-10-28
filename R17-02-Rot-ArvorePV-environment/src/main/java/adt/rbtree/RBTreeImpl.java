package adt.rbtree;

import java.util.ArrayList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeightLeft((RBNode<T>) root);
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (node.isEmpty()) {
			return true;
		} else if ((node.getColour() == Colour.RED) && ((RBNode<T>) node.getParent()).getColour() == Colour.RED) {
			return false;
		} else {
			return (verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& verifyChildrenOfRedNodes((RBNode<T>) node.getRight()));
		}
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		if (verifyBlackHeight((RBNode<T>) root)) {
			return true;
		}

		throw new RuntimeException();
	}

	private boolean verifyBlackHeight(RBNode<T> node) {
		if (node.isEmpty()) {
			return true;
		}

		if (blackHeightLeft(node) != blackHeightRight(node)) {
			return false;
		}

		return (verifyBlackHeight((RBNode<T>) node.getLeft())) && (verifyBlackHeight((RBNode<T>) node.getRight()));
	}

	private int blackHeightLeft(RBNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		} else if (node.getColour() == Colour.RED) {
			return blackHeightLeft((RBNode<T>) node.getLeft());
		} else {
			return 1 + blackHeightLeft((RBNode<T>) node.getLeft());
		}
	}

	private int blackHeightRight(RBNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		} else if (node.getColour() == Colour.RED) {
			return blackHeightRight((RBNode<T>) node.getRight());
		} else {
			return 1 + blackHeightRight((RBNode<T>) node.getRight());
		}
	}

	@Override
	public void insert(T value) {
		BSTNode<T> bstNode = search(value);

		if (bstNode.isEmpty()) {
			super.insert(value);

			RBNode<T> node = (RBNode<T>) search(value);

			RBNode<T> leftNil = new RBNode<>();
			RBNode<T> rightNil = new RBNode<>();

			leftNil.setParent(node);
			rightNil.setParent(node);

			node.setLeft(leftNil);
			node.setRight(rightNil);

			node.setColour(Colour.RED);

			fixUpCase1(node);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RBNode<T>[] rbPreOrder() {
		ArrayList<RBNode<T>> array = new ArrayList<>();

		rbPreOrder(array, (RBNode<T>) root);

		RBNode<T>[] rbArray = new RBNode[array.size()];

		for (int i = 0; i < array.size(); i++) {
			rbArray[i] = array.get(i);
		}

		return rbArray;
	}

	private void rbPreOrder(ArrayList<RBNode<T>> array, RBNode<T> node) {
		if (node.isEmpty()) {
			return;
		}

		array.add(node);

		rbPreOrder(array, (RBNode<T>) node.getLeft());
		rbPreOrder(array, (RBNode<T>) node.getRight());
	}

	protected void fixUpCase1(RBNode<T> node) {
		if (((RBNode<T>) root).getColour() == Colour.RED) {
			((RBNode<T>) root).setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!(((RBNode<T>) node.getParent()).getColour() == Colour.BLACK)) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> father = (RBNode<T>) node.getParent();
		RBNode<T> grandfather = (RBNode<T>) father.getParent();
		RBNode<T> uncle = getUncle(node, father, grandfather);

		if (uncle.getColour() == Colour.RED) {
			father.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);

			grandfather.setColour(Colour.RED);
			fixUpCase1(grandfather);
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;

		if (node.getParent().getRight().equals(node)
				&& node.getParent().getParent().getLeft().equals(node.getParent())) {

			Util.leftRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		} else if (node.getParent().getLeft().equals(node)
				&& node.getParent().getParent().getRight().equals(node.getParent())) {

			Util.rightRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}

		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> father = (RBNode<T>) node.getParent();
		RBNode<T> grandfather = (RBNode<T>) father.getParent();

		father.setColour(Colour.BLACK);
		grandfather.setColour(Colour.RED);

		if (node.getParent().getLeft().equals(node)) {
			Util.rightRotation(grandfather);
		} else {
			Util.leftRotation(grandfather);
		}

		if (father.getParent() == null) {
			root = father;
		}
	}

	private RBNode<T> getUncle(RBNode<T> node, RBNode<T> father, RBNode<T> grandfather) {
		RBNode<T> uncle = null;

		if (grandfather.getLeft().equals(father)) {
			uncle = (RBNode<T>) grandfather.getRight();
		} else {
			uncle = (RBNode<T>) grandfather.getLeft();
		}

		return uncle;
	}
}
