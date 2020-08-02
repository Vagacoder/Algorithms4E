package javasrc.ch04_1;

import java.util.Stack;

/*
 * Algorithm 4.1 Depth-first search to find paths in a graph. P.536
  
 ? upgraded from DepthFirstSearch.java by adding edgeTo[]

 * Proposition A. DFS marks all the vertices connected to a given source in time 
 * proportional to the sum of their degrees. DFS allows us to provide clients 
 * with a path from a given source to any marked vertex in time proportional its 
 * length.
 
 ! The result of the search is a tree rooted at the source; edgeTo[] is a parent-
 ! link representation of that tree. 

 */

public class DepthFirstPaths extends Paths {

    // * has dfs() been called for this vertex?
    private boolean[] marked; 

    // * last vertex on known path to this vertex
    // * edgeTo[] remembers paths from source to every reachable vertices
    private int[] edgeTo; 
    private final int s;

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    // ! Recursive calling dfs() is using a stack implicitly
    private void dfs(Graph G, int v) {
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

    // ! Important, understand the meaning of edgeTo[]:
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
}