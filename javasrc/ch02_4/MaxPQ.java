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

/*
* 2.4.19 Implement the constructor for MaxPQ that takes an array of items as argument,
using the bottom-up heap construction method described on page 323 in the text.
*/

import lib.*;

public class MaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN) {
        this.pq = (Key[]) new Comparable[maxN + 1];
    }

    // * 2.4.19
    public MaxPQ(Key[] arr){
        this.pq = (Key[]) new Comparable[arr.length + 1];
        
        for(int i = 0; i < arr.length; i++){
            pq[i+1] = arr[i];
        }

        for(int i = 1; i < pq.length; i++){
            swim(i);
        }

        this.N = arr.length;
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return this.N;
    }

    public void insert(Key newKey) throws Exception {
        if (this.N < this.pq.length) {
            pq[++N] = newKey;
            swim(this.N);
        } else {
            throw new Exception("Priority queue is full!");
        }
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, this.N--);
        pq[this.N + 1] = null;
        sink(1);
        return max;// copy arr
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean isMaxHeap() {
        for (int i = 1; i <= this.N; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = this.N + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }

        if (pq[0] != null) {
            return false;
        }

        return isMaxHeapOrdered(1);
    }

    private boolean isMaxHeapOrdered(int k) {
        if (k > this.N) {
            return true;
        }

        int left = 2*k;
        int right = 2*k + 1;

        if (left  <= this.N && less(k, left))  return false;
        if (right <= this.N && less(k, right)) return false;
        
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }

    public void printArray(){
        for (int i= 0; i < this.pq.length; i ++){
            if(this.pq[i] != null){
                StdOut.print(this.pq[i].toString()+ ", ");
            } else{
                StdOut.print("null, ");
            }
        }
        StdOut.println();
    }

    public static boolean check() throws Exception {
        boolean good = true;

        MaxPQ<Integer> a =  new MaxPQ<>(7);
        for (int i = 1; i<=7; i ++){
            a.insert(i);
        }

        if(!a.isMaxHeap()){
            return false;
        }

        a.printArray();

        int number = 100;
        MaxPQ<Integer> b =  new MaxPQ<>(number);
        for (int i = 1; i < number; i++){
            b.insert(i);
        }

        if(!b.isMaxHeap()){
            return false;
        }

        // * 2.4.19 test new constructor
        Integer[] numbers1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        MaxPQ<Integer> c = new MaxPQ<>(numbers1);
        
        c.printArray();

        // for(int i = 0; i < numbers1.length; i++){
        //     StdOut.print(numbers1[i] + ", ");
        // }

        if(!c.isMaxHeap()){
            return false;
        }
    


        return good;
    }

    public static void main(String[] args) throws Exception {
        StdOut.println("1. Test using check() ....");
        StdOut.println(check());
    }
}