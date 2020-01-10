package javasrc.ch01_5;

/*
1.5.16 Amortized costs plots. Instrument your implementations from Exercise 1.5.7
( QuickUnionUF and QuickFindUF) to make amortized costs plots like those in the 
text (P.232). 

Note: use VisualAccumulator from Chapter 1 section 2 as reference.

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
        StdDraw.setXscale(0, n*1.8);
        StdDraw.setYscale(0, n*2.5);
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

        int unionCost = 2;
        if (pID == qID) {
            // StdOut.println("same set");
        } else {
            for (int i = 0; i < id.length; i++) {
                unionCost++;
                if (id[i] == pID) {
                    id[i] = qID;
                    unionCost++;
                }
            }
            count--;
        }

        this.totalInputPairNumber++;

        // Draw connect
        int connectCost = 2;
        this.totalConnectCost += 2;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(this.totalInputPairNumber, connectCost);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(this.totalInputPairNumber, ((this.totalConnectCost * 1.0) / this.totalInputPairNumber));

        // Draw Union
        this.totalUnionCost += unionCost;
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(this.totalInputPairNumber, unionCost);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.point(this.totalInputPairNumber, ((this.totalUnionCost * 1.0) / this.totalInputPairNumber));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUFvisual uf = new QuickFindUFvisual(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            // StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

    // public static void main(String[] args){
    //     int n = 10;
    //     QuickFindUFvisual qf = new QuickFindUFvisual(n);
    //     qf.union(4, 3);
    //     StdOut.println("add 4 and 3, get " + qf.count() + " sets." );
    //     qf.union(3, 8);
    //     StdOut.println("add 3 and 8, get " + qf.count() + " sets." );
    //     qf.union(6, 5);
    //     StdOut.println("add 6 and 5, get " + qf.count() + " sets." ); 
    //     qf.union(9, 4);
    //     StdOut.println("add 9 and 4, get " + qf.count() + " sets." ); 
    //     qf.union(2, 1);
    //     StdOut.println("add 2 and 1, get " + qf.count() + " sets." ); 
    //     qf.union(8, 9);
    //     StdOut.println("add 8 and 9, get " + qf.count() + " sets." ); 
    //     qf.union(5, 0);
    //     StdOut.println("add 5 and 0, get " + qf.count() + " sets." ); 
    //     qf.union(7, 2);
    //     StdOut.println("add 7 and 2, get " + qf.count() + " sets." ); 
    //     qf.union(6, 1);
    //     StdOut.println("add 6 and 1, get " + qf.count() + " sets." ); 
    //     qf.union(1, 0);
    //     StdOut.println("add 1 and 0, get " + qf.count() + " sets." ); 
    //     qf.union(6, 7);
    //     StdOut.println("add 6 and 7, get " + qf.count() + " sets." );
    // }


}