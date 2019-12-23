package javasrc.ch01_3;

/*
1.3.41 Copy a queue. 
Create a new constructor so that: Queue<Item> r = new Queue<Item>(q);

Makes r a reference to a new and independent copy of the queue q. You should be 
able to enqueue and dequeue from either q or r without influencing the other. 

Hint : Delete all of the elements from q and add these elements to both q and r.

*/


import java.util.Iterator;
import java.util.NoSuchElementException;
import lib.StdOut;
import lib.StdIn;

public class ResizableArrayQueue<T> implements Iterable<T> {

    private T[] queue;
    private int numberInQueue;
    private int firstIndex;
    private int lastIndex;

    public ResizableArrayQueue() {
        @SuppressWarnings("unchecked")
        T[] q = (T[]) new Object[2];
        this.queue = q;
        numberInQueue = 0;
        firstIndex = 0;
        lastIndex = 0;  // lastIndex excludes
    }

    // copy a queue
    public ResizableArrayQueue(ResizableArrayQueue<T> q){
        int length = q.size();
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[length];
        this.queue = temp;
        for(int i = 0; i< length; i++){
            T tempItem = q.dequeue();
            this.enqueue(tempItem);
            q.enqueue(tempItem);
        }
    }

    public boolean isEmpty() {
        return this.numberInQueue == 0;
    }

    public int size() {
        return this.numberInQueue;
    }

    private void resize(int newCapacity) {
        assert newCapacity >= this.numberInQueue;
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < numberInQueue; i++) {
            newArray[i] = this.queue[this.firstIndex + i];
        }
        this.queue = newArray;
        this.firstIndex = 0;
        this.lastIndex = numberInQueue;
    }

    public void enqueue(T newItem) {
        if (this.lastIndex >= this.queue.length - 1) {
            this.resize(this.numberInQueue * 2);
        }
        this.queue[this.lastIndex++] = newItem;
        this.numberInQueue++;
    }

    public T dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = this.queue[firstIndex];
        this.queue[firstIndex++] = null;
        this.numberInQueue--;
        if (this.numberInQueue > 0 && this.numberInQueue <= queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    public T peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return this.queue[firstIndex];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < numberInQueue;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("Queue is empty");
            }
            T item = queue[firstIndex + this.i];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizableArrayQueue<String> q = new ResizableArrayQueue<>();
        // 1. test input through args
        // while (!StdIn.isEmpty()) {
        //     String input = StdIn.readString();
        //     if (!input.equals("-")) {
        //         q.enqueue(input);
        //     } else {
        //         if (q.isEmpty()) {
        //             StdOut.println("\n(Queue is empty");
        //         } else {
        //             StdOut.println(q.dequeue());
        //         }
        //     }
        // }
        // StdOut.println("(" + q.size() + " left in queue)");

        // 2. test input hard coding
        q.enqueue("we");
        q.enqueue("are");
        q.enqueue("good");
        q.enqueue("at");
        q.enqueue("study");
        StdOut.println("(" + q.size() + " left in queue)");

        ResizableArrayQueue<String> q1 = new ResizableArrayQueue<>(q);
        StdOut.println("(" + q1.size() + " left in queue)");
        q1.dequeue();
        StdOut.println("(" + q.size() + " left in queue)");
        StdOut.println("(" + q1.size() + " left in queue)");


    }

}