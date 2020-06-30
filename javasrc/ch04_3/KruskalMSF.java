package javasrc.ch04_3;

/*
*  4.3.22 Minimum Spanning Forest using Kruskal. 
* Develop versions of Prim’s and Kruskal’s algorithms that compute the minimum 
* spanning forest of an edge-weighted graph that is not necessarily connected. 
* Use the connected-components API of Section 4.1 and find MSTs in each component.
* 
*/

import javasrc.ch01_5.UF;
import javasrc.ch02_4.MinPQ;
import lib.*;

public class KruskalMSF {
    
    private UF uf;

    public KruskalMSF(EdgeWeightedGraph g){
        int V = g.V();

        // boolean[] marked = new boolean[V];
        this.uf = new UF(V);

        MinPQ<Edge> pq = new MinPQ<>();
        for(Edge e: g.edges()){
            pq.insert(e);
        }

        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            if(!uf.connected(v, w)){
                uf.union(v, w);
            }
        }
    }

    public boolean isInSameMST(int v, int w){
        return uf.connected(v, w);
    }

    public int MSTRootId(int v){
        return uf.find(v);
    }

    public int numberOfMST(){
        return uf.count();
    }

    public static void main(String[] args){
        String filename = "data/tinyEWGforest1.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        KruskalMSF msf = new KruskalMSF(ewg);
        StdOut.println("Number of components: " + msf.numberOfMST());

        StdOut.println("Vertex: Component root ID");
        for(int i = 0; i < ewg.V(); i ++){
            StdOut.println(i + ": " + msf.MSTRootId(i));
        }

        StdOut.println("Is 1 and 4 in same tree: " + msf.isInSameMST(1, 4));
        StdOut.println("Is 5 and 7 in same tree: " + msf.isInSameMST(5, 7));
        StdOut.println("Is 6 and 8 in same tree: " + msf.isInSameMST(6, 8));
    }
}