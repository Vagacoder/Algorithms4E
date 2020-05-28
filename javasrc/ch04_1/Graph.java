package javasrc.ch04_1;

import javasrc.ch01_3.BagX;
import lib.*;
/*
* Graph data type. P. 526 and P.523
*/

public class Graph {
    private final int V;            // number of vertices
    private int E;                  // number of edges
    private BagX<Integer>[] adj;    // adjacency lists

    public Graph(int V){
        this.V = V;
        this.E = 0;
        adj = (BagX<Integer>[]) new BagX[V];
        for(int v = 0; v < V; v++){
            adj[v] = new BagX<Integer>();
        }
    }

    public Graph(In in){
        this(in.readInt());         // read V and construct this graph
        int E = in.readInt();       // read E
        for(int i = 0; i < E; i++){
            int v = in.readInt();   // read a vertex
            int w = in.readInt();   // read another vertex
            addEdge(v, w);          // add edge connecting them
        }
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return this.E;
    }

    public void addEdge(int v, int w){
        adj[v].add(w);              // add w to v's list
        adj[w].add(v);              // add v to w's list
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public static int degree(Graph G, int v){
        // ? is this implementation easier?
        // return G.adj[v].size();

        int degree = 0;
        for(int w: G.adj(v)){
            degree++;
        }
        return degree;
    }

    public static int maxDegree(Graph G){
        int max = 0;
        for(int v = 0; v < G.V(); v++){
            if(degree(G, v) > max){
                max = degree(G, v);
            }
        }
        return max;
    }

    public static double averageDegree(Graph G){
        return 2.0 * G.E()/G.V();
    }

    public static int numberOfSlefLoops(Graph G){
        int count = 0;
        for (int v = 0; v < G.V(); v++){
            for(int w: G.adj(v)){
                if(v == w){
                    count++;
                }
            }
        }
        return count/2;
    }

    public String toString(){
        String s = V + " vertices, " + E + " edges\n";
        for(int v = 0; v < V; v++){
            s += v + ": ";
            for(int w: this.adj(v)){
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

}
