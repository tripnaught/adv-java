package week5;

public class linkedList {
    public static void main(String[] args) {
        System.out.println("\u001b[36m");

        SinglyLinkedList<Integer> myList = new SinglyLinkedList<Integer>();
        myList.append(2);
        myList.append(3);
        myList.append(5);
        myList.append(7);
        myList.append(11);
        myList.print();

        myList.search(7);
        System.out.println(myList.search(10));
    }
}

class SinglyLinkedList<T> {
    public Node<T> head;
    public int length;

    public SinglyLinkedList() {
        this.head = null;
        this.length = 0;
    }

    // ================================= INSERTS =============================
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

    public void insertAfter(T data, int index) {
        Node<T> curr = head;
        if (index == -1) {
            prepend(data);
            return;
        } else if (index >= length || index < 0) { // restrict OOB
            return;
        }
        Node<T> newNode = new Node<T>(data);
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        newNode.next = curr.next;
        curr.next = newNode;
        length++;
    }

    public void insertAfter(T data, T afterThisData) {
        insertAfter(data, search(afterThisData));
    }

    // ================================= REMOVES =============================
    public Node<T> removeValue(T data) { // this returns the wrong thing
        // base case 1: linked list is empty
        if (isEmpty()) {
            return null;
        }

        // base case 2: trying to remove the head
        if (head.data.equals(data)) {
            head = head.next;
            return null;
        }

        // otherwise: loop through until data matches, then remove
        Node<T> curr = head;
        while (curr.next != null) {
            if (curr.next.data.equals(data)) {
                length--;
                return curr.next = curr.next.next;
            }
            curr = curr.next;
        }
        return null;

    }

    public Node<T> removeIndex(int index) {
        Node<T> curr = head;
        Node<T> result = null;
        if (index >= length || index < 0 || isEmpty()) { // restrict OOB
            return null;
        } else if (index == 0) {
            result = head;
            head = head.next;
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }
            result = curr.next;
            curr.next = (index < length - 1) ? curr.next.next : null;
        }
        length--;
        return result;
    }

    // ================================= COMPLEX =============================
    public int search(T target) {
        // ...
        if (isEmpty() || head == null) {
            return -1;
        }
        Node<T> curr = head;
        int count = 0;
        while (curr != null) {
            if (curr.data.equals(target)) {
                return count;
            }
            curr = curr.next;
            count++;
        }
        return -1;
    }

    public void sort() {
        // ...
    }

    // ================================= UTILS ================================
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
        // ...
    }

    public boolean isEmpty() {
        return (this.length == 0 || this.head == null) ? true : false;
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