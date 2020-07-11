package javasrc.ch04_4;

/*
 * Algorithm 4.10 Shortest Paths in Edge-Weighted DAGs. P.660
 * 
 * Proposition S. By relaxing vertices in topological order, we can solve the 
 * single-source shortest-paths problem for edge-weighted DAGs in time proportional 
 * to E + V.
 * 
 * Proposition T. We can solve the longest-paths problem in edge-weighted DAGs in
 * time proportional to E + V.
 * Algorithm: Create a copy of the given edge-weighted DAG that is identical to 
 * the original, except that all edge weights are negated. Then the shortest path 
 * in this copy is the longest path in the original.
 * 

 
 ? Sample file: tinyEWDAG.txt, P.659

 */

import java.util.Stack;
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
        if(!hasPathTo(v)){
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }


    public static void main(String[] args){
        String filename = "data/tinyEWDAG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        AcyclicSP asp = new AcyclicSP(g, 5);
        StdOut.println("From 5 to 0");
        for(DirectedEdge e: asp.pathTo(0)){
            StdOut.println(e.toString());
        }
        StdOut.println("From 5 to 2");
        for(DirectedEdge e: asp.pathTo(2)){
            StdOut.println(e.toString());
        }
        StdOut.println("From 5 to 6");
        for(DirectedEdge e: asp.pathTo(6)){
            StdOut.println(e.toString());
        }

    }

}