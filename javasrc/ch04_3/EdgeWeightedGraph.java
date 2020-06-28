package javasrc.ch04_3;

/*
* Edge-weighted graph data type. P.611
*  
*/

import javasrc.ch01_3.BagX;
import lib.*;

public class EdgeWeightedGraph {

    private final int V;
    private int E;
    private BagX<Edge>[] adj;

    public EdgeWeightedGraph(int V){
        this.V = V;
        this.E = 0;
        this.adj = (BagX<Edge>[]) new BagX[V];
        for(int i = 0; i < V; i++){
            this.adj[i] = new BagX<Edge>();
        }
    }

    public EdgeWeightedGraph(In in){
        this(in.readInt()); // read V and construct this graph
        int E = in.readInt(); // read E
        for (int i = 0; i < E; i++) {
            int v = in.readInt(); // read a vertex
            int w = in.readInt(); // read another vertex
            double weight = in.readDouble(); // read weight
            addEdge(new Edge(v, w, weight)); // add edge connecting them
        }
    }

    // * extra constructor from website
    public EdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return this.E;
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        this.E++;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }

    public Iterable<Edge> edges(){
        BagX<Edge> edges = new BagX<>();
        for(int i =0; i < V; i++){
            for(Edge e: this.adj[i]){
                if(e.other(i) > i){
                    edges.add(e);
                }
            }
        }
        return edges;
    }
}