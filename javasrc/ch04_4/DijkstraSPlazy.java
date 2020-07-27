package javasrc.ch04_4;

/*
*  4.4.39 Lazy implementation of Dijkstra’s algorithm. Develop an implementation 
* of the lazy version of Dijkstra’s algorithm that is described in the text.
* 


*/

import java.util.Comparator;

import javasrc.ch02_4.MinPQ;
import javasrc.ch02_4.MinPQex;

import lib.*;

public class DijkstraSPlazy {

    private class DirectedEdgeComparator implements Comparator<DirectedEdge>{

        @Override
        public int compare(DirectedEdge e1, DirectedEdge e2) {
            if(e1.weight() - e2.weight() < 0){
                return -1;
            }else if(e1.weight() - e2.weight() > 0){
                return 1;
            }else{
                return 0;
            }
        }
    }
    
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private MinPQ<DirectedEdge> pq;


    public DijkstraSPlazy(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.edgeTo = new DirectedEdge[V]; 
        this.distTo = new double[V];
        this.pq = new MinPQ<>(new DirectedEdgeComparator());

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        this.pq.insert(new DirectedEdge(s, s, 0.0));
        while(!pq.isEmpty()){
            DirectedEdge e = pq.delMin();
            StdOut.println(e);
            relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        double newDistToW = distTo[v] + e.weight();
        if (distTo[w] > newDistToW) {
            distTo[w] = newDistToW;
            edgeTo[w] = e;
            // TODO insert e to pq
            
        }
    }


    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        DijkstraSPlazy dsp = new DijkstraSPlazy(g, s);

        // for(int i = 0; i < g.V(); i++){
        //     StdOut.print(s + " to " + i);
        //     StdOut.printf(" (%4.2f): ", dsp.distTo(i));
        //     if(dsp.hasPathTo(i)){
        //         for(DirectedEdge e: dsp.pathTo(i)){
        //             StdOut.print(e + "   ");
        //         }
        //     }
        //     StdOut.println();
        // }
    }
}