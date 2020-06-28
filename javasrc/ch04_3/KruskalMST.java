package javasrc.ch04_3;

/*
 * Algorithm 4.8 Kruskal's algorithm. P.627.
 * 
 * Proposition K. (Greedy mst algorithm) The following method colors black all 
 * edges in the the MST of any connected edge-weighted graph with V vertices: 
 * starting with all edges colored gray, find a cut with no black edges, color 
 * its minimum-weight edge black, and continue until V-1 edges have been colored 
 * black.
 * 
 * Proposition O. Kruskal’s algorithm computes the MST of any connected edge-
 * weighted graph.
 * 
 * Proposition N (continued). Kruskal’s algorithm uses space proportional to E 
 * and time proportional to E log E (in the worst case) to compute the MST of an 
 * edge-weighted connected graph with E edges and V vertices.
 
 ! Kruskal’s algorithm is generally slower than Prim’s algorithm because it has 
 ! to do a connected() operation for each edge
    
 ? Comparison table at P.628

 * 
*/

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_5.UF;
import javasrc.ch02_4.MinPQ;

public class KruskalMST {
    
    private LinkedListQueue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph g){
        // * collect mst edges
        mst = new LinkedListQueue<>();
        
        // * priority queue store all edges at beginning
        MinPQ<Edge> pq = new MinPQ<>();

        for(Edge e: g.edges()){
            pq.insert(e);
        }

        // * union-find for testing whether 2 vertices are connected
        UF uf = new UF(g.V());

        while(!pq.isEmpty() && mst.size() < g.V()-1){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            
            if(uf.connected(v, w)){
                continue;
            }
            
            uf.union(v, w);
            mst.enqueue(e);
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