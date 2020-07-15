package javasrc.ch04_4;

/*
* 4.4.3 Develop an implementation of EdgeWeightedDigraph for dense graphs that 
* uses an adjacency-matrix (two-dimensional array of weights) representation 
* (see Exercise 4.3.9). Ignore parallel edges.
* 
* Note: for parallel edges, latter one overwries previous one.
*/

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class EdgeWeightedDigraphMatrix {
    
    private final int V;
    private int E;
    private double[][] edges;

    public EdgeWeightedDigraphMatrix(int V){
        this.V = V;
        this.E = 0;
        this.edges = new double[V][V];
        for(int from = 0; from < V ; from++){
            for(int to = 0; to < V; to++){
                this.edges[from][to] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public EdgeWeightedDigraphMatrix(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0; i < E; i++){
            int from = in.readInt();
            int to = in.readInt();
            double weight = in.readDouble();
            addEdge(from, to, weight);
        }
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return this.E;
    }

    public void addEdge(int from, int to, double weight){
        this.edges[from][to] = weight;
        this.E++;
    }

    public void addEdge(DirectedEdge e){
        int from = e.from();
        int to = e.to();
        this.edges[from][to] = e.weight();
        this.E++;
    }

    public Iterable<DirectedEdge> adj(int v){
        LinkedListQueue<DirectedEdge> result = new LinkedListQueue<>();
        for(int to = 0; to < V; to++){
            if(this.edges[v][to] < Double.POSITIVE_INFINITY){
                result.enqueue(new DirectedEdge(v, to, this.edges[v][to]));
            }
        }
        return result;
    }

    public Iterable<DirectedEdge> edges(){
        LinkedListQueue<DirectedEdge> result = new LinkedListQueue<>();
        for(int from = 0; from < V; from++){
            for(int to = 0; to < V; to++){
                if(this.edges[from][to] < Double.POSITIVE_INFINITY){
                    result.enqueue(new DirectedEdge(from, to, this.edges[from][to]));
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraphMatrix ewdg = new EdgeWeightedDigraphMatrix(new In(filename));
        for(DirectedEdge e: ewdg.edges()){
            StdOut.println(e);
        }
    }
}