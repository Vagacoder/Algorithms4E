package javasrc.ch04_4;

/*
* 4.4.40 Bottleneck SPT. Show that an MST of an undirected graph is equivalent 
* to a bottleneck SPT of the graph: For every pair of vertices v and w, it gives 
* the path connecting them whose longest edge is as short as possible.
 * 
 
 ? https://homes.cs.washington.edu/~anderson/iucee/Slides_421_06/Lecture08_09_10.pdf
 
 * Bottleneck distance for a path (SP or other paths), to be the maximum cost edge
 * along the path.
 
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 
 */

import javasrc.ch01_3.LinkedListStackX;
import javasrc.ch02_4.IndexMinPQ;

import lib.*;

public class BottleneckSP {
    
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    

    public BottleneckSP(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.pq = new IndexMinPQ<>(V);

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        this.distTo[s] = Double.NEGATIVE_INFINITY;

        this.pq.insert(s, Double.NEGATIVE_INFINITY);
        while(!pq.isEmpty()){
            relax(g, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph g, int v){
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            double maxCostBeforeW;
            if(distTo[v] > e.weight()){
                maxCostBeforeW = distTo[v];
            }else{
                maxCostBeforeW = e.weight();
            }

            if(distTo[w] > maxCostBeforeW){
                distTo[w] = maxCostBeforeW;
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
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        BottleneckSP bsp = new BottleneckSP(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", bsp.distTo(i));
            if(bsp.hasPathTo(i)){
                for(DirectedEdge e: bsp.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
    }
}