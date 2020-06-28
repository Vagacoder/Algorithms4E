package javasrc.ch04_3;

/*
 * Edge-weighted graph data type. P.611
 * 
 * 4.3.9 Implement the constructor for EdgeWeightedGraph that reads an edge-weighted 
 * graph from the input stream, by suitably modifying the constructor from Graph 
 * (see page 526).
 * 
 * 
 *
  
 ? Sample file tinyEWG.txt at P.609, 617, 621, 624
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

    // * 4.3.9
    public EdgeWeightedGraph(EdgeWeightedGraph g){
        this(g.V());

        // * implementation #1 (Note the compare i <= w, its same as edges())
        // for(int i = 0; i < g.V();i++){
        //     for(Edge e: g.adj[i]){
        //         int w = e.other(i);
        //         if(i <= w){
        //             double weight = e.weight();
        //             addEdge(new Edge(i, w, weight));
        //         }
        //     }
        // }

        // * implementation #2
        // for(Edge e: g.edges()){
        //     this.addEdge(e);
        // }

        // * implementation #3
        this.E = g.E;
        for(int i = 0; i < g.V(); i++){
            for(Edge e: g.adj[i]){
                this.adj[i].add(e);
            }
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
                // ! remove duplicated edges
                if(e.other(i) > i){
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        for(Edge e: ewg.edges()){
            StdOut.println(e.toString());
        }
        StdOut.println();

        EdgeWeightedGraph ewg1 = new EdgeWeightedGraph(ewg);
        for(Edge e: ewg1.edges()){
            StdOut.println(e.toString());
        }
    }
}