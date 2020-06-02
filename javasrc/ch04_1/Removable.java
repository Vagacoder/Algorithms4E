package javasrc.ch04_1;

/*
 * 4.1.10 Prove that every connected graph has a vertex whose removal (including 
 * all incident edges) will not disconnect the graph, and write a DFS method that 
 * finds such a vertex. 
 * Hint : Consider a vertex whose adjacent vertices are all marked.

*/

import lib.*;

public class Removable{

    private Graph g;
    private boolean[] marked;
    private int[] edgeTo;

    public Removable(Graph g){
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        this.g = g;
    }
    
    private int dfs(Graph G, int v){
        marked[v] = true;

        boolean isAllRoundMarked = true;

        for(int w : G.adj(v)){
            if(!marked[w]){
                edgeTo[w] = v;
                isAllRoundMarked = false;
                int returned = dfs(G, w);
                if(returned != -1){
                    return returned;
                }
            }
        }
        if(isAllRoundMarked){
            return v;
        }else{
            return -1;
        }
    }

    public int getRemovable(int v){
        return dfs(this.g, v);
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

        Removable rm = new Removable(g);
        StdOut.println(rm.getRemovable(2));
    }

    public static void main(String[] args){
        check();
    }
}