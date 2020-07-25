package javasrc.ch02_4;

/*
* Algorithm 2.6 Heap priority queue. P. 318
 * 
 * The priority queue is maintained in a heap-ordered complete binary tree in the 
 * array pq[] with pq[0] unused and the N keys in the priority queue in pq[1] 
 * through pq[N]. 
 * 
 * To implement insert(), we increment N, add the new element at the end, then 
 * use swim() to restore the heap order. 
 * 
 * For delMin(), we take the value to be returned from pq[1], then move pq[N] to 
 * pq[1], decrement the size of the heap, and use sink() to restore the heap 
 * condition. We also set the now-unused position pq[N+1] to null to allow the 
 * system to reclaim the memory associated with it. 
 * 
 
 ! Same as MinPQ, except add changeKey()
 
 */

import java.util.NoSuchElementException;
import lib.*;

public class MinPQex<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N = 0;

    public MinPQex(int capacity) {
        this.pq = (Key[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return this.N;
    }

    public Key min() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.pq[1];
    }

    public void insert(Key newKey) throws Exception {
        if (this.N < this.pq.length - 1) {
            pq[++N] = newKey;
            swim(this.N);
        } else {
            throw new IllegalArgumentException("Priority queue us full!");
        }

        if (!isMinHeap()) {
            throw new Exception("Priority queue is Not min ordered, after insertion!");
        }
    }

    public Key delMin() throws Exception {
        Key min = pq[1];
        exch(1, this.N--);
        pq[this.N + 1] = null;
        sink(1);

        if (!isMinHeap()) {
            throw new Exception("Priority queue is Not min ordered, after delMin!");
        }

        return min;
    }

    public void changeKey(Key oldKey, Key newKey){
        int i = 1;
        for(; i <= N; i++){
            if(pq[i] == oldKey){
                pq[i] = newKey;
                break;
            }
        }
        swim(i);
        sink(i);
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

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k,j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean isMinHeap() {
        for (int i = 1; i <= N; i++) {
            if (pq[i] == null) {
                return false;
            }
        }
        for (int i = N + 1; i < pq.length; i++) {
            if (pq[i] != null) {
                return false;
            }
        }
        if (pq[0] != null) {
            return false;
        }

        return isMinHeapOrdered(1);
    }

    private boolean isMinHeapOrdered(int k) {
        if (k > N) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= N && greater(k, left)) {
            return false;
        }
        if (right <= N && greater(k, right)) {
            return false;
        }
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }

    public static void main(String[] args) {
        StdOut.println("1. test regular operations ...");
        MinPQex<Integer> a = new MinPQex<>(7);
        try {
            for (int i = 1; i <= 7; i++) {
                a.insert(i);
            }
            a.insert(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        a.printArray();

        StdOut.println("\n2. test change key ...");
        a.changeKey(2, 9);

        a.printArray();

    }

}