package javasrc.ch02_4;

/*
* 2.4.22 Array resizing. 
Add array resizing to MaxPQ, and prove bounds like those of Proposition Q for 
array accesses, in an amortized sense.

*/

import lib.*;

public class MaxPQresizable<Key extends Comparable<Key>>{

    private Key[] pq;
    private int size = 0;

    public MaxPQresizable(){
        this.pq = (Key[]) new Comparable[1];
    }

    public boolean isEmpty(){
        return this.size == 0;
    }
    
    public int size(){
        return this.size;
    }

    public void insert(Key newKey){
        if (this.size >= this.pq.length - 1){
            resizePq(this.pq.length * 2);
        } 
        pq[++size] = newKey;
        swim(this.size);
    }

    public Key delMax() {
        if (this.pq.length > 8 && this.size <= this.pq.length / 4){
            resizePq(this.pq.length / 2);
        }
        Key max = pq[1];
        exch(1, this.size--);
        pq[this.size + 1] = null;
        sink(1);
        return max;
    }

    private void resizePq(int newSize){
        Key[] newPq = (Key[]) new Comparable[newSize];
        for(int i = 0; i <= size; i ++){
            newPq[i] = this.pq[i];
        }
        this.pq = newPq;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && less(j, j + 1)) {
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
        for (int i = 1; i <= this.size; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = this.size + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
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

        int left = 2*k;
        int right = 2*k + 1;

        if (left  <= this.size && less(k, left))  return false;
        if (right <= this.size && less(k, right)) return false;
        
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

    public static boolean check() {
        boolean good = true;

        MaxPQresizable<Integer> a =  new MaxPQresizable<>();
        for (int i = 1; i<=10; i ++){
            a.insert(i);
        }

        if(!a.isMaxHeap()){
            return false;
        }

        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        a.delMax();
        a.printArray();
        int number = 100;
        MaxPQresizable<Integer> b =  new MaxPQresizable<>();
        for (int i = 1; i < number; i++){
            b.insert(i);
        }

        if(!b.isMaxHeap()){
            return false;
        }
    

        return good;
    }

    public static void main(String[] args) throws Exception {
        StdOut.println("1. Test using check() ....");
        StdOut.println(check());
    }
}