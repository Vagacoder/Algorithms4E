package javasrc.ch02_4;

/*
* 2.4.30 Dynamic median-finding. 

Design a data type that supports insert in logarithmic time, find the median in 
constant time, and delete the median in logarithmic time.

Hint: Use a min-heap and a max-heap.

*/

import lib.*;

public class FindMedian<Key extends Comparable<Key>> {

    // * heap containing smaller Keys
    private Key[] maxPq;
    // * heap containing larger keys
    private Key[] minPq;
    private int sizeOfMaxPq = 0;
    private int sizeOfMinPq = 0;
    private Key median;

    public FindMedian(int maxSize) {
        if(maxSize == 1){
            maxSize++;
        }
        this.maxPq = (Key[]) new Comparable[maxSize / 2 + 1];
        this.minPq = (Key[]) new Comparable[maxSize / 2 + 1];
        this.median = null;
    }

    public void insert(Key newKey) {
        if (this.median == null) {
            if (this.maxPq[1] != null && newKey.compareTo(this.maxPq[1]) < 0) {
                this.median = this.maxPq[1];
                this.maxPq[1] = newKey;
                sinkMaxPq(1);
            } else if (this.minPq[1] != null && newKey.compareTo(this.minPq[1]) > 0) {
                this.median = this.minPq[1];
                this.minPq[1] = newKey;
                sinkMinPq(1);
            } else {
                this.median = newKey;
            }
        } else {
            if (this.median.compareTo(newKey) < 0) {
                insertMaxPq(this.median);
                this.median = null;
                insertMinPq(newKey);
            } else {
                insertMinPq(this.median);
                this.median = null;
                insertMaxPq(newKey);
            }
        }
    }

    private void insertMaxPq(Key newKey) {
        if (this.sizeOfMaxPq < this.maxPq.length - 1) {
            this.maxPq[++this.sizeOfMaxPq] = newKey;
            swimMaxPq(this.sizeOfMaxPq);
        } else {
            StdOut.println("max priority queue is full!");
        }
    }

    private void insertMinPq(Key newKey) {
        if (this.sizeOfMinPq < this.minPq.length - 1) {
            this.minPq[++this.sizeOfMinPq] = newKey;
            swimMinPq(this.sizeOfMinPq);
        } else {
            StdOut.println("min priority queue is full!");
        }
    }

    public Key[] getMedian() {
        Key[] result = (Key[]) new Comparable[2];

        if (this.median != null) {
            result[0] = this.median;
            result[1] = null;
        } else {
            result[0] = this.maxPq[1];
            result[1] = this.minPq[1];
        }
        return result;
    }

    private void swimMinPq(int k) {
        while (k > 1 && larger(this.minPq, k / 2, k)) {
            exch(this.minPq, k / 2, k);
            k = k / 2;
        }
    }

    private void swimMaxPq(int k) {
        while (k > 1 && less(this.maxPq, k / 2, k)) {
            exch(this.maxPq, k / 2, k);
            k = k / 2;
        }
    }

    private void sinkMinPq(int k) {
        while (2 * k <= this.sizeOfMinPq) {
            int j = 2 * k;
            if (j < this.sizeOfMinPq && larger(this.minPq, j, j + 1)) {
                j++;
            }
            if (!larger(this.minPq, k, j)) {
                break;
            }
            exch(this.minPq, k, j);
            k = j;
        }
    }

    private void sinkMaxPq(int k) {
        while (2 * k <= this.sizeOfMaxPq) {
            int j = 2 * k;
            if (j < this.sizeOfMaxPq && less(this.maxPq, j, j + 1)) {
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

    public static void check(){
        int N = 1000;

        for (int i = 1; i < N; i++) {
            FindMedian<Integer> fm = new FindMedian<>(i);
            Integer[] a = new Integer[i];
            for (int j = 0; j < i; j++) {
                a[j] = j + 1;
            }

            // * unsorted integer array
            StdRandom.shuffle(a);

            for (int j = 0; j < i; j++) {
                fm.insert(a[j]);
            }

            Comparable[] result = fm.getMedian();

            StdOut.println("Integers range from 1 to " + i);
            double correctMedian = ((i + 1)/2.0);

            StdOut.println("The correct median should be " + correctMedian);
            StdOut.println("Median(s) is(are): " + result[0].toString() + 
                ((result[1] == null)? "" :(", " + result[1].toString())));

            double median = ((result[1] == null) ? (int)result[0] : ((int)result[0] + (int)result[1]) / 2.0);
            StdOut.println("Median is : " + median);
            if (correctMedian != median){
                StdOut.println("Wrong!");
                break;
            }
            StdOut.println();
        }
    }
    public static void main(String[] args) {
        check();
    }
}