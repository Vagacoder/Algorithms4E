package javasrc.ch04_3;

/*
* 4.3.32 Specified set. Given a connected edge-weighted graph G and a specified 
* set of edges S (having no cycles), describe a way to find a minimum-weight 
* spanning tree of G among those spanning trees that contain all the edges in S.
* 

! This works for both connected and disconnected set

*/

import javasrc.ch01_3.BagX;
import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_5.UF;
import javasrc.ch02_4.MinPQ;
import lib.*;

public class SpecifiedSetKruskal {
    
    private LinkedListQueue<Edge> mst;

    public SpecifiedSetKruskal(EdgeWeightedGraph g, BagX<Edge> set){
        
        this.mst = new LinkedListQueue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        UF uf = new UF(g.V());

        for(Edge e: g.edges()){
            boolean inSet = false;
            for(Edge bagE: set){
                if(e == bagE){
                    inSet = true;
                    this.mst.enqueue(e);
                    int v = e.either();
                    int w = e.other(v);
                    if(!uf.connected(v, w)){
                        uf.union(v, w);
                    }
                    break;
                }
            }
            if(!inSet){
                pq.insert(e);
            }
        }

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

    public Iterable<Edge> edges(){
        return this.mst;
    }

    public int mstEdgeNumber(){
        return this.mst.size();
    }

    public static void main(String[] args){
        StdOut.println("1. tinyEWG.txt");
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        KruskalMST kmst = new KruskalMST(g);
        StdOut.println("Kruskal find mst:");
        for (Edge e: kmst.edges()){
            StdOut.println(e.toString());
        }

        BagX<Edge> set = new BagX<>();

        // * empty set
        SpecifiedSetKruskal ssk = new SpecifiedSetKruskal(g, set);
        StdOut.println("Specified set: empty, find mst:");
        for(Edge e: ssk.edges()){
            StdOut.println(e.toString());
        }

        // * add 0-7, 0-2, edges are connected
        StdOut.println("Specified set: 0-7, 0-2, find mst:");
        for(Edge e: g.adj(0)){
            int w = e.other(0);
            if(w == 7 || w ==2){
                set.add(e);
            }
        }
        ssk = new SpecifiedSetKruskal(g, set);
        for(Edge e: ssk.edges()){
            StdOut.println(e.toString());            
        }

        // * add 0-7, 0-2, 4-5, 6-2, edges are disconnected
        StdOut.println("Specified set: 0-7, 0-2, 4-5, 6-2, find mst:");
        for(Edge e: g.adj(4)){
            int w = e.other(4);
            if(w == 5){
                set.add(e);
            }
        }
        for(Edge e: g.adj(6)){
            int w = e.other(6);
            if(w == 2){
                set.add(e);
            }
        }
        ssk = new SpecifiedSetKruskal(g, set);
        for(Edge e: ssk.edges()){
            StdOut.println(e.toString());            
        }
    }
}