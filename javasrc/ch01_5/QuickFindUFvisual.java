package javasrc.ch01_5;

/*
1.5.16 Amortized costs plots. Instrument your implementations from Exercise 1.5.7
( QuickUnionUF and QuickFindUF) to make amortized costs plots like those in the 
text (P.232). 

Note: use VisualAccumulator from Chapter 1 section 2.

Algorithm 1.5 Union-find implementation, P.221

usage:
% java QuickFindUF < input.txt
*/

import lib.StdOut;
import lib.StdIn;
import lib.StdDraw;

public class QuickFindUFvisual {
    private int[] id;
    private int count;

    private int totalInputPairNumber;
    private int totalConnectCost;
    private int totalUnionCost;

    public QuickFindUFvisual(int n) {
        totalInputPairNumber = 0;
        totalUnionCost = 0;
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);
        return id[p];
    }

    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        int pID = id[p];
        int qID = id[q];

        if (pID == qID) {
            return;
        }

        int unionCost = 0;

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
                unionCost++;
            }
        }
        count--;

        this.totalInputPairNumber++;
        
        // Draw connect
        int connectCost = 2;
        this.totalConnectCost += 2;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(this.totalInputPairNumber, connectCost);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(this.totalInputPairNumber, ((this.totalConnectCost * 1.0) / 
        this.totalInputPairNumber));

        // Draw Union
        this.totalUnionCost += unionCost;
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(this.totalInputPairNumber, unionCost);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.point(this.totalInputPairNumber,((this.totalUnionCost * 1.0)/this.totalInputPairNumber));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUFvisual uf = new QuickFindUFvisual(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q))
                continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

}