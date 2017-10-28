package adt.bst.extended;

import java.util.ArrayList;
import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

   private Comparator<T> comparator;

   public SortComparatorBSTImpl(Comparator<T> comparator) {
      super();
      this.comparator = comparator;
   }

   @Override
   public void insert(T element) {
      auxInsert(super.root, (BSTNode<T>) super.root.getParent(), element);
   }

   private void auxInsert(BSTNode<T> node, BSTNode<T> parent, T element) {
      if (node.isEmpty()) {
         node.setData(element);
         node.setLeft(new BSTNode<T>());
         node.setRight(new BSTNode<T>());
         node.setParent(parent);

      } else {
         if (getComparator().compare(element, node.getData()) < 0) {
            auxInsert((BSTNode<T>) node.getLeft(), node, element);
         } else {
            auxInsert((BSTNode<T>) node.getRight(), node, element);
         }
      }

   }

   @Override
   public BSTNode<T> search(T element) {

      BSTNode<T> aux = super.root;

      while ((aux.getData() != null) && !(aux.getData().equals(element))) {
         if (getComparator().compare(element, aux.getData()) < 0) {
            aux = (BSTNode<T>) aux.getLeft();
         } else if (getComparator().compare(element, aux.getData()) > 0) {
            aux = (BSTNode<T>) aux.getRight();
         }
      }

      return aux;

   }

   @Override
   public T[] sort(T[] array) {

      if (array == null)
         return null;

      if (super.root != null) {

         T elements[] = order();
         for (int i = 0; i < elements.length; i++) {
            super.remove(elements[i]);
         }

      }

      for (int i = 0; i < array.length; i++) {
         if (array[i] != null)
            insert(array[i]);
      }

      return order();

   }

   @Override
   public T[] reverseOrder() {
      T[] resultado = (T[]) new Comparable[size()];
      ArrayList<T> list = new ArrayList<T>();

      auxReverseOrder(super.root, resultado, list);

      for (int i = 0; i < resultado.length; i++) {
         resultado[i] = list.get(i);
      }

      return resultado;

   }

   private void auxReverseOrder(BSTNode<T> node, T[] resultado, ArrayList<T> list) {

      if (node.getData() != null) {
         auxReverseOrder((BSTNode<T>) node.getRight(), resultado, list);
         visit(node, list);
         auxReverseOrder((BSTNode<T>) node.getLeft(), resultado, list);
      }

   }

   private void visit(BSTNode<T> node, ArrayList<T> list) {
      list.add(node.getData());
   }

   public Comparator<T> getComparator() {
      return comparator;
   }

   public void setComparator(Comparator<T> comparator) {
      this.comparator = comparator;
   }

}
