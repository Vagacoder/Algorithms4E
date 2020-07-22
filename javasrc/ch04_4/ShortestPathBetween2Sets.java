package javasrc.ch04_4;

/*
* 4.4.25 Shortest path between two subsets. Given a digraph with positive edge 
* weights, and two distinguished subsets of vertices S and T, find a shortest 
 * path from any vertex in S to any vertex in T. Your algorithm should run in 
 * time proportional to E log V, in the worst case.
 * 
 * Based on 4.4.24 hint (2), add 2 extra vertices, one is conncted to S, the other
 * connected to T.
 
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 

 */

import javasrc.ch01_3.LinkedListStackX;
import javasrc.ch02_4.IndexMinPQ;
import lib.*;

public class ShortestPathBetween2Sets {
    
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public ShortestPathBetween2Sets(EdgeWeightedDigraph g, int[] set1, int[] set2){
        int N = g.V();
        if(N < set1.length + set2.length){
            throw new IllegalArgumentException("Vertecies number in Digrah must "+
            "equal or smaller than sum of sets");
        }

        int V = N + 2;
        EdgeWeightedDigraph newG = createTempEWD(g, set1, set2);
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.pq = new IndexMinPQ<>(V);

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        this.distTo[N] = 0.0;
        this.pq.insert(N, 0.0);

        while(!this.pq.isEmpty()){
            relax(newG, pq.delMin());
        }

    }


    private EdgeWeightedDigraph createTempEWD(EdgeWeightedDigraph g, int[] set1, int[] set2){
        int V = g.V();
        int N = V+2;
        EdgeWeightedDigraph newG = new EdgeWeightedDigraph(N);

        for(int i = 0; i<V; i++){
            for(DirectedEdge e: g.adj(i)){
                int w = e.to();
                newG.addEdge(new DirectedEdge(i, w, e.weight()));
            }
        }

        for(int i = 0; i < set1.length; i++){
            int source = set1[i];
            newG.addEdge(new DirectedEdge(V, source, 0.0));
        }

        for(int i = 0; i < set2.length; i++){
            int terminal = set2[i];
            newG.addEdge(new DirectedEdge(terminal, (V+1), 0.0));
        }

        return newG;
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

    public double distBetween2Sets(){
        return this.distTo[this.distTo.length-1];
    }

    public boolean hasPathBetween2Sets(){
        return this.distTo[this.distTo.length-1] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathBetween2Sets(){
        if(!hasPathBetween2Sets()){
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        DirectedEdge e = edgeTo[edgeTo.length-1];
        e = edgeTo[e.from()];
        while(e.from() != edgeTo.length-2){
            path.push(e);
            e = edgeTo[e.from()];
        }
        // if(!path.isEmpty()){
        //     path.pop();
        // }
        return path;
    }

    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int[] set1 = {1, 5};
        int[] set2 = {6};
        var sp2sets = new ShortestPathBetween2Sets(g, set1, set2);
        double distance = sp2sets.distBetween2Sets();
        StdOut.printf("Distance between 2 sets: %.2f\n", distance);
        if(sp2sets.hasPathBetween2Sets()){
            for(DirectedEdge e: sp2sets.pathBetween2Sets()){
                StdOut.println(e);
            }
        }
    }
}