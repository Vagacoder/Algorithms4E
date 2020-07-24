package javasrc.ch04_4;

/*
* 4.4.32 Parent-checking heuristic. Modify Bellman-Ford to visit a vertex v only 
* if its SPT parent edgeTo[v] is not currently on the queue. This heuristic has 
* been reported by Cherkassky, Goldberg, and Radzik to be useful in practice. 
* Prove that it correctly computes shortest paths and that the worst-case running 
 * time is proportional to EV.
 * 
 
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 ? 3. tinyEWDGnc.txt (an edge weighted digraph has CYCLE with NEGATIVE edges), P.669
 
 */

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStackX;

import lib.*;

public class BellmanFordSPparentCheck {

    private double[] distTo;
    private DirectedEdge[] edgeTo;

    private LinkedListQueue<Integer> queue;
    private boolean[] onQueue;

    private int cost;

    private Iterable<DirectedEdge> cycle;

    public BellmanFordSPparentCheck(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.queue = new LinkedListQueue<>();
        this.onQueue = new boolean[V];

        for (int i = 0; i < V; i++) {
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        this.distTo[s] = 0.0;
        this.queue.enqueue(s);
        onQueue[s] = true;

        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;

            // * 4.4.32
            if (edgeTo[v] != null) {
                int parent = edgeTo[v].from();
                if (onQueue[parent]) {
                    continue;
                }
            }
            
            relax(g, v);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % g.V() == 0) {
                findNegativeCycle();
            }
        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int i = 0; i < V; i++) {
            if (this.edgeTo[i] != null) {
                spt.addEdge(edgeTo[i]);
            }
        }

        EdgeWeightedDirectedCycle cfind = new EdgeWeightedDirectedCycle(spt);
        this.cycle = cfind.cycle();
    }

    public double distTo(int v) {
        return this.distTo[v];
    }

    public boolean hasPathTo(int v) {
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public boolean hasNegativeCycle() {
        return this.cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return this.cycle;
    }

    public static void main(String[] args) {

        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        BellmanFordSPparentCheck bfsp = new BellmanFordSPparentCheck(g, s);

        if (!bfsp.hasNegativeCycle()) {
            StdOut.println("Shortest paths:");
            for (int i = 0; i < g.V(); i++) {
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if (bfsp.hasPathTo(i)) {
                    for (DirectedEdge e : bfsp.pathTo(i)) {
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("Negative cycle:");
            for (DirectedEdge e : bfsp.negativeCycle()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }

        // * tester #2
        StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
        filename = "data/tinyEWDGn.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfsp = new BellmanFordSPparentCheck(g, s);
        if (!bfsp.hasNegativeCycle()) {
            StdOut.println("Shortest paths:");
            for (int i = 0; i < g.V(); i++) {
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if (bfsp.hasPathTo(i)) {
                    for (DirectedEdge e : bfsp.pathTo(i)) {
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("Negative cycle:");
            for (DirectedEdge e : bfsp.negativeCycle()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }

        // * tester #3
        StdOut.println("\n3. tinyEWDGnc.txt with a negative cycle");
        filename = "data/tinyEWDGnc.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfsp = new BellmanFordSPparentCheck(g, s);
        if (!bfsp.hasNegativeCycle()) {
            StdOut.println("Shortest paths:");
            for (int i = 0; i < g.V(); i++) {
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if (bfsp.hasPathTo(i)) {
                    for (DirectedEdge e : bfsp.pathTo(i)) {
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("Negative cycle:");
            for (DirectedEdge e : bfsp.negativeCycle()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }
    }
}