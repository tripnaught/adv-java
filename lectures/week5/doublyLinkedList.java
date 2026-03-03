package week5;

// append, prepend, insertAfter, remove, search, print, printReverse, sort, isEmpty, getLength

public class doublyLinkedList {
	public static void main(String[] args) {
		DLL<Integer> myList = new DLL<Integer>();
        myList.append(2);
        myList.append(3);
        myList.append(5);
        myList.print();
        myList.printReverse();
	}
}

class DLL<T> {
	public Node<T> head;
	public Node<T> tail;
	public int length;

	public DLL() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

	public void append(T data) {
		if (isEmpty()) {
			this.head = new Node<T>(data);
			this.tail = head;
			length++;
		} else {
			Node<T> newNode = new Node<T>(data);
			newNode.prev = tail;
			tail.next = newNode;
			length++;
		}
	}

	public void print() {
        Node<T> curr = head;
        while (curr != null) {
            System.out.print(curr.data);
            System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.println("END");
    }

    public void printReverse() {
        Node<T> curr = tail;
        while (curr != null) {
            System.out.print(curr.data);
            System.out.print(" <- ");
            curr = curr.prev;
        }
        System.out.println("END");
    }

	public boolean isEmpty() {
		return (length == 0) ? true : false;
	}

	public int getLength() {
		return length;
	}
}

class Node<T> {
    // data
    public T data;

    // links
    public Node<T> next;
	public Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.next = null;
		this.prev = null;
    }
}
