package adt.linkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import adt.queue.QueueDoubleLinkedListImpl;
import adt.queue.QueueOverflowException;
import adt.queue.QueueUnderflowException;

public class StudentQueueTest {

	public QueueDoubleLinkedListImpl<Integer> queue1;
	public QueueDoubleLinkedListImpl<Integer> queue2;
	public QueueDoubleLinkedListImpl<Integer> queue3;

	@Before
	public void setUp() throws QueueOverflowException {
		queue1 = new QueueDoubleLinkedListImpl<Integer>(6);
		queue1.enqueue(1);
		queue1.enqueue(2);
		queue1.enqueue(3);

		queue2 = new QueueDoubleLinkedListImpl<Integer>(2);
		queue2.enqueue(1);
		queue2.enqueue(2);

		queue3 = new QueueDoubleLinkedListImpl<Integer>(6);
	}

	// MÉTODOS DE TESTE
	@Test
	public void testHead() {
		Assert.assertEquals(1, queue1.head().intValue());
		Assert.assertEquals(1, queue2.head().intValue());
		Assert.assertEquals(null, queue3.head());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(!queue1.isEmpty());
		Assert.assertTrue(!queue2.isEmpty());
		Assert.assertTrue(queue3.isEmpty());
	}

	@Test
	public void testIsFull() {
		Assert.assertTrue(!queue1.isFull());
		Assert.assertTrue(queue2.isFull());
		Assert.assertTrue(!queue3.isFull());
	}

	@Test
	public void testEnqueue() {
		try {
			queue1.enqueue(4);
			queue1.enqueue(5);
			queue1.enqueue(6);
			queue3.enqueue(2);
		} catch (QueueOverflowException e) {
			// Não deve chegar aqui
			Assert.fail();
		}
		Assert.assertTrue(!queue3.isEmpty());
		Assert.assertEquals(1, queue1.head().intValue());

	}

	@Test(expected = QueueOverflowException.class)
	public void testEnqueueComErro() throws QueueOverflowException {
		queue2.enqueue(5);
	}

	@Test
	public void testDequeue() {
		try {
			Assert.assertEquals(1, queue1.dequeue().intValue());
			Assert.assertEquals(2, queue1.dequeue().intValue());	 
			Assert.assertEquals(3, queue1.dequeue().intValue());	 
		} catch (QueueUnderflowException e) {
			// Não deve chegar aqui
			Assert.fail();
		}
		
		Assert.assertEquals(null, queue1.head());
	}

	@Test(expected = QueueUnderflowException.class)
	public void testDequeueComErro() throws QueueUnderflowException {
		queue3.dequeue();
	}
}