package javasrc.ch04_4;


/*
 * 4.4.36 Neighbors. Develop an SP client that finds all vertices within a given 
 * distance d of a given vertex in a given edge-weighted digraph. The running 
 * time of your method should be proportional to the number of vertices and edges 
 * in the subgraph induced by those vertices and the edges incident to them, plus 
 * V (to initialize data structures).
 
 ? Algorithm: when calculate distTo[w], compare it with the given maximum distance
 
 */

import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class NeighborsSP {
    
    private double maxDist;
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public NeighborsSP(EdgeWeightedDigraph g, int s, double maxDist){
        this.maxDist = maxDist;
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;

        for(int i = 0; i < V; i++){
            for(int v = 0; v < V; v++){
                for(DirectedEdge e : g.adj(v)){
                    relax(e);
                }
            }
        }
    }
    
    private void relax(DirectedEdge e){
        int v = e.from();
        int w = e.to();
        double newDistToW = distTo[v] + e.weight();
        if(distTo[w] > newDistToW && newDistToW < maxDist){
            distTo[w] = newDistToW;
            edgeTo[w] = e;
        }
    }

    public boolean hasPathTo(int v){
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v){
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v){
        if(!hasPathTo(v)){
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        DirectedEdge e = edgeTo[v];
        while( e!=null){
            path.push(e);
            e = edgeTo[e.from()];
        }
        
        return path;
    }

    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        NeighborsSP nsp = new NeighborsSP(g, s, 1.0);

        for(int i =0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", nsp.distTo(i));
            if (nsp.hasPathTo(i)) {
                for (DirectedEdge e : nsp.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }
}