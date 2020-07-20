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
 
 ? Sample file: 
 ? tinyEWDAG.txt, P.659
 ? tinyEWDAGn.txt, modified from tinyEWDAG, 6->4 -0.93
 
 */

import javasrc.ch01_3.LinkedListStackX;
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

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }


    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDAG.txt no negative edge, no cycle");
        String filename = "data/tinyEWDAG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 5;
        AcyclicSP asp = new AcyclicSP(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", asp.distTo(i));
            if(asp.hasPathTo(i)){
                for(DirectedEdge e: asp.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }
        
        // * tester #2
        StdOut.println("2. tinyEWDAGn.txt with negative edge");
        filename = "data/tinyEWDAGn.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 5;
        asp = new AcyclicSP(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", asp.distTo(i));
            if(asp.hasPathTo(i)){
                for(DirectedEdge e: asp.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

        // * tester #3
        StdOut.println("3. tinyEWDG.txt no negative edge, with cycle, will fail");
        filename = "data/tinyEWDG.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        asp = new AcyclicSP(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", asp.distTo(i));
            if(asp.hasPathTo(i)){
                for(DirectedEdge e: asp.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

    }

}