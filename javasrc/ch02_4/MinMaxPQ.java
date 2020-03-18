package javasrc.ch02_4;

/*
* 2.4.29 Min/max priority queue. 
Design a data type that supports the following operations: insert, delete the maximum, 
and delete the minimum (all in logarithmic time); and find the maximum and find 
the minimum (both in constant time). 

Hint: Use two heaps.

! I feel my solution is not best one, need find a better one later.

*/

import lib.*;

public class MinMaxPQ<Key extends Comparable<Key>> {

    private Key[] minPq;
    private Key[] maxPq;
    private int size = 0;

    public MinMaxPQ(int maxSize) {
        this.minPq = (Key[]) new Comparable[maxSize + 1];
        this.maxPq = (Key[]) new Comparable[maxSize + 1];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Key newKey) {
        if (this.size < this.minPq.length - 1) {
            this.size++;
            this.minPq[size] = newKey;
            swimMin(this.size);
            this.maxPq[size] = newKey;
            swimMax(this.size);
        } else {
            StdOut.println("PQ is full!");
        }
    }

    public Key findMax() {
        return this.maxPq[1];
    }

    public Key delMax() {
        Key max = maxPq[1];
        exch(maxPq, 1, this.size);
        this.maxPq[this.size] = null;

        removeFromMin(max);

        // ! move size decreas into removeFromMin()
        // this.size--;
        sinkMax(1);
        return max;
    }

    private void removeFromMin(Key key) {
        int k = 0;
        for (int i = 1; i <= this.size; i++) {
            if (this.minPq[i].compareTo(key) == 0) {
                k = i;
                break;
            }
        }

        exch(this.minPq, k, this.size);
        this.minPq[this.size] = null;

        // * if key is last one, no need sink
        if (this.minPq[k] == null) {
            this.size--;
            return;
        }

        exch(this.minPq, k, 1);
        this.size--;
        sinkMin(1);
        swimMin(k);
    }

    public Key findMin() {
        return this.minPq[1];
    }

    public Key delMin() {
        Key min = minPq[1];
        exch(minPq, 1, this.size);
        this.minPq[this.size] = null;

        removeFromMax(min);

        // ! move size decreas into removeFromMax()
        // this.size--;
        sinkMin(1);
        return min;
    }

    private void removeFromMax(Key key) {
        int k = 0;
        for (int i = 1; i <= this.size; i++) {
            if (this.maxPq[i].compareTo(key) == 0) {
                k = i;
                break;
            }
        }

        exch(this.maxPq, k, this.size);
        this.maxPq[this.size] = null;

        // * if key is last one, no need sink
        if (this.maxPq[k] == null) {
            this.size--;
            return;
        }

        exch(this.maxPq, k, 1);
        this.size--;
        sinkMax(1);
        swimMax(k);

    }

    private void swimMin(int k) {
        while (k > 1 && larger(this.minPq, k / 2, k)) {
            exch(this.minPq, k / 2, k);
            k = k / 2;
        }
    }

    private void swimMax(int k) {
        while (k > 1 && less(this.maxPq, k / 2, k)) {
            exch(this.maxPq, k / 2, k);
            k = k / 2;
        }
    }

    private void sinkMin(int k) {
        while (2 * k <= this.size) {
            int j = 2 * k;
            if (j < this.size && larger(this.minPq, j, j + 1)) {
                j++;
            }
            if (!larger(this.minPq, k, j)) {
                break;
            }
            exch(this.minPq, k, j);
            k = j;
        }
    }

    private void sinkMax(int k) {
        while (2 * k <= this.size) {
            int j = 2 * k;
            if (j < this.size && less(this.maxPq, j, j + 1)) {
                j++;
            }
            if (!less(this.maxPq, k, j)) {
                break;
            }
            exch(this.maxPq, k, j);
            k = j;
        }
    }

    private boolean less(Key[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private boolean larger(Key[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(Key[] pq, int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public static boolean check() {
        boolean good = true;

        StdOut.println("1.1. test findMax() delMax() ...");
        int[] o = { 0, 3, 6, 1, 4, 5, 2, 7 };
        MinMaxPQ<Integer> a = new MinMaxPQ<>(7);
        for (int i = 1; i <= 7; i++) {
            a.insert(o[i]);
        }

        a.insert(8);

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println(a.findMax());
        StdOut.println(a.delMax());

        StdOut.println();

        StdOut.println("1.2. test findMin() delMin() ...");
        for (int i = 1; i <= 7; i++) {
            a.insert(o[i]);
        }
        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        StdOut.println(a.findMin());
        StdOut.println(a.delMin());

        return good;
    }

    public static void main(String[] args) {
        StdOut.println("1. testing ... ");
        StdOut.println(check());
    }
}