package javasrc.ch04_3;

/*
 * 4.3.15 Given an MST for an edge-weighted graph G and a new edge e with weight 
 * w, describe how to find an MST of the new graph in time proportional to V.
 
 ? Add a new edge will create a UNIQUE cycle, remove the max weight edge of thta cycle.
  
 * 4.3.16 Given an MST for an edge-weighted graph G and a new edge e, write a 
 * program that determines the range of weights for which e is in an MST.

 */

import lib.*;

public class ex4_3_16 {
    
    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        LazyPrimMST lpmst = new LazyPrimMST(ewg);

        EdgeWeightedGraph newEwg = new EdgeWeightedGraph(ewg.V());
        for(Edge e: lpmst.edges()){
            newEwg.addEdge(e);
        }
        
    }
}