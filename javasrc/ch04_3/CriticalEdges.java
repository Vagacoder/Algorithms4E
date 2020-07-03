package javasrc.ch04_3;

/*
* 4.3.26 Critical edges. An MST edge whose deletion from the graph would cause 
* the MST weight to increase is called a critical edge. Show how to find all 
* critical edges in a graph in time proportional to ElogE . 
* Note : This question assumes that edge weights are not necessarily distinct 
* (otherwise all edges in the MST are critical).
* 

? based on Kruskal, tried lazy Prim which did not work

*/

import java.util.ArrayList;

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_5.UF;
import javasrc.ch02_4.MinPQ;

import lib.*;

public class CriticalEdges {
    
    private ArrayList<Edge> criticals;
    
    // ? not need to be instance variable ?
    private LinkedListQueue<Edge> mst;
    private MinPQ<Edge> pq;

    public CriticalEdges(EdgeWeightedGraph g){

        // * using Kruskal
        int V = g.V();
        this.mst = new LinkedListQueue<>();
        this.criticals = new ArrayList<>();
        this.pq = new MinPQ<>();

        for(Edge e: g.edges()){
            pq.insert(e);
        }

        UF uf = new UF(V);

        Edge lastMstE = null;
        
        while(!pq.isEmpty() && mst.size() < V-1 ){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            if(uf.connected(v, w)){
                continue;
            }

            uf.union(v, w);
            mst.enqueue(e);

            if(lastMstE != null){
                if(e.weight()!= lastMstE.weight()){
                    this.criticals.add(lastMstE);
                }
            }
            lastMstE = e;
        }
        if(this.criticals.size()>0 && 
            criticals.get(criticals.size()-1).weight() != lastMstE.weight()){
            criticals.add(lastMstE);
        }

    }

    public Iterable<Edge> getCriticals(){
        return this.criticals;
    }



    public static void main(String[] args){
        StdOut.println("1. tinyEWG.txt");
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        CriticalEdges c = new CriticalEdges(g);
        for(Edge e: c.getCriticals()){
            StdOut.println(e);
        }

        StdOut.println("\n2. tineyEWG3.txt");
        String filename1 = "data/tinyEWG3.txt";
        EdgeWeightedGraph g1 = new EdgeWeightedGraph(new In(filename1));
        CriticalEdges c1 = new CriticalEdges(g1);
        for(Edge e: c1.getCriticals()){
            StdOut.println(e);
        }
    }

}