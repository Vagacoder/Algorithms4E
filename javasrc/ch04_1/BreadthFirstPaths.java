package javasrc.ch04_1;

import java.util.Stack;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 4.2 Breadth-first search to find paths in a graph. P.540
 * 
 * Proposition b. For any vertex v reachable from s, BFS computes a shortest 
 * path from s to v (no path from s to v has fewer edges). BFS takes time 
 * proportional to V + E in the worst case.
 * 
 */

public class BreadthFirstPaths extends Paths{
    
    private boolean[] marked;       // Is a shortest path to this vertex know?
    private int[] edgeTo;           // last vertex on known path to this vertex
    private final int s;

    public BreadthFirstPaths(Graph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        marked[s] = true;
        queue.enqueue(s);
        while(!queue.isEmpty()){
            int v = queue.dequeue();
            for(int w : G.adj(v)){
                if(!marked[w]){
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

	public boolean hasPathTo(int v) {
		return marked[v];
    }
    
    // ! Important, understand the meaning of edgeTo[]: 
    // !    edgeTo[w] = v  ==> a edge from v to w; v-w
    public Iterable<Integer> pathTo(int v) {
        if(!hasPathTo(v)){
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for(int x = v; x!=s; x = edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
    }
}