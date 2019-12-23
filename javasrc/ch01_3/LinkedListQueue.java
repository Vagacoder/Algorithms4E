package javasrc.ch01_3;

/*
Algorithm 1.3 FIFO queue (linked list implementatin)
P.151

1.3.41 Copy a queue. 
Create a new constructor so that: Queue<Item> r = new Queue<Item>(q);

Makes r a reference to a new and independent copy of the queue q. You should be 
able to enqueue and dequeue from either q or r without influencing the other. 

Hint : Delete all of the elements from q and add these elements to both q and r.

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

    // no need 
    public LinkedListQueue(){

    }

    // copy constructor
    public LinkedListQueue(LinkedListQueue<T> l){
        int length = l.size();
        for(int i = 0; i < length; i++){
            T tempItem = l.dequeue();
            this.enqueue(tempItem);
            l.enqueue(tempItem);
        }
    }

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

        // 1. test input through args
        // while (!StdIn.isEmpty()) {
        //     String input = StdIn.readString();
        //     if (!input.equals("-")) {
        //         q.enqueue(input);
        //     } else {
        //         if (q.isEmpty()) {
        //             StdOut.println("(Queue is empty)");
        //         } else {
        //             StdOut.println(q.dequeue());
        //         }
        //     }
        // }
        // StdOut.println("(" + q.size() + " left in Queue)");

        // 2. test input hard coding
        q.enqueue("we");
        q.enqueue("are");
        q.enqueue("good");
        q.enqueue("at");
        q.enqueue("study");
        StdOut.println("(" + q.size() + " left in queue)");

        LinkedListQueue<String> q1 = new LinkedListQueue<>(q);
        StdOut.println("(" + q1.size() + " left in queue)");
        q1.dequeue();
        StdOut.println("(" + q.size() + " left in queue)");
        StdOut.println("(" + q1.size() + " left in queue)");

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
