package javasrc.ch04_4;

/*
 * 4.4.8 The diameter of a digraph is the length of the maximum-length shortest 
 * path connecting two vertices. Write a DijkstraSP client that finds the diameter 
 * of a givenEdgeWeightedDigraph that has nonnegative weights.

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 */

import lib.*;

public class Diameter {
   
    public static void main(String[] args){
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));

        int V = g.V();
        double diameter = Double.NEGATIVE_INFINITY;
        Iterable<DirectedEdge> diameterEdges = null;
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                if(i != j){
                    DijkstraSP dsp = new DijkstraSP(g, i);
                    double dist = dsp.distTo(j);
                    if (dist > diameter){
                        diameter = dist;
                        diameterEdges = dsp.pathTo(j);
                    }
                }
            }
        }

        StdOut.printf("Diameter is: %.2f\n", diameter);
        for(DirectedEdge e : diameterEdges){
            StdOut.println(e.toString());
        }
    }

}