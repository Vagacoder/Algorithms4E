package javasrc.ch01_3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import lib.StdOut;
import lib.StdRandom;

/*
1.3.35 Random queue. 
A random queue stores a collection of items and supports the following API:
public class RandomQueue<Item>

RandomQueue() create an empty random queue
boolean isEmpty(): is the queue empty?
void enqueue(Item item): add an item remove and return a random item
Item dequeue(): (sample without replacement) return a random item, but do not remove
Item sample(): (sample with replacement)

Hint : 
Use an array representation (with resizing). To remove an item, swap one at a 
random position (indexed 0 through N-1) with the one at the last position 
(index N-1). Then delete and return the last object, as in ResizingArrayStack. 

1.3.36 Random iterator. 
Write an iterator for RandomQueue<Item> from the previous exercise that returns 
the items in random order.

*/

public class RandomQueue<T> implements Iterable<T> {

    private T[] queue;
    private int numberInQueue;
    private int first;
    private int last; // last excludes

    public RandomQueue() {
        @SuppressWarnings("unchecked")
        T[] q = (T[]) new Object[2];
        this.queue = q;
        this.numberInQueue = 0;
        this.first = 0;
        this.last = 0;
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
        T[] q = (T[]) new Object[newCapacity];

        for (int i = 0; i < this.numberInQueue; i++) {
            q[i] = this.queue[first + i];
        }

        this.queue = q;
        this.first = 0;
        this.last = this.numberInQueue;
    }

    public void enqueue(T newItem) {
        if (this.last > this.queue.length - 1) {
            this.resize(this.numberInQueue * 2);
        }
        this.queue[this.last++] = newItem;
        this.numberInQueue++;
    }

    public T dequeue() {
        if (this.isEmpty()) {
            return null;
        }

        if (this.numberInQueue < this.queue.length / 4) {
            this.resize(this.queue.length / 2);
        }
        int randomIndex = (int) StdRandom.uniform(first, last);
        T result = this.queue[randomIndex];
        this.queue[randomIndex] = this.queue[last - 1];
        this.queue[--last] = null;
        this.numberInQueue--;
        return result;
    }

    public T sample() {
        if (this.isEmpty()) {
            return null;
        }

        int randomIndex = (int) StdRandom.uniform(first, last);
        return this.queue[randomIndex];
    }

    public void print() {
        for (int i = 0; i < this.queue.length; i++) {
            if (this.queue[i] == null) {
                StdOut.print("null ");
            } else {
                StdOut.print(this.queue[i].toString() + " ");
            }
        }
        StdOut.println();
    }

    @Override
    public Iterator<T> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<T> {

        private int i = 0;
        private T[] q = Arrays.copyOf(queue, queue.length);

        @Override
        public boolean hasNext() {
            return i < numberInQueue;
        }

        @Override
        public T next() {
            if(!this.hasNext()){
                throw new NoSuchElementException("Queue is empty");
            }
            int randomIndex = (int) StdRandom.uniform(first, last);
            while(q[randomIndex] == null){
                randomIndex = (int) StdRandom.uniform(first, last);
            }
            T item = q[randomIndex];
            q[randomIndex] = null;
            i++;
            return item;
        }

    }

    public static void main(String[] args) {
        RandomQueue<String> rq = new RandomQueue<>();
        rq.print();
        rq.dequeue();
        rq.print();

        rq.enqueue("we");
        rq.print();
        rq.enqueue("are");
        rq.print();
        rq.enqueue("good");
        rq.print();
        rq.enqueue("at");
        rq.print();
        rq.enqueue("study");
        rq.print();

        StdOut.println(rq.dequeue());
        rq.print();
        StdOut.println(rq.dequeue());
        rq.print();
        StdOut.println(rq.dequeue());
        rq.print();
        StdOut.println(rq.dequeue());
        rq.print();
        StdOut.println(rq.dequeue());
        rq.print();

        rq.enqueue("they");
        rq.enqueue("like");
        rq.enqueue("this");
        rq.enqueue("living");
        rq.enqueue("style");
        rq.enqueue("too");
        rq.print();

        for(String s: rq){
            StdOut.println(s);
        }

    }


}