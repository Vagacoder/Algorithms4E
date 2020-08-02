package javasrc.ch04_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javasrc.ch01_3.BagX;
import lib.*;
/*
 * Graph data type. P. 526 and P.523
 *
 * 4.1.3 Create a copy constructor for Graph that takes as input a graph G and 
 * creates and initializes a new copy of the graph. Any changes a client makes 
 * to G should not affect the newly created graph.
 * 
 * 4.1.4 Add a method hasEdge() to Graph which takes two int arguments v and w 
 * and returns true if the graph has an edge v-w, false otherwise.
 * 
 * 4.1.5 Modify Graph to disallow parallel edges and self-loops.
 * 
 * 4.1.32 Parallel edge detection. Devise a linear-time algorithm to count the 
 * parallel edges in a graph.

 * 
*/

public class Graph {
    private final int V; // number of vertices
    private int E; // number of edges
    private BagX<Integer>[] adj; // adjacency lists

    private int parallelEdge;

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (BagX<Integer>[]) new BagX[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new BagX<Integer>();
        }
    }

    public Graph(In in) {
        this(in.readInt()); // read V and construct this graph
        int E = in.readInt(); // read E
        for (int i = 0; i < E; i++) {
            int v = in.readInt(); // read a vertex
            int w = in.readInt(); // read another vertex
            addEdge(v, w); // add edge connecting them
        }
    }

    public Graph(In in, String adj){
        this(in.readInt());
        int E = in.readInt();
        while(in.hasNextLine()){
            String line = in.readLine();
            String[] vertices = line.split(" ");
            for(int i=1; i<vertices.length; i ++){
                addEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[i]));
            }
        }
        if(E!= this.E){
            StdOut.println("Miss some edge data line(s)");
        }
    }


    // * 4.1.3 clone constructor
    public Graph(Graph g) {
        this.V = g.V;
        this.E = g.E;
        this.adj = (BagX<Integer>[]) new BagX[V];
        for (int i = 0; i < V; i++) {
            this.adj[i] = new BagX<Integer>();
            // ! the order of edges is reversed, to get same order, need use a stack as
            // buffer
            for (int j : g.adj[i]) {
                this.adj[i].add(j);
            }
        }
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    // * 4.1.5, 4.1.32
    public void addEdge(int v, int w) {
        boolean isParalle = false;
        for (int i : adj[v]) {
            if (i == w) {
                this.parallelEdge++;
                isParalle = true;
            }
        }
        if(isParalle){
            return;
        }

        adj[v].add(w); // add w to v's list
        adj[w].add(v); // add v to w's list
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // * 4.1.4
    public boolean hasEdge(int v, int w) {
        for (int i : this.adj[v]) {
            if (i == w) {
                return true;
            }
        }
        return false;
    }

    public int degree(int v){
        return this.adj[v].size();
    }

    public static int degree(Graph G, int v) {
        // ? is this implementation easier?
        // return G.adj[v].size();

        int degree = 0;
        for (int w : G.adj(v)) {
            degree++;
        }
        return degree;
    }

    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    public static double averageDegree(Graph G) {
        return 2.0 * G.E() / G.V();
    }

    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    count++;
                }
            }
        }
        return count / 2;
    }


    // * 4.1.32
    public int parallelEdge(){
        return this.parallelEdge;
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

    // * testor
    public static void check() {

        // ? using tinyGex2.txt as sample.
        Graph g = new Graph(12);
        g.addEdge(8, 4);
        g.addEdge(2, 3);
        g.addEdge(1, 11);
        g.addEdge(0, 6);
        g.addEdge(3, 6);
        g.addEdge(10, 3);
        g.addEdge(7, 11);
        g.addEdge(7, 8);
        g.addEdge(11, 8);
        g.addEdge(2, 0);
        g.addEdge(6, 2);
        g.addEdge(5, 2);
        g.addEdge(5, 10);
        g.addEdge(5, 0);
        g.addEdge(8, 1);
        g.addEdge(4, 1);
        // ? test parallel edge
        g.addEdge(1, 4);

        StdOut.println(g.toString());

        // ? test clone constructor
        // Graph g1 = new Graph(g);
        // StdOut.println(g1.toString());
    }

    public static void main(String[] args) throws Exception {
        // check();

        // * 4.1.7
        // Scanner scanner = new Scanner(new File(args[0]));
        // int v = Integer.parseInt(scanner.nextLine());
        // int e = Integer.parseInt(scanner.nextLine());
        // Graph g = new Graph(v);
        
        // int lineNumber = 0;
        // while(scanner.hasNextLine()){
        //     String line = scanner.nextLine();
        //     String[] edge = line.trim().split(" ");
        //     if(edge.length != 2){
        //         throw new Exception("File format is not correct: need 2 vertices each line");
        //     }
        //     g.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
        //     lineNumber++;
        // }
        // if(lineNumber != e){
        //     System.out.println("Edges' number does not match header");
        // }

        // StdOut.println(g.toString());

        // * 4.1.15
        In in = new In(args[0]);
        Graph g = new Graph(in, "adj");

        StdOut.println(g.toString());
    }
}
