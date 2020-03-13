package javasrc.ch02_4;

/*
* 2.4.23 Multiway heaps. 
* Considering the cost of compares only, and assuming that it takes t compares to 
find the largest of t items, find the value of t that minimizes the coefficient 
of N lg N in the compare count when a t-ary heap is used in heapsort. First, assume 
a straightforward generalization of sink(); then, assume that Floyd’s method can 
save one compare in the inner loop.


* A complete heap-ordered ternary trees, with an entry at position k larger than 
or equal to entries at positions 3k-1, 3k, and 3k+1 and smaller than or equal to 
entries at position ⎣(k+1) / 3⎦, for all indices between 1 and N in an array of 
N items, and not much more difficult to use d-ary heaps for any given d. 

There is  a tradeoff between the lower cost from the reduced tree height (log d N) 
and the higher cost of finding the largest of the d children at each node. This 
tradeoff is dependent on details of the implementation and the expected relative 
frequency of operations.

*/

import lib.*;

// * 2.4.23 ternary tree heap
public class MaxPQmultiway<Key extends Comparable<Key>> {

    private Key[] pq;
    private int size = 0;

    public MaxPQmultiway(int maxN) {
        this.pq = (Key[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Key newKey) {
        if (this.size < this.pq.length - 1) {
            pq[++this.size] = newKey;
            swim(this.size);
        } else {
            StdOut.println("Queue is full!");
        }
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, this.size--);
        pq[this.size + 1] = null;
        sink(1);
        return max;
    }

    private void swim(int i) {
        int j = (i + 1) / 3;
        while (i > 1 && less(j, i)) {
            exch(i, j);
            i = j;
            j = (i + 1) / 3;
        }
    }

    private void sink(int i) {
        while (i * 3 - 1 <= size) {
            int j = i * 3 - 1;
            if ((j == size - 1) && less(j, j + 1)) {
                j++;
            } else if (j <= size - 2) {
                int max = j;
                if (less(max, j + 1)) {
                    max = j + 1;
                }
                if (less(max, j + 2)) {
                    max = j + 2;
                }
                j = max;
            }
            exch(i, j);
            i = j;
        }
    }

    private boolean less(int a, int b) {
        return pq[a].compareTo(pq[b]) < 0;
    }

    private void exch(int a, int b) {
        Key temp = pq[a];
        pq[a] = pq[b];
        pq[b] = temp;
    }

    private boolean isMaxHeap() {
        for (int i = 1; i <= this.size; i++) {
            if (pq[i] == null)
                return false;
        }
        for (int i = this.size + 1; i < pq.length; i++) {
            if (pq[i] != null)
                return false;
        }

        if (pq[0] != null) {
            return false;
        }

        return isMaxHeapOrdered(1);
    }

    private boolean isMaxHeapOrdered(int k) {
        if (k > this.size) {
            return true;
        }

        int left = 3 * k - 1;
        int mid = 3 * k;
        int right = 3 * k + 1;

        if (left <= this.size && less(k, left)) {
            return false;
        }
        if (mid <= this.size && less(k, mid)) {
            return false;
        }
        if (right <= this.size && less(k, right)) {
            return false;
        }

        return isMaxHeapOrdered(left) && isMaxHeapOrdered(mid) && isMaxHeapOrdered(right);
    }

    public static boolean check() {
        boolean good = true;

        MaxPQmultiway<Integer> a = new MaxPQmultiway<>(13);
        for (int i = 1; i <= 13; i++) {
            a.insert(i);
        }
        a.insert(14);
        a.printArray();

        if (!a.isMaxHeap()) {
            return false;
        }


        int number = 100;
        MaxPQmultiway<Integer> b = new MaxPQmultiway<>(number);
        for (int i = 1; i < number; i++) {
            b.insert(i);
        }

        if (!b.isMaxHeap()) {
            return false;
        }

        return good;
    }

    public void printArray() {
        for (int i = 0; i < this.pq.length; i++) {
            if (this.pq[i] != null) {
                StdOut.print(this.pq[i].toString() + ", ");
            } else {
                StdOut.print("null, ");
            }
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        StdOut.println("1. Test using check() ....");
        StdOut.println(check());
    }
}