package javasrc.ch01_3;

/*
* Algorithm 1.2 Pushdown stack (linked list implementation) P.149

! Simple version, implement only Iterable<T>

* Proposition D. In the linked-list implementations of Bag (Algorithm 1.4), Stack
(Algorithm 1.2), and Queue (Algorithm 1.3), all operations take constant time
in the worst case.


1.3.42 Copy a stack. 
Create a new constructor for the linked-list implementation of Stack so that

Stack<Item> t = new Stack<Item>(s);

makes t a reference to a new and independent copy of the stack s.
*/

import java.util.Iterator;
import lib.*;

public class LinkedListStackX <T> implements Iterable<T>{
    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfStack;

    // no need, default constructor
    public LinkedListStackX() {}

    public boolean isEmpty() {
        // return this.sizeOfStack == 0;
        return first == null;
    }

    public int size() {
        return this.sizeOfStack;
    }

    public void push(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        newNode.next = this.first;
        this.first = newNode;
        this.sizeOfStack++;
    }

    public T pop() {
        if (this.isEmpty()) {
            return null;
        } else {
            Node result = this.first;
            this.first = this.first.next;
            this.sizeOfStack--;
            return result.item;
        }
    }

    public T peek() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.first.item;
        }
    }

    public T removeLast() {
        Node cursor = first;
        if (first == null) {
            return null;
        }

        while (cursor.next.next != null) {
            cursor = cursor.next;
        }

        T result = cursor.item;
        cursor.next = null;
        this.sizeOfStack--;
        return result;
    }

    public T delete(int k) {
        if (k >= this.sizeOfStack) {
            return null;
        }
        Node cursor = first;
        for (int i = 0; i < k - 1; i++) {
            cursor = cursor.next;
        }
        Node temp = cursor.next;
        cursor.next = temp.next;
        this.sizeOfStack--;
        temp.next = null;
        return temp.item;
    }

    public boolean find(LinkedListStackX<T> stack, T value) {

        Node cursor = stack.first;
        if (cursor == null) {
            return false;
        } else if (cursor.item.equals(value)) {
            return true;
        }

        while (cursor.next != null) {
            cursor = cursor.next;
            if (cursor.item.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public T removeAfter(Node a) {
        if (a == null || a.next == null) {
            return null;
        }

        Node cursor = first;
        if (cursor.equals(a)) {
            this.first = a.next;
            a.next = null;
            this.sizeOfStack--;
            return a.item;
        }
        while (cursor.next != null) {
            cursor = cursor.next;
            if (cursor.equals(a)) {
                cursor.next = a.next;
                a.next = null;
                this.sizeOfStack--;
                return a.item;
            }
        }
        return null;
    }

    public boolean insertAfter(Node a, Node b) {
        if (a == null || b == null) {
            return false;
        }
        Node cursor = first;
        if (cursor.equals(a)) {
            b.next = cursor.next;
            cursor.next = b;
            this.sizeOfStack++;
            return true;
        }
        while (cursor.next != null) {
            cursor = cursor.next;
            if (cursor.equals(a)) {
                b.next = cursor.next;
                cursor.next = b;
                this.sizeOfStack++;
                return true;
            }
        }
        return false;
    }

    public int remove(T value) {
        int number = 0;

        if (first == null) {
            return number;
        }

        Node cursor = first;
        if (cursor.item.equals(value)) {
            first = first.next;
            this.sizeOfStack--;
            number++;
        }
        while (cursor.next != null) {
            cursor = cursor.next;
            if (cursor.item.equals(value)) {
                Node temp = cursor.next;
                cursor.next = temp.next;
                this.sizeOfStack--;
                number++;
                temp.next = null;
            }
        }
        return number;
    }

    public void print() {
        Node cur = this.first;
        while (cur != null) {
            StdOut.println(cur.item.toString());
            cur = cur.next;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {

        private Node cursor = first;

        @Override
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override
        public T next() {
            T result = this.cursor.item;
            this.cursor = this.cursor.next;
            return result;
        }
    }

    public static void main(String[] args) {
        StdOut.println("1. Test input by hard coding ... ");
        LinkedListStackX<String> s1 = new LinkedListStackX<>();
        s1.push("study");
        s1.push("at");
        s1.push("good");
        s1.push("are");
        s1.push("we");
        StdOut.println("(" + s1.size() + " left in stack)");

        for(String s: s1){
            StdOut.println(s);
        }
    }

}