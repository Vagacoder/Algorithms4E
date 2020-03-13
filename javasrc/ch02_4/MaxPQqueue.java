package javasrc.ch02_4;

/*
* 2.4.21 Elementary data structures. 
Explain how to use a priority queue to implement the stack, queue, and randomized 
queue data types from Section 1.3 and Exercise 1.3.35.

* Queue
*/

import lib.*;

// * helper class to wrap Key
class Data<Key> implements Comparable<Data<Key>> {
    Integer priority;
    Key key;

    Data(Key newKey, int priority) {
        this.priority = priority;
        this.key = newKey;
    }

    @Override
    public int compareTo(Data<Key> that) {
        return this.priority - that.priority;
    }
}

public class MaxPQqueue<Key> {

    private Integer priority;
    private Data<Key>[] pq;
    private int size;

    public MaxPQqueue(int max) {
        this.priority = 0;
        this.pq = new Data[max + 1];
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Key newKey) {
        pq[++this.size] = new Data<Key>(newKey, this.priority--);
        swim(this.size);
    }

    public Key dequeue() {
        if (this.size <= 0) {
            return null;
        }

        Data<Key> max = pq[1];
        exch(1, this.size--);
        this.pq[this.size + 1] = null;
        sink(1);
        return (Key) max.key;
    }

    public void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    public void sink(int k) {
        while (2 * k < this.size) {
            int j = 2 * k;
            if (j < this.size && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    public boolean less(int a, int b) {
        return pq[a].compareTo(pq[b]) < 0;
    }

    public void exch(int a, int b) {
        Data<Key> temp = pq[a];
        pq[a] = pq[b];
        pq[b] = temp;
    }

    public void printArray() {
        for (int i = 0; i < this.pq.length; i++) {
            if (this.pq[i] == null) {
                StdOut.print("null, ");
            } else {
                StdOut.print(this.pq[i].key.toString() + ", ");
            }
        }
        StdOut.println();
    }

    public static boolean check() {
        boolean good = true;

        StdOut.println("1. enqueue \"this is a unit test\" ...");
        MaxPQqueue<String> stack1 = new MaxPQqueue<>(10);
        stack1.enqueue("this");
        stack1.enqueue("is");
        stack1.enqueue("a");
        stack1.enqueue("unit");
        stack1.enqueue("test");
        StdOut.println("1.1. print array of priority queue ... ");
        stack1.printArray();
        StdOut.println("\n1.2. dequeueing ... ");
        StdOut.println(stack1.dequeue());
        StdOut.println(stack1.dequeue());
        StdOut.println(stack1.dequeue());
        StdOut.println(stack1.dequeue());
        StdOut.println(stack1.dequeue());
        StdOut.println(stack1.dequeue());

        return good;
    }

    public static void main(String[] args) {
        StdOut.println(check());
    }
}