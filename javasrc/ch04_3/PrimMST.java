package javasrc.ch04_3;

/*
 * Algorithm 4.7 Prim's MST algorithm (eager version). P.622
 * 
 * Proposition L. Prim’s algorithm computes the MST of any connected edge-weighted 
 * graph.
 * 
*/

import javasrc.ch02_4.IndexMinPQ;

public class PrimMST {

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

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

    public Iterable<Edge> edges(){
        // TODO
        return null;
    }

    public double weight(){
        // TODO
        return 0.0;
    }
}