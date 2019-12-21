package javasrc.ch01_3;

/*
Algorithm 1.4 Bag
P.155
*/

import java.util.Iterator;

import lib.StdIn;
import lib.StdOut;

public class LinkedListBag<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfBag;

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.sizeOfBag;
    }

    public void add(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        newNode.next = this.first;
        this.first = newNode;
        this.sizeOfBag++;
    }

    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<T> {

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

        LinkedListBag<String> bag = new LinkedListBag<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            bag.add(input);
            StdOut.println("Size of bag is " + bag.size());
        }
        StdOut.println("Items in bag are:");
        for (String item : bag) {
            StdOut.print(item + " ");
        }
    }

}