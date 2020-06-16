package javasrc.ch04_2;

import javasrc.ch01_3.BagX;
import lib.*;

/*
 * Directed graph (digraph) P.569
 * 
 * 4.2.3 Create a copy constructor for Digraph that takes as input a digraph G 
 * and creates and initializes a new copy of the digraph.
 * 
 * 4.2.4 Add a method hasEdge() to Digraph which takes two int arguments v and w 
 * and returns true if the graph has an edge v->w, false otherwise.
 * 
 * 4.2.5 Modify Digraph to disallow parallel edges and self-loops.
 * 
 * 4.2.6 Develop a test client for Digraph.
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
        int e = in.readInt(); // read E
        for (int i = 0; i < e; i++) {
            int v = in.readInt(); // read a vertex
            int w = in.readInt(); // read another vertex
            addEdge(v, w); // add edge connecting them
        }
    }

    // * 4.2.3 
    public Digraph(Digraph g){
        this(g.V);
        for(int i = 0; i < g.V; i++){
            for(int v: g.adj[i]){
                this.addEdge(i, v);
            }
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

    // * 4.2.5
    public void addStrictEdge(int v, int w){
        // ? disallow self loop
        if(v == w){
            return;
        }

        // ? disallow parallel edges
        for(int i: adj[v]){
            if(i == w){
                return;
            }
        }

        adj[v].add(w);
        this.E++;
    }

    // * 4.2.4
    public boolean hasEdge(int v, int w){
        for(int i: adj[v]){
            if(i == w){
                return true;
            }
        }
        return false;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // * very useful for advanced graph algorithm
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

        Digraph dg = new Digraph(new In(args[0]));
        StdOut.println(dg.toString());
        StdOut.println();
        Digraph dg1 = new Digraph(dg);
        StdOut.println(dg1.toString());

    }
}