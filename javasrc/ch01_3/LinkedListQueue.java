package javasrc.ch01_3;

/*
* Algorithm 1.3 FIFO queue (linked list implementatin) 
* P.151

* Proposition D. In the linked-list implementations of Bag (Algorithm 1.4), Stack
(Algorithm 1.2), and Queue (Algorithm 1.3), all operations take constant time
in the worst case.

1.3.41 Copy a queue. 
Create a new constructor so that: Queue<Item> r = new Queue<Item>(q);

Makes r a reference to a new and independent copy of the queue q. You should be 
able to enqueue and dequeue from either q or r without influencing the other. 

Hint : Delete all of the elements from q and add these elements to both q and r.

1.3.47 Catenable queues, stacks, or steques.
destructively concatenates two queues, stacks, or steques (see Exercise 1.3.32).

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
    public LinkedListQueue() {

    }

    // copy constructor
    public LinkedListQueue(LinkedListQueue<T> l) {
        int length = l.size();
        for (int i = 0; i < length; i++) {
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

    public T peekTop(){
        if (this.isEmpty()) {
            return null;
        }else {
            return this.first.item;
        }
    }

    public void print() {
        Node cur = this.first;
        while (cur != null) {
            StdOut.println(cur.item.toString());
            cur = cur.next;
        }
    }

    /*
     * 1.3.47 Catenable queues, stacks, or steques. destructively concatenates two
     * queues, stacks, or steques (see Exercise 1.3.32).
     */
    public void concatenate(LinkedListQueue<T> a) {
        while (!a.isEmpty()) {
            this.enqueue(a.dequeue());
        }
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

    public static void main(String[] args) {

        LinkedListQueue<String> q = new LinkedListQueue<>();

        // 1. test input through args
        StdOut.println("1. Test input from StdIn ...");
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

        // 2. test input hard coding
        StdOut.println("2. Test input from hard coding ...");
        LinkedListQueue<String> q1 = new LinkedListQueue<>();

        q1.enqueue("we");
        q1.enqueue("are");
        q1.enqueue("good");
        q1.enqueue("at");
        q1.enqueue("study");
        StdOut.println("(" + q1.size() + " left in queue)");

        LinkedListQueue<String> q2 = new LinkedListQueue<>(q1);
        StdOut.println("(" + q2.size() + " left in queue)");
        q2.dequeue();
        StdOut.println("(" + q1.size() + " left in queue)");
        StdOut.println("(" + q2.size() + " left in queue)");

        // 3. test concatenate
        StdOut.println("3. Test concatenate ...");
        LinkedListQueue<String> q3 = new LinkedListQueue<>();
        q3.enqueue("but");
        q3.enqueue("we");
        q3.enqueue("do");
        q3.enqueue("not");
        q3.enqueue("love");
        q3.enqueue("literature");
        q1.concatenate(q3);
        q1.print();
    }

}
