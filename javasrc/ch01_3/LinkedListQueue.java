package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class LinkedListQueue<T> {

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

    public T pop() {
        Node result = this.first;
        this.first = this.first.next;
        this.sizeOfQueue--;
        if (this.isEmpty()) {
            this.last = this.first;
        }
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
                    StdOut.println("(Queue is empty");
                } else {
                    StdOut.println(q.pop());
                }
            }
        }
        StdOut.println("(" + q.size() + " left in Queue)");
    }
}
