package javasrc.ch04_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * 4.3.25 Worst-case generator. Develop a reasonable generator for edge-weighted 
 * graphs with V vertices and E edges such that the running time of the lazy 
 * version of Prim’s algorithm is nonlinear. Answer the same question for the 
 * eager version.
 * 
 * 1. lazy version of Prim’s algorithm uses space proportional to E and time 
 * proportional to Elog E (in the worst case) to compute the MST of a connected 
 * edge-weighted graph with E edges and V vertices.
 * 
 * 2. The bottleneck in the algorithm is the number of edge-weight comparisons in 
 * the priority-queue methods insert() and delMin(). The number of edges on the 
 * priority queue is at most E, which gives the space bound. In the worst case, 
 * the cost of an insertion is ~lg E and the cost to delete the minimum is ~2 lg E 
 * (see Proposition Q in Chapter 2). Since at most E edges are inserted and at 
 * most E are deleted, the time bound follows.
  
 ? In lazy Prim, we use MinPQ, thus the worst case is:
 ? (1) to insert the smallest element for every insert(), thus inserted one must 
 ? swim to top of heap, which costs lg(N);

 ? (2) delMin() has to exame both children to sink exchanged first element, which
 ? cost 2lg(N);

 ? Taking (1) and (2) togethter, to get wrost case, we need add edges to pq (MinPQ
 ? in lazy Prim) in decreasing order (from max to min).

 ? (3) MinPQ in lazy Prim adds edges from EdgeWeightedGraph.adj[vertexId], whose
 ? data structure is BagX.

 ? (4) BagX is using Linked List, 
 
 ? Taking (3) and (4) together, EdgeWeightedGraph should add edges in increasing 
 ? order (from min to max);

 */

import lib.*;

public class WorstcaseLazyPrim {
    
    private EdgeWeightedGraph newG;

    public WorstcaseLazyPrim(EdgeWeightedGraph g){
        int V = g.V();
        ArrayList<Edge> edges = new ArrayList<>();
        for(Edge e: g.edges()){
            edges.add(e);
        }
        Collections.sort(edges);
        // for(Edge e: edges){
        //     StdOut.println(e);
        // }
        
        newG = new EdgeWeightedGraph(V);
        for(Edge e: edges){
            newG.addEdge(e);
        }
    }

    public EdgeWeightedGraph getWorstGraph(){
        return newG;
    }

    public static void main(String[] args){
        String filename = "data/mediumEWG.txt";

        StdOut.println("1. Ordinary graph");
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        Stopwatch timer1 = new Stopwatch();
        LazyPrimMST lp = new LazyPrimMST(g);
        double time1 = timer1.elapsedTime();
        // for (Edge e: lp.edges()){
        //     StdOut.println(e.toString());
        // }
        StdOut.println("Time 1: " + time1);

        StdOut.println("\n2. Worst case");
        WorstcaseLazyPrim worst = new WorstcaseLazyPrim(g);
        EdgeWeightedGraph worstG = worst.getWorstGraph();
        Stopwatch timer2 = new Stopwatch();
        LazyPrimMST lpw = new LazyPrimMST(worstG);
        double time2 = timer2.elapsedTime();
        // for (Edge e: lpw.edges()){
        //     StdOut.println(e.toString());
        // }
        StdOut.println("Time 2: " + time2);
    }
}