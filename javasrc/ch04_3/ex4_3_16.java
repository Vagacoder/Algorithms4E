package javasrc.ch04_3;

import java.util.ArrayList;
import java.util.Collections;

/*
 * 4.3.15 Given an MST for an edge-weighted graph G and a new edge e with weight 
 * w, describe how to find an MST of the new graph in time proportional to V.
 
 ? Add a new edge will create a UNIQUE cycle, remove the max weight edge of thta cycle.
  
 * 4.3.16 Given an MST for an edge-weighted graph G and a new edge e, write a 
 * program that determines the range of weights for which e is in an MST.

 ? Write a helper class of EWGcycle.java
 ? Sample file tinyEWG.txt at P.624

 */

import lib.*;

public class ex4_3_16 {
    
    public static void main(String[] args){
        // * use tinyEWG, find MST using lazy Prim algorithm
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        LazyPrimMST lpmst = new LazyPrimMST(ewg);

        // * construct a new edge-weighted graph of MST
        EdgeWeightedGraph newEwg = new EdgeWeightedGraph(ewg.V());
        for(Edge e: lpmst.edges()){
            newEwg.addEdge(e);
        }
        
        // * add a new edge (from 1 to 3, whose weight is 0.1) to MST, forming a cycle
        // ! for original edge 1 to 3 (in tinyEWG.txt), weight is 0.29, will not in new MST;
        newEwg.addEdge(new Edge(1, 3, 0.1));

        // * using EWG Cylce to find the UNIQUE cycle. Add all weights (in cycle) to a list
        EWGcycle cycle = new EWGcycle(newEwg);
        ArrayList<Double> weights = new ArrayList<>();

        // * print weights in cycle, if new edge in new MST, it must smaller than largest
        // * since for new MST, the largest weight in cycle should be removed.
        for(Edge e: cycle.cycle()){
            StdOut.println(e.toString());
            weights.add(e.weight());
        }

        // * sort weights, and remove largest one
        StdOut.println("\nNew MST edges' weights:");
        Collections.sort(weights);
        weights.remove(weights.size()-1);
        for(double w: weights){
            StdOut.println(w);
        }
    }
}