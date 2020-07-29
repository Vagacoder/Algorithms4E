package javasrc.ch04_4;

/*
*  4.4.41 Bidirectional search. Develop a class for the source-sink shortest-paths 
* problem that is based on code like Algorithm 4.9 but that initializes the 
* priority queue with both the source and the sink. Doing so leads to the growth 
* of an SPT from each vertex; your main task is to decide precisely what to do 
* when the two SPTs collide.

! Hard

*/

import javasrc.ch01_3.LinkedListStackX;
import javasrc.ch02_4.IndexMinPQ;

import lib.*;


public class BidirectionalSearchSP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    private boolean[] fromS;

    public BidirectionalSearchSP(EdgeWeightedDigraph g, int s, int t){
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.pq = new IndexMinPQ<>(V);
        this.fromS = new boolean[V];

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;
        fromS[s] = true;
        distTo[t] = 0.0;

        pq.insert(s, 0.0);
        pq.insert(t, 0.0);

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
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args){
            // * tester #1
            StdOut.println("1. tinyEWDG.txt no negative edge");
            String filename = "data/tinyEWDG.txt";
            EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
            int s = 0;
            int t = 6;
            BidirectionalSearchSP dsp = new BidirectionalSearchSP(g, s, t);
    
            for(int i = 0; i < g.V(); i++){
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", dsp.distTo(i));
                if(dsp.hasPathTo(i)){
                    for(DirectedEdge e: dsp.pathTo(i)){
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
    }
}