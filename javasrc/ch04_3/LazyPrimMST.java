package javasrc.ch04_3;

/*
 * Lazy version or Prim's MST algorithm. P.619
 * 
 * Proposition K. (Greedy mst algorithm) The following method colors black all 
 * edges in the the MST of any connected edge-weighted graph with V vertices: 
 * starting with all edges colored gray, find a cut with no black edges, color 
 * its minimum-weight edge black, and continue until V-1 edges have been colored 
 * black.
 * 
 * Proposition L. Prim’s algorithm computes the MST of any connected edge-weighted 
 * graph.
 * 
 * Proposition M. The lazy version of Prim’s algorithm uses space proportional to 
 * E and time proportional to E*log E (in the worst case) to compute the MST of a 
 * connected edge-weighted graph with E edges and V vertices.
  
 ? Comparison table at P.628

 * 4.3.31 MST weights. Develop implementations of weight() for LazyPrimMST using 
 * a lazy strategy that iterates through the MST edges when the client calls 
 * weight(). Then develop alternate implementations that use an eager strategy 
 * that maintains a running total as the MST is computed.

*/

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch02_4.MinPQ;
import lib.*;

public class LazyPrimMST {
    
    private boolean[] marked;
    private LinkedListQueue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph g){
        this.pq = new MinPQ<Edge>();
        this.mst = new LinkedListQueue<Edge>();
        // * for lazy process
        this.marked = new boolean[g.V()];

        visit(g, 0);

        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            
            // * lazy process
            if(marked[v] && marked[w]){
                continue;
            }
            mst.enqueue(e);
            if(!marked[v]){
                visit(g, v);
            }
            if(!marked[w]){
                visit(g, w);
            }
        }
    }

    private void visit(EdgeWeightedGraph g, int v){
        marked[v] = true;
        for(Edge e: g.adj(v)){
            if(!marked[e.other(v)]){
                pq.insert(e);
            }
        }
    }

    public Iterable<Edge> edges(){
        return this.mst;
    }

    public int mstEdgeNumber(){
        return this.mst.size();
    }

    // * 4.3.31
    public double weight(){
        double weightSum = 0;
        for(Edge e: this.edges()){
            weightSum += e.weight();
        }
        return weightSum;
    }

    public static void main(String[] args){
        StdOut.println("1. tinyEWG.txt");
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        LazyPrimMST lp = new LazyPrimMST(g);
        for (Edge e: lp.edges()){
            StdOut.println(e.toString());
        }

        StdOut.println("\n2. tineyEWG3.txt");
        String filename1 = "data/tinyEWG3.txt";
        EdgeWeightedGraph g1 = new EdgeWeightedGraph(new In(filename1));
        LazyPrimMST lp1 = new LazyPrimMST(g1);
        for(Edge e: lp1.edges()){
            StdOut.println(e);
        }
    }
}