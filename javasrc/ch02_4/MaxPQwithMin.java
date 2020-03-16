package javasrc.ch02_4;

/*
* 2.4.27 Find the minimum. 
Add a min() method to MaxPQ. Your implementation should use constant time and 
constant extra space.

*/

import lib.*;

public class MaxPQwithMin<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N = 0;
    private Key min;

    public MaxPQwithMin(int maxN) {
        this.pq = (Key[]) new Comparable[maxN + 1];
    }

    // * 2.4.19
    public MaxPQwithMin(Key[] arr) {
        this.pq = (Key[]) new Comparable[arr.length + 1];

        for (int i = 0; i < arr.length; i++) {
            pq[i + 1] = arr[i];
        }

        for (int i = 1; i < pq.length; i++) {
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

    // * 2.4.27
    public void insert(Key newKey) throws Exception {
        if (this.N < this.pq.length - 1) {
            
            if(this.isEmpty()){
                this.min = newKey;
            }else{
                if(newKey.compareTo(min) < 0){
                    this.min = newKey;
                }
            }
            
            pq[++N] = newKey;
            swim(this.N);
        } else {
            throw new Exception("Priority queue is full!");
        }
    }

    public Key min(){
        return this.min;
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, this.N--);
        pq[this.N + 1] = null;
        sink(1);
        return max;
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
            if (pq[i] == null)
                return false;
        }
        for (int i = this.N + 1; i < pq.length; i++) {
            if (pq[i] != null)
                return false;
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

        int left = 2 * k;
        int right = 2 * k + 1;

        if (left <= this.N && less(k, left))
            return false;
        if (right <= this.N && less(k, right))
            return false;

        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
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

    public static boolean check() throws Exception {
        boolean good = true;

        MaxPQwithMin<Integer> a = new MaxPQwithMin<>(7);
        for (int i = 7; i >= 1; i--) {
            a.insert(i);
            StdOut.println("current min is: " + a.min().toString());
        }
        try {
            a.insert(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!a.isMaxHeap()) {
            return false;
        }

        a.printArray();

        int number = 100;
        MaxPQwithMin<Integer> b = new MaxPQwithMin<>(number);
        for (int i = 1; i < number; i++) {
            b.insert(i);
        }

        if (!b.isMaxHeap()) {
            return false;
        }

        // * 2.4.19 test new constructor
        Integer[] numbers1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        MaxPQwithMin<Integer> c = new MaxPQwithMin<>(numbers1);

        c.printArray();

        // for(int i = 0; i < numbers1.length; i++){
        // StdOut.print(numbers1[i] + ", ");
        // }

        if (!c.isMaxHeap()) {
            return false;
        }

        return good;
    }

    public static void main(String[] args) throws Exception {
        StdOut.println("1. Test using check() ....");
        StdOut.println(check());
    }
}