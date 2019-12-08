package javasrc.ch01_3;

import java.util.Iterator;

import lib.StdIn;
import lib.StdOut;

public class LinkedListStack<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfStack;

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
        Node result = this.first;
        this.first = this.first.next;
        this.sizeOfStack--;
        return result.item;
    }

    public static void main(String[] args) {
        LinkedListStack<String> s = new LinkedListStack<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (!input.equals("-")) {
                s.push(input);
            } else {
                if (s.isEmpty()) {
                    StdOut.println("(Stack is empty");
                } else {
                    StdOut.println(s.pop());
                }
            }
        }

        StdOut.println("(" + s.size() + " left in stack)");
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
}
