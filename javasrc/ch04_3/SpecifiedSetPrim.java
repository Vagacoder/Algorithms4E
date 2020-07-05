package javasrc.ch04_3;

/*
* 4.3.32 Specified set. Given a connected edge-weighted graph G and a specified 
* set of edges S (having no cycles), describe a way to find a minimum-weight 
* spanning tree of G among those spanning trees that contain all the edges in S.
* 
 * algorithm: using lazy prim. 
 * (1) Mark all vertices visited in set;
 * (2) visit() all vertices to add all connecting edges to pq
 * (3) continue regular lazy prim
 
 ! This only works for all edges in specified set are connected!
 ! Need use Kruskal algorithm for non-connected set.

 */

import javasrc.ch01_3.BagX;
import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch02_4.MinPQ;
import lib.*;

public class SpecifiedSetPrim {
    
    private boolean[] visited;
    private MinPQ<Edge> pq;
    private LinkedListQueue<Edge> mst;

    public SpecifiedSetPrim(EdgeWeightedGraph g, BagX<Edge> set){
        this.pq = new MinPQ<>();
        this.mst = new LinkedListQueue<>();
        this.visited = new boolean[g.V()];

        for(Edge e: set){
            int v = e.either();
            int w = e.other(v);
            visit(g, v);
            visit(g, w);
            // * not add e to pq, since both ends (v and w) are visited

            mst.enqueue(e);
        }

        if(pq.isEmpty()){
            visit(g, 0);
        }

        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            
            // * lazy process
            if(visited[v] && visited[w]){
                continue;
            }
            mst.enqueue(e);
            if(!visited[v]){
                visit(g, v);
            }
            if(!visited[w]){
                visit(g, w);
            }
        }

    }

    private void visit(EdgeWeightedGraph g, int v){
        visited[v] = true;
        for(Edge e: g.adj(v)){
            if(!visited[e.other(v)]){
                pq.insert(e);
            }
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
        LazyPrimMST lp = new LazyPrimMST(g);
        StdOut.println("Lazy prim find mst:");
        for (Edge e: lp.edges()){
            StdOut.println(e.toString());
        }

        BagX<Edge> set = new BagX<>();

        // * empty set
        SpecifiedSetPrim ss = new SpecifiedSetPrim(g, set);
        StdOut.println("Specified set: empty, find mst:");
        for(Edge e: ss.edges()){
            StdOut.println(e.toString());            
        }

        // * add 0-7, 0-2
        StdOut.println("Specified set: 0-7, 0-2, find mst:");
        for(Edge e: g.adj(0)){
            int w = e.other(0);
            if(w == 7 || w ==2){
                set.add(e);
            }
        }
        ss = new SpecifiedSetPrim(g, set);
        for(Edge e: ss.edges()){
            StdOut.println(e.toString());            
        }

        // * add 0-7, 0-2, 4-5, 6-2
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
        ss = new SpecifiedSetPrim(g, set);
        for(Edge e: ss.edges()){
            StdOut.println(e.toString());            
        }
    }
}