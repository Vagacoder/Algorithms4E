package javasrc.ch01_3;

/*
Algorithm 1.3 FIFO queue (linked list implementatin)
P.151
*/

import lib.StdOut;

import java.util.Iterator;

import lib.StdIn;

public class LinkedListQueue<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private Node last;
    private int sizeOfQueue;

    public boolean isEmpty() {
        // return this.sizeOfQueue == 0;
        return this.first == null;
    }

    public int size() {
        return this.sizeOfQueue;
    }

    public void enqueue(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (!this.isEmpty()) {
            this.last.next = newNode;
            this.last = newNode;
        } else {
            this.first = newNode;
            this.last = newNode;
        }
        this.sizeOfQueue++;
    }

    public T dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        Node result = this.first;
        this.first = this.first.next;
        this.sizeOfQueue--;
        if (this.isEmpty()) {
            this.last = this.first;
        }
        result.next = null;
        return result.item;
    }

    public static void main(String[] args) {

        LinkedListQueue<String> q = new LinkedListQueue<>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (!input.equals("-")) {
                q.enqueue(input);
            } else {
                if (q.isEmpty()) {
                    StdOut.println("(Queue is empty)");
                } else {
                    StdOut.println(q.dequeue());
                }
            }
        }
        StdOut.println("(" + q.size() + " left in Queue)");
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {

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
