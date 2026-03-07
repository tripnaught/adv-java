package week6;

public class queues {
	
}

class Queue<T> {
	private Object[] data;
	private int size;
	private int head;
	private int tail;

	public Queue(int size) {
		this.size = size;
		data = new Object[size];
		size = 0;
		head = 0;
		tail = 0;
	}

	public void enqueue(T item) {
		if (size == data.length) {
			// grow

			// insert the data unwrapped (???)
		} else {
			this.data[this.tail] = item;
			this.tail = (this.tail + 1) % data.length;
			this.size++;
		}
	}

	public T dequeue() {
		T result = null;
		if (size == 0) {
			System.err.println("error: removing from empty queue!");
		} else {
			result = (T) this.data[this.head];
			this.data[this.head] = null;
			this.head = (this.head + 1) % data.length;
			this.size--;
		}
		return result;
	}
}
