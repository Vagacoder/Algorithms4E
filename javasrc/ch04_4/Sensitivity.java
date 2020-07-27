package javasrc.ch04_4;

/*
 * 4.4.38 Sensitivity. Develop an SP client that performs a sensitivity analysis 
 * on the edge-weighted digraph’s edges with respect to a given pair of vertices 
 * s and t: Compute a V-by-V boolean matrix such that, for every v and w, the 
 * entry in row v and column w is true if v->w is an edge whose weight can be 
 * increased without the shortest-path length from v to w being increased and is 
 * false otherwise.
  
 ? Algorithm:  
 ? (1) Compute SP tree,
 ? (2) Any edges in SP tree are sensitive, others are not;

 */

import lib.*;

public class Sensitivity {
    
    private boolean[][] sensitive;

    public Sensitivity(EdgeWeightedDigraph g, int s, int t){
        int V = g.V();
        this.sensitive = new boolean[V][V];

        DijkstraSP dsp = new DijkstraSP(g, s);
        for(DirectedEdge e: dsp.pathTo(t)){
            int v = e.from();
            int w = e.to();
            this.sensitive[v][w] = true;
        }
    }

    public boolean isSensitive(int v, int w){
        return this.sensitive[v][w];
    }

}