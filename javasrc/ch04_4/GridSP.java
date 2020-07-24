package javasrc.ch04_4;

/*
* 4.4.33 Shortest path in a grid. Given an N-by-N matrix of positive integers, 
* find the shortest path from the (0, 0) entry to the (N-1, N-1) entry, where 
* the length of the path is the sum of the integers in the path. 

* Repeat the problem but assume you can only move right and down.
* See comment on relax()

*/

import java.util.Arrays;
import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class GridSP {

    class XyPair {
        int row;
        int col;

        XyPair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private int[][] distTo;
    private XyPair[][] parentTo;
    private final int demension;
    private final int[][] matrix;

    public GridSP(int[][] matrix, int demension) {
        if (demension <= 0 || demension != matrix.length || demension != matrix[0].length) {
            throw new IllegalArgumentException("Wrong demension or matrix");
        }

        this.demension = demension;
        this.matrix = matrix;
        this.distTo = new int[demension][demension];
        this.parentTo = new XyPair[demension][demension];

        for (int i = 0; i < demension; i++) {
            for (int j = 0; j < demension; j++) {
                this.distTo[i][j] = Integer.MAX_VALUE;
            }
        }

        this.distTo[0][0] = 0;

        int V = demension * demension;

        for (int i = 0; i < V; i++) {
            for (int r = 0; r < demension; r++) {
                for (int c = 0; c < demension; c++) {
                    XyPair thisP = new XyPair(r, c);
                    for (XyPair adjP : adj(thisP)) {
                        relax(thisP, adjP);
                    }
                }
            }
        }

    }

    // * to only move right and down, comment out 1st and 3rd if statements.
    public Iterable<XyPair> adj(XyPair v) {
        LinkedListQueue<XyPair> queue = new LinkedListQueue<XyPair>();

        if (v.row > 0) {
            queue.enqueue(new XyPair(v.row - 1, v.col));
        }

        if (v.row < demension - 1) {
            queue.enqueue(new XyPair(v.row + 1, v.col));
        }

        if (v.col > 0) {
            queue.enqueue(new XyPair(v.row, v.col - 1));
        }

        if (v.col < demension - 1) {
            queue.enqueue(new XyPair(v.row, v.col + 1));
        }

        return queue;
    }

    private void relax(XyPair thisP, XyPair adjP) {
        int newDistToAdjP = distTo[thisP.row][thisP.col] + this.matrix[adjP.row][adjP.col];
        if (distTo[adjP.row][adjP.col] > newDistToAdjP) {
            distTo[adjP.row][adjP.col] = newDistToAdjP;
            parentTo[adjP.row][adjP.col] = thisP;
        }

    }

    public boolean hasPathTo(int r, int c) {
        return this.distTo[r][c] < Double.POSITIVE_INFINITY;
    }

    public Iterable<XyPair> pathTo(int r, int c) {
        if (!hasPathTo(r, c)) {
            return null;
        }

        LinkedListStackX<XyPair> path = new LinkedListStackX<>();

        path.push(new XyPair(r, c));
        XyPair p = this.parentTo[r][c];
        while (p != null) {
            path.push(p);
            p = this.parentTo[p.row][p.col];
        }

        return path;
    }

    public static void main(String[] args) {
        
        StdOut.println("1. For matrix of:");
        int[][] matrix = {{2, 3}, {0, 1}};
        int n = matrix[0].length - 1;
        GridSP gsp = new GridSP(matrix, n+1);
        for(int i = 0; i <= n; i++){
            StdOut.println(Arrays.toString(matrix[i]));
        }
        StdOut.printf("Is there path to %d, %d : %s\n", n, n, (""+gsp.hasPathTo(n, n)));
        for(XyPair p: gsp.pathTo(n, n)){
            StdOut.printf("(%d %d),  ", p.row, p.col);
        }

        StdOut.println("\n\n2. For matrix of:");
        int[][] matrix1 = {{1, 3, 4, 2}, {3, 4, 1, 2}, {2, 4, 3, 1}, {4, 1, 2, 3}};
        n = matrix1[0].length - 1;
        gsp = new GridSP(matrix1, n+1);
        for(int i = 0; i <= n; i++){
            StdOut.println(Arrays.toString(matrix1[i]));
        }
        StdOut.printf("Is there path to %d, %d : %s\n", n, n, (""+gsp.hasPathTo(n, n)));
        for(XyPair p: gsp.pathTo(n, n)){
            StdOut.printf("(%d %d),  ", p.row, p.col);
        }

        StdOut.println("\n\n3. For matrix of:");
        int[][] matrix2 = {{1, 1, 0, 2, 4}, {5, 8, 6, 1, 4}, {4, 0, 1, 2, 5}, {5, 1, 3, 9, 3}, {6, 1, 2, 1, 2}};
        n = matrix2[0].length - 1;
        gsp = new GridSP(matrix2, n+1);
        for(int i = 0; i <= n; i++){
            StdOut.println(Arrays.toString(matrix2[i]));
        }
        StdOut.printf("Is there path to %d, %d : %s\n", n, n, (""+gsp.hasPathTo(n, n)));
        for(XyPair p: gsp.pathTo(n, n)){
            StdOut.printf("(%d %d),  ", p.row, p.col);
        }

        StdOut.println();
    }

}