package javasrc.ch04_4;

/*
* Algorithm 4.11 Bellman-Ford Algorithm (queue-based). P.674
 * 
 * Proposition X. (bellman-Ford algorithm) The following method solves the single-
 * source shortest-paths problem from a given source s for any edge-weighted 
 * digraph with V vertices and no negative cycles reachable from s: Initialize 
 * distTo[s] to 0 and all other distTo[] values to infinity. Then, considering 
 * the digraph’s edges in any order, relax all edges. Make V such passes.
 * 
 * Proposition W (continued). The Bellman-Ford algorithm takes time proportional 
 * to EV and extra space proportional to V.
 * 
 * Proposition Y. The queue-based implementation of the Bellman-Ford algorithm 
 * solves the single-source shortest-paths problem from a given source s (or finds 
 * a negative cycle reachable from s) for any edge-weighted digraph with E edges 
 * and V vertices, in time proportional to EV and extra space proportional to V, 
 * in the worst case.
 * 

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 ? 3. tinyEWDGnc.txt (an edge weighted digraph has CYCLE with NEGATIVE edges), P.669

 */

import java.util.Stack;
import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class BellmanFordSP {
   
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    private boolean[] onQueue;
    private LinkedListQueue<Integer> queue;

    // * number of call on relax()
    private int cost;

    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.onQueue = new boolean[V];
        this.queue = new LinkedListQueue<>();
        
        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        this.queue.enqueue(s);
        onQueue[s] = true;
        while(!queue.isEmpty() && !hasNegativeCycle()){
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(g, v);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v){
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if(!onQueue[w]){
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if(cost++ % g.V() == 0){
                findNegativeCycle();
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

    public double distTo(int v){
        return this.distTo[v];
    }

    public boolean hasPathTo(int v){
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v){
        if(!hasPathTo(v)){
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }
    
    public boolean hasNegativeCycle(){
        return this.cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle(){
        return this.cycle;
    }

    public static void main(String[] args){

        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        BellmanFordSP bfsp = new BellmanFordSP(g, s);

        if(!bfsp.hasNegativeCycle()){
            StdOut.println("Shortest paths:");
            for(int i = 0; i < g.V(); i++){
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if(bfsp.hasPathTo(i)){
                    for(DirectedEdge e: bfsp.pathTo(i)){
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        }else{
            StdOut.println("Negative cycle:");
            for(DirectedEdge e: bfsp.negativeCycle()){
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }

        // * tester #2 
        StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
        filename = "data/tinyEWDGn.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfsp = new BellmanFordSP(g, s);
        if(!bfsp.hasNegativeCycle()){
            StdOut.println("Shortest paths:");
            for(int i = 0; i < g.V(); i++){
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if(bfsp.hasPathTo(i)){
                    for(DirectedEdge e: bfsp.pathTo(i)){
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        }else{
            StdOut.println("Negative cycle:");
            for(DirectedEdge e: bfsp.negativeCycle()){
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }

        // * tester #3 
        StdOut.println("\n3. tinyEWDGnc.txt with a negative cycle");
        filename = "data/tinyEWDGnc.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        bfsp = new BellmanFordSP(g, s);
        if(!bfsp.hasNegativeCycle()){
            StdOut.println("Shortest paths:");
            for(int i = 0; i < g.V(); i++){
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", bfsp.distTo(i));
                if(bfsp.hasPathTo(i)){
                    for(DirectedEdge e: bfsp.pathTo(i)){
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
        }else{
            StdOut.println("Negative cycle:");
            for(DirectedEdge e: bfsp.negativeCycle()){
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }
    }
}