package javasrc.ch04_2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Stack;
import lib.*;

/*
 *
 
 ! this is copied from chapter 4.1, with only change of Grapgh to Digraph. P.573

 *
 * Algorithm 4.1 Depth-first search to find paths in a graph. P.536
 * 
 
 ? upgraded from ch04_1.DepthFirstSearch.java by adding edgeTo[]
 ? this is also a upgraded version of ch04_2.DepthFirstDFS.java by adding edgeTo[] and s
  
 * Proposition A. DFS marks all the vertices connected to a given source in time 
 * proportional to the sum of their degrees. DFS allows us to provide clients 
 * with a path from a given source to any marked vertex in time proportional its 
 * length.
 * 
 */

public class DepthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // ! edgeTo[w] = v means: an edge from v to w; v-w
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public int distanceTo(int v){
        if(!hasPathTo(v)){
            return -1;
        }
        int distance = -1;
        for(int i: pathTo(v)){
            distance++;
        }
        return distance;
    }
}