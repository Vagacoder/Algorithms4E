package javasrc.ch04_4;

/*
*  4.4.34 Monotonic shortest path. Given an edge-weighted digraph, find a monotonic 
 * shortest path from s to every other vertex. A path is monotonic if the weight 
 * of its edges are either strictly increasing or strictly decreasing. 
 * 
 * Hint : Relax edges in ascending order always NOT in queue and find a best path; 
 * then relax edges in descending order and find a best path.

 ! The weight of edges are increasing or descreasing alone the path, one egde by 
 ! another.

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 */

import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class MonotonicSP2 {
    private double[] distToA;
    private double[] distToD;
    private DirectedEdge[] edgeToA;
    private DirectedEdge[] edgeToD;

    public MonotonicSP2(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        this.distToA = new double[V];
        this.distToD = new double[V];
        this.edgeToA = new DirectedEdge[V];
        this.edgeToD = new DirectedEdge[V];

        for (int i = 0; i < V; i++) {
            this.distToA[i] = Double.POSITIVE_INFINITY;
            this.distToD[i] = Double.POSITIVE_INFINITY;
        }
        this.distToA[s] = 0.0;
        this.distToD[s] = 0.0;

        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : g.adj(v)) {
                    relaxA(e);
                    relaxD(e);
                }
            }
        }
    }

    // *
    private void relaxA(DirectedEdge e) {
        int v = e.from();
        int w = e.to();

        if (edgeToA[v] == null || e.weight() >= edgeToA[v].weight()) {
            double newDistToW = distToA[v] + e.weight();
            if (distToA[w] > newDistToW) {
                distToA[w] = newDistToW;
                edgeToA[w] = e;
            }
        }
    }

    private void relaxD(DirectedEdge e) {
        int v = e.from();
        int w = e.to();

        if (edgeToD[v] == null || e.weight() <= edgeToD[v].weight()) {
            double newDistToW = distToD[v] + e.weight();
            if (distToD[w] > newDistToW) {
                distToD[w] = newDistToW;
                edgeToD[w] = e;
            }
        }
    }

    public boolean hasPathToA(int v) {
        return this.distToA[v] < Double.POSITIVE_INFINITY;
    }

    public double distToA(int v) {
        return this.distToA[v];
    }

    public boolean hasPathToD(int v) {
        return this.distToD[v] < Double.POSITIVE_INFINITY;
    }

    public double distToD(int v) {
        return this.distToD[v];
    }

    public Iterable<DirectedEdge> pathToA(int v) {
        if (!hasPathToA(v)) {
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for (DirectedEdge e = edgeToA[v]; e != null; e = edgeToA[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public Iterable<DirectedEdge> pathToD(int v) {
        if (!hasPathToD(v)) {
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for (DirectedEdge e = edgeToD[v]; e != null; e = edgeToD[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        MonotonicSP2 mpg = new MonotonicSP2(g, s);

        StdOut.println("1.1. Shortest Ascending paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", mpg.distToA(i));
            if (mpg.hasPathToA(i)) {
                for (DirectedEdge e : mpg.pathToA(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

        StdOut.println("1.2. Shortest Descending paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", mpg.distToD(i));
            if (mpg.hasPathToD(i)) {
                for (DirectedEdge e : mpg.pathToD(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }
}