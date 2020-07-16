package javasrc.ch04_4;

/*
 * 4.4.7 Develop a version of DijkstraSP that supports a client method that returns 
 * a second shortest path from s to t in an edge-weighted digraph (and returns 
 * null if there is only one shortest path from s to t).
  
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 ! this algorithm does not guarantee SECOND SHORTEST path

 */

import java.util.Stack;
import javasrc.ch02_4.IndexMinPQ;
import lib.*;

public class DijkstraSP2nd {

    private DirectedEdge[] edgeTo;
    private DirectedEdge[] edgeTo2nd;
    private double[] distTo;
    private double[] distTo2nd;
    private double[] diff;
    private IndexMinPQ<Double> pq;

    public DijkstraSP2nd(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        this.edgeTo = new DirectedEdge[V];
        this.edgeTo2nd = new DirectedEdge[V];
        this.distTo = new double[V];
        this.distTo2nd = new double[V];
        this.diff = new double[V];
        this.pq = new IndexMinPQ<>(V);

        for (int i = 0; i < V; i++) {
            this.distTo[i] = Double.POSITIVE_INFINITY;
            this.distTo2nd[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        // distTo2nd[s] = 0;

        this.pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(g, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            double newDistToW = this.distTo[v] + e.weight();
            if (this.distTo[w] > newDistToW) {
                this.distTo2nd[w] = this.distTo[w];
                this.distTo[w] = newDistToW;
                this.diff[w] = distTo2nd[w] - distTo[w];

                this.edgeTo2nd[w] = this.edgeTo[w];
                this.edgeTo[w] = e;

                if (pq.contains(w)) {
                    pq.changeKey(w, newDistToW);
                } else {
                    pq.insert(w, newDistToW);
                }
            }else if(this.distTo2nd[w] > newDistToW){
                this.distTo2nd[w] = newDistToW;
                this.diff[w] = distTo2nd[w] - distTo[w];

                this.edgeTo2nd[w] = e;
            }
        }
    }

    public double distTo(int v) {
        return this.distTo[v];
    }

    public double distTo2nd(int v){
        return this.distTo2nd[v];
    }

    public boolean hasPathTo(int v) {
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    public boolean hasPathTo2nd(int v) {
        return this.distTo2nd[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public Iterable<DirectedEdge> pathTo2nd(int v) {
        if (!hasPathTo2nd(v)) {
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        
        // int vMinDiff = v;
        // double minDiff = this.diff[v];

        // int curV = v;
        // DirectedEdge e = edgeTo2nd[curV];
        // while(e != null){

        //     curV = e.from();
        //     e = edge
        // }


        // ! Note: last statement in for loop: e = edgeTo[e.from()]; NOT edgeTo2nd[]
        for (DirectedEdge e = edgeTo2nd[v]; e != null; e = edgeTo[e.from()]) {
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
        DijkstraSP2nd dsp2 = new DijkstraSP2nd(g, s);

        StdOut.println("Shortest paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", dsp2.distTo(i));
            if (dsp2.hasPathTo(i)) {
                for (DirectedEdge e : dsp2.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

        StdOut.println("2nd shortest paths:");
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", dsp2.distTo2nd(i));
            if (dsp2.hasPathTo2nd(i)) {
                for (DirectedEdge e : dsp2.pathTo2nd(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }

}