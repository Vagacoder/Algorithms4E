package javasrc.ch04_1;

import lib.*;
import java.util.Stack;
import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 4.2 Breadth-first search to find paths in a graph. P.540
 * 
 * Proposition B. For any vertex v reachable from s, BFS computes a shortest 
 * path from s to v (no path from s to v has fewer edges). BFS takes time 
 * proportional to V + E in the worst case.
 * 
 * 4.1.13 Add a distTo() method to the BreadthFirstPaths API and implementation, 
 * which returns the number of edges on the shortest path from the source to a 
 * given vertex. A distTo() query should run in constant time.
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
    // ! edgeTo[w] = v means: an edge from v to w; v-w
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

    // * 4.1.13
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

    // * check for 4.1.13
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

        BreadthFirstPaths bfp1 = new BreadthFirstPaths(g, 1);
        StdOut.printf("From 1 to %d: %d\n", 0, bfp1.distTo(0));
        StdOut.printf("From 1 to %d: %d\n", 1, bfp1.distTo(1));
        StdOut.printf("From 1 to %d: %d\n", 2, bfp1.distTo(2));
        StdOut.printf("From 1 to %d: %d\n", 4, bfp1.distTo(4));
        StdOut.printf("From 1 to %d: %d\n", 7, bfp1.distTo(7));
    }

    public static void main(String[] args){
        check();
    }
}