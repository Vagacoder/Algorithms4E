package javasrc.ch04_2;

import lib.*;
import java.util.Stack;
import javasrc.ch01_3.LinkedListQueue;

/*
 *
 // ! this is copied from chapter 4.1, with only change of Grapgh to Digraph
 *
 * Algorithm 4.2 Breadth-first search to find paths in a graph. P.540
 * 
 * Proposition B. For any vertex v reachable from s, BFS computes a shortest 
 * path from s to v (no path from s to v has fewer edges). BFS takes time 
 * proportional to V + E in the worst case.
 * 
 * 
 */

public class BreadthFirstPaths{
    
    private boolean[] marked;       // Is a shortest path to this vertex know?
    private int[] edgeTo;           // last vertex on known path to this vertex
    private final int s;

    public BreadthFirstPaths(Digraph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Digraph G, int s){
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

    public int distTo(int v){
        if(!hasPathTo(v)){
            return -1;
        }
        int dist = 0;
        for(int x = v; x!= this.s; x = this.edgeTo[x]){
            dist++;
        }
        return dist;
    }

    public static void check(){
        // ? using tinyDG.txt as sample.
        Digraph g = new Digraph(13);
        g.addEdge(4, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 2);
        g.addEdge(6, 0);
        g.addEdge(0, 1);
        g.addEdge(2, 0);
        g.addEdge(11, 12);
        g.addEdge(12, 9);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(7, 9);
        g.addEdge(10, 12);
        g.addEdge(11, 4);
        g.addEdge(4, 3);
        g.addEdge(3, 5);
        g.addEdge(6, 8);
        g.addEdge(8, 6);
        g.addEdge(5, 4);
        g.addEdge(0, 5);
        g.addEdge(6, 4);
        g.addEdge(6, 9);
        g.addEdge(7, 6);

        BreadthFirstPaths bfp = new BreadthFirstPaths(g, 0);
        StdOut.printf("From 0 to %d: %d\n", 0, bfp.distTo(0));
        StdOut.printf("From 0 to %d: %d\n", 1, bfp.distTo(1));
        StdOut.printf("From 0 to %d: %d\n", 2, bfp.distTo(2));
        StdOut.printf("From 0 to %d: %d\n", 3, bfp.distTo(3));
        StdOut.printf("From 0 to %d: %d\n", 4, bfp.distTo(4));
        StdOut.printf("From 0 to %d: %d\n", 5, bfp.distTo(5));
        StdOut.printf("From 0 to %d: %d\n", 6, bfp.distTo(6));
        StdOut.printf("From 0 to %d: %d\n", 8, bfp.distTo(8));
        StdOut.printf("From 0 to %d: %d\n", 9, bfp.distTo(9));
        StdOut.printf("From 0 to %d: %d\n", 10, bfp.distTo(10));
        StdOut.printf("From 0 to %d: %d\n\n", 11, bfp.distTo(11));

        BreadthFirstPaths bfp1 = new BreadthFirstPaths(g, 7);
        StdOut.printf("From 7 to %d: %d\n", 0, bfp1.distTo(0));
        StdOut.printf("From 7 to %d: %d\n", 1, bfp1.distTo(1));
        StdOut.printf("From 7 to %d: %d\n", 6, bfp1.distTo(6));
        StdOut.printf("From 7 to %d: %d\n", 8, bfp1.distTo(8));
        StdOut.printf("From 7 to %d: %d\n", 12, bfp1.distTo(12));
    }

    public static void main(String[] args){
        check();
    }
}