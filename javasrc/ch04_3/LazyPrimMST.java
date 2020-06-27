package javasrc.ch04_3;

/*
 * Lazy version or Prim's MST algorithm. P.619
 * 
 * Proposition L. Prim’s algorithm computes the MST of any connected edge-weighted 
 * graph.
 * 
 * Proposition M. The lazy version of Prim’s algorithm uses space proportional to 
 * E and time proportional to E*log E (in the worst case) to compute the MST of a 
 * connected edge-weighted graph with E edges and V vertices.
 * 
 * 
 * 
*/

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch02_4.MinPQ;
// import lib.*;

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

    public double weight(){
        // TODO
        return -1;
    }
}