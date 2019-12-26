package javasrc.ch01_3;

/*
Algorithm 1.2 Pushdown stack (linked list implementation)
P.149

1.3.42 Copy a stack. 
Create a new constructor for the linked-list implementation of Stack so that

    Stack<Item> t = new Stack<Item>(s);

makes t a reference to a new and independent copy of the stack s.
*/

import java.util.Comparator;
import java.util.Iterator;

import lib.StdIn;
import lib.StdOut;

public class LinkedListStack<T extends Comparable<T>> implements Iterable<T>, Comparator<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfStack;

    // no need, default constructor
    public LinkedListStack() {
    }

    // Copy constructor
    public LinkedListStack(LinkedListStack<T> s) {
        LinkedListStack<T> temp = new LinkedListStack<>();

        while (!s.isEmpty()) {
            temp.push(s.pop());
        }
        while (!temp.isEmpty()) {
            T tempItem = temp.pop();
            s.push(tempItem);
            this.push(tempItem);
        }

    }

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

    public boolean find(LinkedListStack<T> stack, T value) {

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

    public T max() {
        Node cursor = first;
        if (cursor == null) {
            return null;
        }
        T max = cursor.item;

        while (cursor.next != null) {
            cursor = cursor.next;
            // ? how generic type implements comparable?
            if (cursor.item.compareTo(max) > 0) {
                max = cursor.item;
            }
        }

        return max;
    }

    public T recursiveMax() {
        return this.recursiveMax(first);
    }

    private T recursiveMax(Node first) {

        if (first.next == null) {
            return first.item;
        } else {
            T maxOfRest = recursiveMax(first.next);
            return first.item.compareTo(maxOfRest) > 0 ? first.item : maxOfRest;
        }
    }

    public static void main(String[] args) {
        LinkedListStack<String> s = new LinkedListStack<>();
        // 1. test by stdin input
        // while (!StdIn.isEmpty()) {
        // String input = StdIn.readString();
        // if (!input.equals("-")) {
        // s.push(input);
        // } else {
        // if (s.isEmpty()) {
        // StdOut.println("(Stack is empty");
        // } else {
        // StdOut.println(s.pop());
        // }
        // }
        // }
        // StdOut.println("(" + s.size() + " left in stack)");

        // 2. test by hard coding input
        s.push("we");
        s.push("are");
        s.push("good");
        s.push("at");
        s.push("study");
        StdOut.println("(" + s.size() + " left in stack)");

        LinkedListStack<String> s1 = new LinkedListStack<>(s);
        StdOut.println("(" + s1.size() + " left in stack)");

        StdOut.println(s1.pop());
        StdOut.println("(" + s.size() + " left in stack)");
        StdOut.println("(" + s1.size() + " left in stack)");
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

    @Override
    public int compare(T a, T b) {
        return a.toString().compareTo(b.toString());
    }

}
