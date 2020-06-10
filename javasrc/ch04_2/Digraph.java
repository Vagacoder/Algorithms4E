package javasrc.ch04_2;

import javasrc.ch01_3.BagX;
import lib.*;

/*
 * Directed graph (digraph) P.569
 * 
 */

public class Digraph {

    private final int V;
    private int E;
    private BagX<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (BagX<Integer>[]) new BagX[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new BagX<Integer>();
        }
    }

    public Digraph(In in) {
        this(in.readInt()); // read V and construct this graph
        int E = in.readInt(); // read E
        for (int i = 0; i < E; i++) {
            int v = in.readInt(); // read a vertex
            int w = in.readInt(); // read another vertex
            addEdge(v, w); // add edge connecting them
        }
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);
            }
        }
        return R;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : this.adj(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {

    }
}