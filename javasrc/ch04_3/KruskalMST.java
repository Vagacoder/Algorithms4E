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
import lib.*;

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

    // ! Note: this constructor does not work! it missed the edge of 0-2,
    // ! since 0 and 2 are in different trees, and both are marked.
    // * test constructor, using a marked[] instead of UF 
    public KruskalMST(EdgeWeightedGraph g, int l){
        // * collect mst edges
        mst = new LinkedListQueue<>();
        
        // * priority queue store all edges at beginning
        MinPQ<Edge> pq = new MinPQ<>();

        for(Edge e: g.edges()){
            pq.insert(e);
        }

        // ! NOT working
        // * using boolean[] marked to track whether 2 vertices are connected
        boolean[] marked = new boolean[g.V()];

        while(!pq.isEmpty() && mst.size() < g.V()-1){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            
            // ! here, we miss any edge connecting vertices are in different trees
            if(marked[v] && marked[w]){
                continue;
            }
            
            marked[v] = true;
            marked[w] = true;
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

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        KruskalMST mst = new KruskalMST(ewg);

        for(Edge e: mst.edges()){
            StdOut.println(e.toString());
        }

        StdOut.println("\nTest using marked[] instead of UF");
        EdgeWeightedGraph ewg1 = new EdgeWeightedGraph(new In(filename));
        KruskalMST mst1 = new KruskalMST(ewg1, 1);

        for(Edge e: mst1.edges()){
            StdOut.println(e.toString());
        }
    }
}