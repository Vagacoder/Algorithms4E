package javasrc.ch04_1;

import javasrc.ch01_5.WeightedQuickUnionUF;

/*
 * 4.1.8 Develop an implementation for the Search API on page 528 that uses UF, 
 * as described in the text.
 * 
 * The constructor can build a UF object, do a union() operation for each of the 
 * graph’s edges, and implement marked(v) by calling connected(s, v).
 * 
 * Implementing count() requires using a weighted UF implementation and extending 
 * its API to use a size() method that returns wt[find(v)]
 ! No need use weighted UF, when iterating graph, the node number of adj[i] is the
 ! count.

*/

import lib.*;

public class UFSearch{

    private int source;
    private WeightedQuickUnionUF uf;

    public UFSearch(Graph g, int s){
        this.source = s;
        this.uf = new WeightedQuickUnionUF(g.V());

        for(int i = 0; i < g.V(); i++){
            for(int j: g.adj(i)){
                uf.union(i, j);
            }
        }
    }

    public boolean marked(int v){
        return uf.find(source) == uf.find(v);
    }

    // ! exclude source itself
    public int count(){
        return uf.size(source)-1;
    }

    public static void check(){
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

        UFSearch us = new UFSearch(g, 8);
        StdOut.println("1 " + us.marked(1));
        StdOut.println("2 " + us.marked(2));
        StdOut.println("3 " + us.marked(3));
        StdOut.println("4 " + us.marked(4));
        StdOut.println("7 " + us.marked(7));
        StdOut.println("8 " + us.marked(8));
        StdOut.println("10 " + us.marked(10));
        StdOut.println("11 " + us.marked(11));
        StdOut.println("size of 8: " + us.count());
    }

    public static void main(String[] args){
        check();
    }
}