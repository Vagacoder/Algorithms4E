package javasrc.ch02_4;

/*
* 2.4.25 Computational number theory. 
Write a program that prints out all integers of the form a^3 + b^3 where a and b 
are integers between 0 and N in sorted order, without using excessive space. 

That is, instead of computing an array of the N2 sums and sorting them, build a 
minimum-oriented priority queue, 
! initially containing (0^3, 0, 0), (1^3, 1, 0), (2^3, 2, 0), . . . , (N^3, N, 0). 
! should contain (N^3 , N, N) to avoid reversed pair like (2, 4) and (4, 2)

Then, while the priority queue is nonempty, remove the smallest item(i^3 + j^3, i, j ), 
print it, and then, if j < N, insert the item (i^3 + ( j+1)^3, i, j+1).

Use this program to find all distinct integers a, b, c, and d between 0 and 10^6 
such that a^3 + b^3 = c^3 + d^3.

*/

import lib.*;

public class CubeSum {

    class Data {
        long sum;
        int i;
        int j;

        public Data(int i, int j) {
            this.i = i;
            this.j = j;
            long longI = i;
            long longJ = j;
            this.sum = longI * longI * longI + longJ * longJ * longJ;
        }

        @Override
        public String toString() {
            return "(" + this.sum + ", " + this.i + ", " + this.j + ")";
        }
    }

    // * [0, maxNumber], both are inclusive, total # is maxNumber + 1
    private int maxNumber;
    // * size max value is maxNumber + 1
    private int size;
    // * pq size is maxNumber + 2
    private Data[] pq;

    public CubeSum(int maxNumber) {
        this.maxNumber = maxNumber;
        this.size = 0;
        this.pq = new Data[maxNumber + 2];
        for (int i = 0; i <= maxNumber; i++) {
            // ! not insert (i, 0) to avoid reversed pair like (2, 4) and (4, 2)
            this.insert(i, i);
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(int i, int j) {
        if (this.size < this.pq.length - 1) {
            pq[++size] = new Data(i, j);
            swim(size);
        } else {
            StdOut.println("PQ is full!");
        }
    }

    public Data delMin() {
        Data min = pq[1];
        exch(1, this.size--);
        pq[this.size + 1] = null;
        sink(1);
        return min;
    }

    private void swim(int k) {
        while (k > 1 && larger(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= this.size) {
            int j = 2 * k;
            if (j < this.size && larger(j, j + 1)) {
                j++;
            }
            if (!larger(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean larger(int i, int j) {
        return pq[i].sum - pq[j].sum > 0;
    }

    private void exch(int i, int j) {
        Data temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean isMinHeap() {
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

        return isMinHeapOrdered(1);
    }

    private boolean isMinHeapOrdered(int k) {
        if (k > this.size) {
            return true;
        }

        int left = 2 * k;
        int right = 2 * k + 1;

        if (left <= this.size && larger(k, left))
            return false;
        if (right <= this.size && larger(k, right))
            return false;

        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }

    public static void check() {
        int N = 10;
        CubeSum cs = new CubeSum(N);
        while (!cs.isEmpty()) {
            Data min = cs.delMin();
            StdOut.println(min.toString());
            int j = min.j;
            if (j < N) {
                cs.insert(min.i, j + 1);
            }
        }
    }

    public static void main(String[] args) {
        StdOut.println("0. Testing java long and int ... ");
        int f = 100000000;
        int g = 100000000;
        long sum = f*g;
        long h = 10000000000000000L;
        StdOut.println("" + sum);
        StdOut.println("" + h);

        StdOut.println("1. Testing N = 10 ... ");
        check();

        StdOut.println("2, Find distinct pairs ...");
        int N = 1000;
        CubeSum cs = new CubeSum(N);
        Data previousData = cs.delMin();
        Data foundData = null;
        while (!cs.isEmpty()) {
            Data currentData = cs.delMin();
            int j = currentData.j;
            if (j < N) {
                cs.insert(currentData.i, j + 1);
            }

            // ! have to check reversed pair if initially contain (N^3, N ,0)
            // if (currentData.sum == previousData.sum) {
            //     if (currentData.i != previousData.j || currentData.j != previousData.i) {
            //         if (foundData == null) {
            //             StdOut.println("Find a pair: " + previousData.toString() + ", " + currentData.toString());
            //             foundData = currentData;
            //         } else {
            //             if (foundData.i != currentData.j || foundData.j != currentData.i) {
            //                 StdOut.println("Find a pair: " + previousData.toString() + ", " + currentData.toString());
            //                 foundData = currentData;
            //             }
            //         }
            //     }
            // } else {
            //     previousData = currentData;
            //     foundData = null;
            // }

            // ! Dont need check reversed pair if initially contain (N^3, N ,N)
            if (currentData.sum == previousData.sum) {
                StdOut.println("Find a pair: " + previousData.toString() + ", " + currentData.toString());
            }else {
                previousData = currentData;
            }
        }
    }
}