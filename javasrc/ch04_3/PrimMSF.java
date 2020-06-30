package javasrc.ch04_3;


/*
*  4.3.22 Minimum Spanning Forest using Prim. 
* Develop versions of Prim’s and Kruskal’s algorithms that compute the minimum 
* spanning forest of an edge-weighted graph that is not necessarily connected. 
* Use the connected-components API of Section 4.1 and find MSTs in each component.
* 
*/

import javasrc.ch02_4.IndexMinPQ;
import javasrc.ch01_5.UF;

import lib.*;

public class PrimMSF {
    
    private IndexMinPQ<Double> pq;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private UF uf;
    

    public PrimMSF(EdgeWeightedGraph g){
        int v = g.V();
        this.pq = new IndexMinPQ<>(v);
        this.edgeTo = new Edge[v];
        this.marked = new boolean[v];
        this.distTo = new double[v];
        this.uf = new UF(v);

        for(int i = 0; i < v; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        for(int i = 0; i < v; i++){
            if(!marked[i]){
                distTo[i] = 0.0;
                pq.insert(i, 0.0);
                while(!pq.isEmpty()){
                    visit(g, pq.delMin());
                }
            }
        }

    }

    private void visit(EdgeWeightedGraph g, int v){
        this.marked[v] = true;
        for(Edge e: g.adj(v)){
            int w = e.other(v);
            if(!marked[w]){
                if(e.weight() < distTo[w]){
                    edgeTo[w] = e;
                    distTo[w] = e.weight();
                    if(pq.contains(w)){
                        pq.changeKey(w, e.weight());
                    }else{
                        pq.insert(w, e.weight());
                    }
                    uf.union(v, w);
                }
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
        PrimMSF msf = new PrimMSF(ewg);
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