package javasrc.ch04_4;

/*
 * 4.4.30 All-pairs shortest paths in digraphs without negative cycles. 
 * 
 * (1) Articulate an API like the one implemented on page 656 for the all-pairs 
 * shortest-paths problem in graphs with no negative cycles. 
 * 
 * (2) Develop an implementation that runs a version of Bellman-Ford to identify 
 * real-valued weights pi[v] such that for any edge v->w, the edge weight plus 
 * the difference between pi[v] and pi[w] is nonnegative. Then use these weights 
 * to reweight the graph, so that Dijkstra’s algorithm is effective for finding 
 * all shortest paths in the reweighted graph.
 * 
 * see RealValueWeightEWDigraph.java
 * 

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 */

import lib.*;


public class AllPairsSPnoNc {

    private DijkstraSP[] all;

    public AllPairsSPnoNc(EdgeWeightedDigraph g){
        int V = g.V();
        all = new DijkstraSP[V];
        for(int i = 0; i < V; i++){
            EdgeWeightedDigraph newG = new RealValueWeightEWDigraph(g, i).getNewDigraph();
            all[i] = new DijkstraSP(newG, i);
        }
    }

    public double dist(int s, int t){
        return all[s].distTo(t);
    }

    public Iterable<DirectedEdge> path(int s, int t){
        return all[s].pathTo(t);
    }

    public static void main(String[] args){
        String filename = "data/tinyEWDG.txt";
        var g = new EdgeWeightedDigraph(new In(filename));
        var allpairs = new AllPairsSPnoNc(g);

        for(DirectedEdge e: allpairs.path(5, 6)){
            StdOut.println(e);
        }

    }
}