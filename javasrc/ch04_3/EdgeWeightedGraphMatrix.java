package javasrc.ch04_3;

/*
*  4.3.10 Develop an EdgeWeightedGraph implementation for dense graphs that uses 
* an adjacency-matrix (two-dimensional array of weights) representation. Disallow 
* parallel edges.
* 
*/

import javasrc.ch01_3.BagX;
import lib.*;

public class EdgeWeightedGraphMatrix {
    
    private final int V;
    private int E;
    private double[][] edges;

    public EdgeWeightedGraphMatrix(int V){
        this.V = V;
        this.E = 0;
        this.edges = new double[V][V];
        for(int i =0;i < V; i++){
            for(int j = 0; j <V; j++){
                edges[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public EdgeWeightedGraphMatrix(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0; i < E; i++){
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(v, w, weight);
        }
    }

    public void addEdge(int v, int w, double weight){
        this.edges[v][w] = weight;
        this.edges[w][v] = weight;
        this.E++;
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        double weight = e.weight();
        addEdge(v, w, weight);
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return this.E;
    }

    public Iterable<Edge> adj(int v){
        BagX<Edge> adj = new BagX<>();
        for(int j = 0; j<V; j++){
            double weight = this.edges[v][j];
            if(weight < Double.POSITIVE_INFINITY){
                adj.add(new Edge(v, j, weight));
            }
        }
        return adj;
    }

    public Iterable<Edge> edges(){
        BagX<Edge> edges = new BagX<>();
        for(int i = 0; i < V; i++){
            for(int j = i; j <V; j++){
                double weight = this.edges[i][j];
                if(weight < Double.POSITIVE_INFINITY){
                    edges.add(new Edge(i, j, weight));
                }
            }
        }
        return edges;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraphMatrix ewgm = new EdgeWeightedGraphMatrix(new In(filename));
        for(Edge e: ewgm.edges()){
            StdOut.println(e.toString());
        }
        StdOut.println();

    }
}