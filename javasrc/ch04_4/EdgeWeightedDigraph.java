package javasrc.ch04_4;

/*
 * Edge-weighted Digraph (Directed graph). P.643

 ? sample file tinyEWDG.txt (tinyEWD.txt in textbook), P.653

*/

import javasrc.ch01_3.BagX;
import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class EdgeWeightedDigraph {

    private final int V;
    private int E;
    private BagX<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V){
        this.V = V;
        this.E = 0;
        this.adj = (BagX<DirectedEdge>[]) new BagX[V];
        for(int i=0; i< V; i++){
            this.adj[i] = new BagX<DirectedEdge>();
        }
    }

    public EdgeWeightedDigraph(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0; i < E; i++){
            int from = in.readInt();
            int to = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(from, to, weight));
        }
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return this.E;
    }

    public void addEdge(DirectedEdge e){
        this.adj[e.from()].add(e);
        this.E++;
    }

    public Iterable<DirectedEdge> adj(int v){
        return this.adj[v];
    }

    public Iterable<DirectedEdge> edges(){
        // * change BagX to LinklistQueue to get edges list in order of insertion
        LinkedListQueue<DirectedEdge> result = new LinkedListQueue<>();
        for(int i = 0; i <V; i++){
            for(DirectedEdge e: this.adj[i]){
                result.enqueue(e);
            }
        }
        return result;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(new In(filename));
        for(DirectedEdge e: ewdg.edges()){
            StdOut.println(e);
        }
    }
}