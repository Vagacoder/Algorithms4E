package javasrc.ch02_4;

/*
* 2.4.29 Min/max priority queue. 
Design a data type that supports the following operations: insert, delete the maximum, 
and delete the minimum (all in logarithmic time); and find the maximum and find 
the minimum (both in constant time). 

Hint: Use two heaps.
*/

import lib.*;

public class MinMaxPQ<Key extends Comparable<Key>>{

    private Key[] minPq;
    private Key[] maxPq;
    private int size = 0;

    public MinMaxPQ(int maxSize){
        this.minPq = (Key[]) new Comparable[maxSize + 1];
        this.maxPq = (Key[]) new Comparable[maxSize + 1];
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public void insert(Key newKey){
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

    public Key delMax(){
        Key max = maxPq[1];
        exch(maxPq, 1, this.size - 1);
        this.maxPq[this.size] = null;

        this.size--;
        sinkMax(1);
        return max;
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


    public static void main(String[] args){
    
    }
}