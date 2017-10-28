package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stack1;
	private Stack<T> stack2;

	public QueueUsingStack(int size) {
		stack1 = new StackImpl<T>(size);
		stack2 = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		
		if (element != null) {
			try {
				this.stack1.push(element);
			} catch (StackOverflowException e) {
				throw new QueueOverflowException(); 
			}
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {

		T element = null;

		moveElements(this.stack1, this.stack2);

		try {
			element = this.stack2.pop();
		} catch (StackUnderflowException exception) {
			throw new QueueUnderflowException();
		}

		moveElements(this.stack2, this.stack1);

		return element;

	}

	private void moveElements(Stack<T> inputStack, Stack<T> outputStack) throws QueueUnderflowException {

		while (!inputStack.isEmpty()) {
			try {
				outputStack.push(inputStack.pop());
			} catch (StackUnderflowException exception) {
				throw new QueueUnderflowException();
			} catch (StackOverflowException exception) {
				exception.printStackTrace();
			}
		}

	}

	@Override
	public T head() {

		T head;

		try {
			moveElements(this.stack1, this.stack2);
			head = stack2.top();
			moveElements(this.stack2, this.stack1);
		} catch (QueueUnderflowException exception) {
			return null;
		}

		return head;

	}

	@Override
	public boolean isEmpty() {
		return this.stack1.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.stack1.isFull();
	}

}
