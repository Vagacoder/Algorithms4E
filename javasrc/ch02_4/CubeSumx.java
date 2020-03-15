package javasrc.ch02_4;

/*
* 
*/

import lib.*;

public class CubeSumx implements Comparable<CubeSumx> {
    private final int sum;
    private final int i;
    private final int j;

    public CubeSumx(int i, int j) {
        this.sum = i*i*i + j*j*j;
        this.i = i;
        this.j = j;
    }

    public int compareTo(CubeSumx that) {
        if (this.sum < that.sum) return -1;
        if (this.sum > that.sum) return +1;
        return 0;
    }

    public String toString() {
        return sum + " = " + i + "^3" + " + " + j + "^3";
    }


    public static void main(String[] args) {

        // int n = Integer.parseInt(args[0]);
        int n = 10;

        // initialize priority queue
        MinPQ<CubeSumx> pq = new MinPQ<CubeSumx>();
        for (int i = 0; i <= n; i++) {
            pq.insert(new CubeSumx(i, i));
        }

        // find smallest sum, print it out, and update
        while (!pq.isEmpty()) {
            CubeSumx s = pq.delMin();
            StdOut.println(s);
            if (s.j < n)
                pq.insert(new CubeSumx(s.i, s.j + 1));
        }
    }

}