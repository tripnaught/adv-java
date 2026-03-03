package week6;

public class stacks {
	public static void main(String[] args) {
		System.out.println("\u001b[36m");

		Stack<Integer> myStack = new Stack<>(6); // inefficient use of memory

		myStack.push(10);
		myStack.push(11);
		myStack.push(12);
		myStack.push(13);
		myStack.push(14);
		myStack.push(15);
		myStack.push(16);
		myStack.push(17);

		System.out.println(myStack.pop());
		System.out.println(myStack.pop());
		System.out.println(myStack.pop());
	}
}

class Stack<T> {
	public int maxSize;
	public int currentSize; // last element is at currentSize - 1
	private T[] data;

	public Stack(int maxSize) {
		this.maxSize = maxSize;
		this.currentSize = 0;
		data = (T[]) new Object[maxSize];
	}

	public void push(T item) {
		if (!this.isFull()) {
			data[currentSize] = item;
			currentSize++;
		} else {
			// we need to grow !!!
			Object[] newData = new Object[this.maxSize * 2];
			for (int i = 0; i < maxSize; i++) {
				newData[i] = this.data[i];
			}
			newData[this.currentSize] = item;
			data = (T[]) newData;
			this.maxSize *= 2;
			currentSize++;
		}

	}

	public T pop() {
		T result = null;
		if (currentSize > 0) {
			result = (T) data[currentSize - 1];
			data[currentSize - 1] = null;
			currentSize--;
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public T peek() {
		T result = null;
		if (!this.isEmpty()) {
			result = (T) data[currentSize - 1];
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public boolean isEmpty() {
		return (currentSize == 0) ? true : false;
	}

	public boolean isFull() {
		return (currentSize == maxSize) ? true : false;
	}
}

class LLStack<T> {
	public int currentSize;
	public Node<T> head ;

	public LLStack() {
		this.currentSize = 0;
		this.head = null;
	}

	public void push(T item) {
		Node<T> newNode = new Node<T>(item);
		newNode.next = head;
		this.head = newNode;
		currentSize++;
	}

	public T pop() {
		T result = null;
		if (currentSize > 0) {
			result = this.head.data;
			this.head = this.head.next;
			currentSize--;
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public T peek() {
		T result = null;
		if (currentSize > 0) {
			result = this.head.data;
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public boolean isEmpty() {
		return (currentSize == 0) ? true : false;
	}
}

class Node<T> {
    // data
    public T data;

    // link
    public Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}