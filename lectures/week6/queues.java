package week6;

public class queues {
	public static void main(String[] args) {
		Queue<Integer> q = new Queue<Integer>(5);

		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);

		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
	}
}


class Queue<T> {
	private Object[] data;
	private int size;
	private int head;
	private int tail;

	public Queue(int capacity) {
		data = new Object[capacity];
		size = 0;
		head = 0;
		tail = 0;
	}

	public void enqueue(T item) {
		if (size == data.length) {
			// queue is too small -- resize the array
			Object[] newData = new Object[data.length * 2];
			for (int i = 0; i < size; i++) {
				newData[i] = data[(head + i) % data.length];
			}
			data = newData;
			head = 0;
			tail = size;
		}
		this.data[this.tail] = item;
		this.tail = (this.tail + 1) % data.length;
		this.size++;
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
