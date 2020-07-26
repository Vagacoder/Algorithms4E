package javasrc.ch04_4;

/*
* 4.4.35 Bitonic shortest path. Given an edge-weighted digraph, find a bitonic 
* shortest path from s to every other vertex (if one exists). A path is bitonic 
 * if there is an intermediate vertex v such that the weights of the edges on the 
 * path from s to v are strictly increasing and the weights of the edges on the 
 * path from v to t are strictly decreasing. 
 * 
 * The path should be simple (no repeated vertices).
 * 
 
 ! Assume edge weights are distinct.
 
 */

import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class BitonicSP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public BitonicSP(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];

        for (int i = 0; i < V; i++) {
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;

        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : g.adj(v)) {
                    relaxBitonic(e);
                }
            }
        }
    }

    // *
    // ? grandParent -------> p -------> v -------> w
    // ? edgeToP edgeToV e

    private void relaxBitonic(DirectedEdge e) {
        int v = e.from();
        int w = e.to();

        DirectedEdge edgeToV = this.edgeTo[v];
        DirectedEdge edgeToP = null;

        if (edgeToV != null) {
            int p = edgeToV.from();
            // * simple path
            if(p == w){
                return;
            }

            edgeToP = this.edgeTo[p];
        } else {
            relax(e);
            return;
        }

        if (edgeToP == null && edgeToV.weight() != e.weight()) {
            relax(e);
        } else {
            if (edgeToP.weight() < edgeToV.weight() && edgeToV.weight() < e.weight()) {
                relax(e);
            } else if (edgeToP.weight() > edgeToV.weight() && edgeToV.weight() > e.weight()) {
                relax(e);
            }

        }
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

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
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
        BitonicSP bsp = new BitonicSP(g, s);

        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", bsp.distTo(i));
            if (bsp.hasPathTo(i)) {
                for (DirectedEdge e : bsp.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

        // * tester #2
        StdOut.println("\n2. mediumEWDG.txt no negative edge");
        filename = "data/mediumEWDG.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        bsp = new BitonicSP(g, s);

        for (int i = 0; i < g.V(); i++) {
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.4f): ", bsp.distTo(i));
            if (bsp.hasPathTo(i)) {
                for (DirectedEdge e : bsp.pathTo(i)) {
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }
}