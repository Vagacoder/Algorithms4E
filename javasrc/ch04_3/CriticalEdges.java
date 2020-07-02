package javasrc.ch04_3;


/*
* 4.3.26 Critical edges. An MST edge whose deletion from the graph would cause 
* the MST weight to increase is called a critical edge. Show how to find all 
* critical edges in a graph in time proportional to ElogE . 
* Note : This question assumes that edge weights are not necessarily distinct 
* (otherwise all edges in the MST are critical).
* 

? based on lazy prim

*/

import java.util.ArrayList;

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch02_4.MinPQ;

import lib.*;

public class CriticalEdges {
    
    private boolean[] marked;
    private LinkedListQueue<Edge> mst;
    private MinPQ<Edge> pq;
    private ArrayList<Edge> criticals;

    public CriticalEdges(EdgeWeightedGraph g){
        int V = g.V();
        this.pq = new MinPQ<>();
        this.mst = new LinkedListQueue<>();
        this.marked = new boolean[V];
        this.criticals = new ArrayList<>();

        visit(g, 0);

        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            if(marked[v] && marked[w]){
                continue;
            }
            mst.enqueue(e);

            // * handle critical edge
            if(pq.isEmpty()){
                criticals.add(e);
            }else{
                Edge eNext = pq.min();
                if (e.weight() != eNext.weight()){
                    // int vNext = eNext.either();
                    // int wNext = eNext.other(vNext);
                    // if(!(marked[vNext] && marked[wNext])){
                        criticals.add(e);
                    // }
                }
            }

            if(!marked[v]){
                visit(g, v);
            }
            if(!marked[w]){
                visit(g, w);
            }

        }


    }

    private void visit(EdgeWeightedGraph g, int v){
        marked[v] = true;
        for(Edge e: g.adj(v)){
            if(!marked[e.other(v)]){
                pq.insert(e);
            }
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