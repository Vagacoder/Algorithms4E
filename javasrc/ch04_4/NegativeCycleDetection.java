package javasrc.ch04_4;

/*
 * 4.4.43 Negative cycle detection. Suppose that we add a constructor to Algorithm 
 * 4.11 (Bellman-Ford) that differs from the constructor given only in that it 
 * omits the second argument and that it initializes all distTo[] entries to 0. 
 * 
 * Show that, if a client uses that constructor, a client call to hasNegativeCycle() 
 * returns true if and only if the graph has a negative cycle (and negativeCycle() 
 * returns that cycle).
 
 ! not working
 
 */

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class NegativeCycleDetection {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    private boolean[] onQueue;
    private LinkedListQueue<Integer> queue;

    // * number of call on relax()
    private int cost;

    private Iterable<DirectedEdge> cycle;

    public NegativeCycleDetection(EdgeWeightedDigraph g){
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.onQueue = new boolean[V];
        this.queue = new LinkedListQueue<>();
        
        for(int i = 0; i < V; i++){
            this.distTo[i] = 0.0;
        }
        // this.distTo[s] = 0.0;

        this.queue.enqueue(0);
        onQueue[0] = true;
        
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

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
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

        NegativeCycleDetection ncd = new NegativeCycleDetection(g);
        StdOut.println("Has negative cycle: " + ncd.hasNegativeCycle());

        // * tester #2 
        StdOut.println("\n2. tinyEWDGnc.txt with a negative cycle");
        filename = "data/tinyEWDGnc.txt";
        g = new EdgeWeightedDigraph(new In(filename));

        ncd = new NegativeCycleDetection(g);
        StdOut.println("Has negative cycle: " + ncd.hasNegativeCycle());
    }
}