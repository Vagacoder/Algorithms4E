package javasrc.ch04_4;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Helper for ex 4.4.30 
 * 
 */

import lib.*;

public class RealValueWeightEWDigraph {

    private EdgeWeightedDigraph newG;

    public RealValueWeightEWDigraph(EdgeWeightedDigraph g, int s) {
        int V = g.V();
        newG = new EdgeWeightedDigraph(V);

        // * solution #1, directly use Bellman-ford sp, may not correct
        // var bfsp = new BellmanFordSP(g, s);

        // for(DirectedEdge e: g.edges()){
        // int v = e.from();
        // int w = e.to();
        // double distToV = bfsp.distTo(v);
        // double distToW = bfsp.distTo(w);
        // double realValueWeight = e.weight() + distToV - distToW;
        // newG.addEdge(new DirectedEdge(v, w, realValueWeight));
        // }

        // * solution #2,

        // * 2.1 Bellman-ford general,
        var distTo = new double[V];
        var edgeTo = new DirectedEdge[V];
        // var onQueue = new boolean[V];
        // var queue = new LinkedListQueue<Integer>();

        for (int i = 0; i < V; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        // queue.enqueue(s);
        // onQueue[s] = true;

        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : g.adj(v)) {
                    if (distTo[v] == Double.POSITIVE_INFINITY) {
                        continue;
                    }
                    int w = e.to();
                    double newDistToW =  e.weight() + distTo[v];
                    if (distTo[w] > newDistToW) {
                        distTo[w] = newDistToW;
                        edgeTo[w] = e;
                    }
                }
            }
        }

        // * reweight (reconstruct) digraph using real-valued wieghts
        for (int w = 0; w < V; w++) {
            if (edgeTo[w] != null) {
                int v = edgeTo[w].from();
                double distToV = distTo[v];
                double distToW = distTo[w];
                double realValueWeight = edgeTo[w].weight() + distToV - distToW;
                newG.addEdge(new DirectedEdge(v, w, realValueWeight));
            }
        }
    }

    public EdgeWeightedDigraph getNewDigraph() {
        return this.newG;
    }

}