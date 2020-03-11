package javasrc.ch02_4;

/* 
* 2.4.21 Elementary data structures. 
Explain how to use a priority queue to implement the stack, queue, and randomized 
queue data types from Section 1.3 and Exercise 1.3.35.

* Stack
*/

import lib.*;

public class MaxPQstack <Key> {
    
    // static variable
    static Integer priority;

    // * inner class to wrap Key
    private class Data implements Comparable<Data>{
        Integer priority;
        Key key;

        Data(Key newKey){
            this.key = newKey;
            this.priority = priority++;
        }

        @Override
        public int compareTo(MaxPQstack<Key>.Data that) {
            return this.priority - that.priority;
        }

    }

    // instance variables
    private Data[] pq;
    private int N = 0;

    // constructor
    public MaxPQstack(int max){
        priority = 0;
        this.pq = (Data[]) new Comparable[max + 1];
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return this.N;
    }

    public void push(Key newKey) {
        if(this.N < this.pq.length){
            pq[++N] = new Data(newKey);
            swim(this.N);
        } else {
            StdOut.println("Priority queue is full!");
        }
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
        Data temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public void printArray(){
        for (int i= 0; i < this.pq.length; i ++){
            if(this.pq[i] != null){
                StdOut.print(this.pq[i].key.toString()+ ", ");
            } else{
                StdOut.print("null, ");
            }
        }
        StdOut.println();
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

    public static boolean check() {
        boolean good = true;
        
        MaxPQstack<String> stack1 = new MaxPQstack<>(10);
        stack1.push("this");
        stack1.push("is");
        stack1.push("a");
        stack1.printArray();
    
        return good;
    }

    public static void main(String[] args){
        StdOut.println(check());
    }
}