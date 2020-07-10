package javasrc.ch04_4;

/*
 * Algorithm 4.10 Shortest Paths in Edge-Weighted DATGs. P.660
 * 
 * Proposition S. By relaxing vertices in topological order, we can solve the 
 * single-source shortest-paths problem for edge-weighted DAGs in time proportional 
 * to E + V.
 * 
 * 
 * 

 ? Sample file: tinyEWDAG.txt, P.659

 */

import lib.*;

public class AcyclicSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.edgeTo = new DirectedEdge[V];
        this.distTo = new double[V];

        for(int i = 0; i < V; i++){
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        TopologicalEWDG topo = new TopologicalEWDG(g);

        for(int v: topo.order()){
            relax(g, v);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v){
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
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
        // TODO
        return null;
    }


    public static void main(String[] args){
        String filename = "data/tinyEWDAG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        AcyclicSP asp = new AcyclicSP(g, 5);
    }

}