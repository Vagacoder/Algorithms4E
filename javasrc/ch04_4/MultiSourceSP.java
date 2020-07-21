package javasrc.ch04_4;


import javasrc.ch01_3.LinkedListStackX;

/*
 * 4.4.24 Multisource shortest paths. Develop an API and implementation that uses 
 * Dijkstra’s algorithm to solve the multisource shortest-paths problem on edge-
 * weighted digraphs with positive edge weights: given a set of sources, find a 
 * shortest-paths forest that enables implementation of a method that returns to 
 * clients the shortest path from any source to each vertex. 
 * 
 * Hint : 
 * (1) Add a dummy vertex with a zero-weight edge to each source, 
 * this one is hard to do, since have to re-construct a new digraph.
 * (2) or initialize the priority queue with all sources, with their distTo[] 
 * entries set to 0.

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 ? 3. tinyEWDGnc.txt (an edge weighted digraph has CYCLE with NEGATIVE edges), P.669
 ? 4. tinyEWDGnConvert.txt (from tinyEWDGn.txt, for strawman I P.688)
 */

import javasrc.ch02_4.IndexMinPQ;
import lib.*;

public class MultiSourceSP {
    
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public MultiSourceSP(EdgeWeightedDigraph g, int[] sources){
        int V = g.V();
        this.edgeTo = new DirectedEdge[V];
        this.distTo = new double[V];
        this.pq = new IndexMinPQ<>(V);

        for(int i = 0; i < V; i++){
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        for(int i = 0; i < sources.length;i++){
            int source = sources[i];
            distTo[source] = 0.0;
            this.pq.insert(source, 0.0);
        }

        while(!pq.isEmpty()){
            relax(g, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph g, int v){
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if(pq.contains(w)){
                    pq.changeKey(w, distTo[w]);
                }else{
                    pq.insert(w, distTo[w]);
                }
            }
        }
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
        DirectedEdge e = edgeTo[v];
        while(e != null){
            path.push(e);
            e = this.edgeTo[e.from()];
        }

        return path;
    }

    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int[] sources = {4, 5 ,7};

        MultiSourceSP mssp = new MultiSourceSP(g, sources);
        for(DirectedEdge e: mssp.pathTo(6)){
            StdOut.println(e.toString());
        }
    }
}