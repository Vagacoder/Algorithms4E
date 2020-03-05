package javasrc.ch02_4;

/*
* Algorithm 2.6 Heap priority queue. P. 318

The priority queue is maintained in a heap-ordered complete binary tree in the 
array pq[] with pq[0] unused and the N keys in the priority queue in pq[1] through 
pq[N]. 

To implement insert(), we increment N, add the new element at the end, then use 
swim() to restore the heap order. 

For delMax(), we take the value to be returned from pq[1], then move pq[N] to pq[1], 
decrement the size of the heap, and use sink() to restore the heap condition. 
We also set the now-unused position pq[N+1] to null to allow the system to reclaim 
the memory associated with it. 

* Proposition O. 
The largest key in a heap-ordered binary tree is found at the root.

* Proposition p. 
The height of a complete binary tree of size N is ⎣lgN⎦ .

* Proposition Q. 
In an N-key priority queue, the heap algorithms require no more than (1 + lg N) 
compares for insert and no more than (2lg N) compares for remove the maximum.



*/

import lib.*;

public class MaxPQ<Key extends Comparable<Key>>{

    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN){
        this.pq = (Key[]) new Comparable[maxN + 1];
    }
    
    public boolean isEmpty(){
        return this.N == 0;
    }

    public int size(){
        return this.N;
    }

    public void insert(Key newKey){
        pq[++N] = newKey;
        swim(this.N);
    }

    public Key delMax(){
        Key max = pq[1];
        exch(1, this.N--);
        pq[this.N + 1] = null;
        sink(1);
        return max;
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        while ( 2 * k <= N ){
            int j = 2 * k;
            if(j < N && less(j, j+1)){
                j++;
            }
            if(!less(k, j)){
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    public static void main(String[] args){
    
    }
}