package week5;

public class linkedList {
    static Node<Integer> headNode = new Node<Integer>(-1); // this initial reference will never change; it's always the
                                                           // head of the list
    static Node<Integer> currentNode = headNode;

    public static void main(String[] args) {
        Node<Integer> newNode = new Node<Integer>(8);
        Node<Integer> newNode2 = new Node<Integer>(3);
        Node<Integer> newNode3 = new Node<Integer>(8);

        newNode.next = newNode2;
        newNode2.next = newNode3;

    }

    static void append(int newData) {
        Node<Integer> newNode = new Node<Integer>(newData);

        if (headNode == null) {
            headNode = newNode;
            return;
        }

        Node<Integer> curr = headNode; // it's not last yet!
        while (curr.next != null) {
            curr = curr.next;
        }
        // now we've reached the last node
        curr.next = newNode;

    }

    static boolean search(int target) {
        Node<Integer> curr = headNode;
        while (curr != null) {
            if (curr.data == target) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    static boolean remove(int target) {
        // case 1: linked list is empty
        if (headNode == null) {
            return false;
        }

        // case 2: need to remove the head of the linked list
        if (headNode.data == target) {
            headNode.next = headNode.next.next;
            return true;
        }

        // case 3: need to remove anywhere else
        Node<Integer> curr = headNode;
        while (curr.next != null) {
            if (curr.next.data == target) {
                curr.next = curr.next.next; // bunnyhop
                return true;
            }
            curr = curr.next; // advance
        }
        return false;
    }
}

class SinglyLinkedList<T> {
    public Node<T> head;
    public int length;

    public SinglyLinkedList() {
        this.head = null;
        this.length = 0;
    }

    public void append(T data) {
        if (head == null) {
            head = new Node<T>(data);
            length = 1;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node<T>(data);
            length++;
        }
    }

    public void prepend(T data) {
        if (head == null) {
            head = new Node<T>(data);
            length = 1;
        } else {
            Node<T> newHead = new Node<T>(data);
            newHead.next = head;
            this.head = newHead;
            length++;
        }
    }

    public void insertAfter() {
        // ...
    }

    public void remove() {
        // ...
    }

    public void search() {
        // ...
    }

    public void print() {
        Node<T> curr = head;
        while (curr.next != null) {
            System.out.print(curr.data);
            System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.print("END");
    }

    public void printReverse() {
        // ...
    }

    public void sort() {
        // ...
    }

    public boolean isEmpty() {
        return (this.length == 0) ? true : false; 
    }

    public int getLength() {
        return this.length;
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