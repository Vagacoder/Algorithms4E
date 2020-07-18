package javasrc.ch04_4;

/*
 * Bellman-Ford Algorithm, General. P.671
 *  
 * Proposition X. (bellman-Ford algorithm) The following method solves the single-
 * source shortest-paths problem from a given source s for any edge-weighted 
 * digraph with V vertices and no negative cycles reachable from s: Initialize 
 * distTo[s] to 0 and all other distTo[] values to infinity. Then, considering 
 * the digraph’s edges in any order, relax all edges. Make V such passes.
 * 
 
 ! Algorithm: a given source s for any edge-weighted digraph with V vertices and 
 ! no negative cycles reachable from s: 
 ! (1) Initialize distTo[s] to 0 and all other distTo[] values to infinity. 
 ! (2) Then, considering the digraph’s edges in any order, relax all edges. 
 ! (3) Make V such passes.

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 ? 3. tinyEWDGnc.txt (an edge weighted digraph has CYCLE with NEGATIVE edges), P.669
 
 */

import java.util.Stack;
import lib.*;

public class BellmanFordSPgeneral {

    private double[] distTo;
    private DirectedEdge[] edgeTo;

    // * for cycle detection, no need for basic algorithm
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSPgeneral(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;

        findNegativeCycle();
        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : g.adj(v)) {
                    relax(e);
                }
            }
        }
    }
    
    private void findNegativeCycle(){
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for(int i = 0; i < V; i++){
            if(this.edgeTo[i] != null){
                spt.addEdge(edgeTo[i]);
            }
        }

        EdgeWeightedDirectedCycle cfind = new EdgeWeightedDirectedCycle(spt);
        this.cycle = cfind.cycle();
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        double newDistToW = distTo[v] + e.weight();
        if (distTo[w] > newDistToW) {
            distTo[w] = newDistToW;
            edgeTo[w] = e;
        }
    }

    public boolean hasNegativeCycle(){
        return this.cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle(){
        return this.cycle;
    }

    public boolean hasPathTo(int v){
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v) {
        return this.distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if(!hasPathTo(v)){
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
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
        BellmanFordSPgeneral bfspg = new BellmanFordSPgeneral(g, s);

        StdOut.println("Shortest paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", bfspg.distTo(i));
            if (bfspg.hasPathTo(i)) {
                for (DirectedEdge e : bfspg.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }


        // * tester #2 
        StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
        filename = "data/tinyEWDGn.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfspg = new BellmanFordSPgeneral(g, s);

        StdOut.println("Shortest paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", bfspg.distTo(i));
            if (bfspg.hasPathTo(i)) {
                for (DirectedEdge e : bfspg.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

        // * tester #3 
        StdOut.println("\n3. tinyEWDGnc.txt with a negative cycle");
        filename = "data/tinyEWDGnc.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfspg = new BellmanFordSPgeneral(g, s);

        StdOut.println("Shortest paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", bfspg.distTo(i));
            if (bfspg.hasPathTo(i)) {
                for (DirectedEdge e : bfspg.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }
}