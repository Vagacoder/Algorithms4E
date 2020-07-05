package javasrc.ch04_3;

/*
* Algorithm 4.7 Prim's MST algorithm (eager version). P.622
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
 * Proposition N. The eager version of Prim’s algorithm uses extra space proportional 
 * to V and time proportional to E log V (in the worst case) to compute the MST 
 * of a connected edge-weighted graph with E edges and V vertices.
 
 ? Comparison table at P.628

 * 4.3.21 Provide an implementation of edges() for PrimMST (page 622).
 * 
 * 4.3.31 MST weights. Develop implementations of weight() for PrimMST using a 
 * lazy strategy that iterates through the MST edges when the client calls weight(). 
 * Then develop alternate implementations that use an eager strategy that maintains 
 * a running total as the MST is computed.
 * 

 */

import javasrc.ch02_4.IndexMinPQ;
import javasrc.ch01_3.LinkedListQueue;

import lib.*;

public class PrimMST {

    // ! Note: this.pq and this.edgeTo[] are working together, each provides a 
    // ! part of functions we need

    // * eligible crossing edges. This stores only which vertex to (from mst)
    // * but providing ability to find min distance.
    private IndexMinPQ<Double> pq; 
    
    // * This store edges of mst, which has more infor than this.pq, but no sorting.
    private Edge[] edgeTo;

    // * store the weighte of edge
    // ? Is this redundant? weight can be obtained from edgeTo[]
    private double[] distTo;
    private boolean[] marked;

    public PrimMST(EdgeWeightedGraph g){
        this.edgeTo = new Edge[g.V()];
        this.distTo = new double[g.V()];
        this.marked = new boolean[g.V()];

        for(int v = 0; v < g.V(); v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        this.pq = new IndexMinPQ<>(g.V());

        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while(!pq.isEmpty()){
            visit(g, pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph g, int v){
        this.marked[v] = true;
        for(Edge e: g.adj(v)){
            int w = e.other(v);
            if(marked[w]){
                continue;
            }
            // * if w is not visited
            // * if distance from v to w is smaller than stored min distance to w 
            // * (stored in distTo[]), then this is min distance from mst to 
            // * not-in-mst, store w and distance in edgeTo[] and distTo[], as 
            // * well as in pq(w, distTo w).
            // * If edge to w is already in pq, need change the key (followed with reheap),  
            // * if not in pq, just insert that edge.
            if(e.weight() < distTo[w]){
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if(pq.contains(w)){
                    pq.changeKey(w, distTo[w]);
                }else{
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }


    // * 4.3.21
    public Iterable<Edge> edges(){
        LinkedListQueue<Edge> edges = new LinkedListQueue<>();

        // ! Or i starts at 1, then no need check whether e is null or not
        for(int i =0; i<this.edgeTo.length;i++){
            Edge e = this.edgeTo[i];
            if(e != null){
                edges.enqueue(e);
            }
        }
        return edges;
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
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        PrimMST mst = new PrimMST(ewg);

        for(Edge e: mst.edges()){
            StdOut.println(e.toString());
        }
    }
}