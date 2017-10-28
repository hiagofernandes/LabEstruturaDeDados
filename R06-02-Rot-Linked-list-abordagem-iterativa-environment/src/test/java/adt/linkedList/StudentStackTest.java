package adt.linkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import adt.stack.StackDoubleLinkedListImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class StudentStackTest {

	public StackDoubleLinkedListImpl<Integer> stack1;
	public StackDoubleLinkedListImpl<Integer> stack2;
	public StackDoubleLinkedListImpl<Integer> stack3;

	@Before
	public void setUp() throws StackOverflowException{
		stack1 = new StackDoubleLinkedListImpl<Integer>(6);
		stack1.push(1);
		stack1.push(2);
		stack1.push(3);
		
		stack2 = new StackDoubleLinkedListImpl<Integer>(2);
		stack2.push(1);
		stack2.push(2);
		
		stack3 = new StackDoubleLinkedListImpl<Integer>(6);
	}
	
	//MÉTODOS DE TESTE
	@Test
	public void testTop() {
		Assert.assertEquals(3, stack1.top().intValue());
		Assert.assertEquals(2, stack2.top().intValue());
		Assert.assertEquals(null, stack3.top());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(!stack1.isEmpty());
		Assert.assertTrue(!stack2.isEmpty());
		Assert.assertTrue(stack3.isEmpty());
	}

	@Test
	public void testIsFull() {
		Assert.assertTrue(!stack1.isFull());
		Assert.assertTrue(stack2.isFull());
		Assert.assertTrue(!stack3.isFull());
	}

	@Test
	public void testPush() {
		try {
			stack1.push(4);
			stack1.push(5);
			stack1.push(6);
			stack3.push(1);
		} catch (StackOverflowException e) {
			// Não deve chegar aqui
			Assert.fail();
		}
		Assert.assertEquals(6, stack1.top().intValue());
		Assert.assertEquals(1, stack3.top().intValue());
	}
	
	@Test(expected=StackOverflowException.class)
	public void testPushComErro() throws StackOverflowException {
		stack2.push(5);
	}

	@Test
	public void testPop() {
		try {
			Assert.assertEquals(3, stack1.pop().intValue());
			Assert.assertEquals(2, stack1.pop().intValue());
			Assert.assertEquals(1, stack1.pop().intValue());
			
			Assert.assertEquals(2, stack2.pop().intValue());
			
			Assert.assertEquals(1, stack2.top().intValue());
		} catch (StackUnderflowException e) {
			// Não deve chegar aqui
			Assert.fail(); 
		}
	}
	
	@Test(expected=StackUnderflowException.class)
	public void testPopComErro() throws StackUnderflowException {
		stack3.pop();
	}
}