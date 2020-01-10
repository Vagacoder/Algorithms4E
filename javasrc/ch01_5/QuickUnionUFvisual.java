package javasrc.ch01_5;

/*
1.5.16 Amortized costs plots. Instrument your implementations from Exercise 1.5.7
( QuickUnionUF and QuickFindUF) to make amortized costs plots like those in the 
text (P.232). 

Note: use VisualAccumulator from Chapter 1 section 2 as reference.

Quick-Union implementation, P.224

usage:
% java QuickUnionUFvisual < input.txt
*/

import lib.StdOut;
import lib.StdIn;
import lib.StdDraw;

public class QuickUnionUFvisual {

    private int[] parent;
    private int count;

    private int totalInputPairNumber;
    private int tempUnionCost;
    private int totalUnionCost;

    public QuickUnionUFvisual(int n) {
        this.count = n;
        this.totalInputPairNumber = 0;
        this.tempUnionCost = 0;
        this.totalUnionCost = 0;
        this.parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        StdDraw.setXscale(0, n * 1.8);
        StdDraw.setYscale(0, 1000);
        StdDraw.setPenRadius(0.005);
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);
        this.tempUnionCost = 0;
        while (p != parent[p]) {
            p = parent[p];
            this.tempUnionCost++;
        }
        return p;
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public void union(int p, int q) {
        int unionCost = 0;
        int rootP = find(p);
        unionCost += this.tempUnionCost;
        int rootQ = find(q);
        unionCost += this.tempUnionCost;

        if (rootP == rootQ) {

        } else {
            parent[rootP] = rootQ;
            unionCost++;
            count--;
        }

        // Draw Union
        this.totalInputPairNumber++;
        this.totalUnionCost += unionCost;
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(this.totalInputPairNumber, unionCost);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.point(this.totalInputPairNumber, ((this.totalUnionCost * 1.0) / this.totalInputPairNumber));

    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnionUFvisual uf = new QuickUnionUFvisual(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            // StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

    // public static void main(String[] args) {
    // int n = 10;
    // QuickUnionUFvisual uf = new QuickUnionUFvisual(n);
    // uf.union(4, 3);
    // StdOut.println("add 4 and 3, get " + uf.count() + " sets.");
    // uf.union(3, 8);
    // StdOut.println("add 3 and 8, get " + uf.count() + " sets.");
    // uf.union(6, 5);
    // StdOut.println("add 6 and 5, get " + uf.count() + " sets.");
    // uf.union(9, 4);
    // StdOut.println("add 9 and 4, get " + uf.count() + " sets.");
    // uf.union(2, 1);
    // StdOut.println("add 2 and 1, get " + uf.count() + " sets.");
    // uf.union(8, 9);
    // StdOut.println("add 8 and 9, get " + uf.count() + " sets.");
    // uf.union(5, 0);
    // StdOut.println("add 5 and 0, get " + uf.count() + " sets.");
    // uf.union(7, 2);
    // StdOut.println("add 7 and 2, get " + uf.count() + " sets.");
    // uf.union(6, 1);
    // StdOut.println("add 6 and 1, get " + uf.count() + " sets.");
    // uf.union(1, 0);
    // StdOut.println("add 1 and 0, get " + uf.count() + " sets.");
    // uf.union(6, 7);
    // StdOut.println("add 6 and 7, get " + uf.count() + " sets.");
    // }
}