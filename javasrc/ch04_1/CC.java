package javasrc.ch04_1;

import java.util.Queue;

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

/*
 * Algorithm 4.3 Depth-first search to find connected components in a graph
 * 
 * Proposition c. DFS uses preprocessing time and space proportional to V + E to 
 * support constant-time connectivity queries in a graph.
 *
 */

public class CC {

    private boolean[] marked;
    private int[] componentId;      // * original name: id
    private int count;

    public CC(Graph G){
        marked = new boolean[G.V()];
        componentId = new int[G.V()];
        for(int s = 0; s < G.V(); s++){
            if(!marked[s]){
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        componentId[v] = count;
        for(int w : G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
    }

    // * are v and w connected
    public boolean connected(int v, int w){
        return componentId[v] == componentId[w];
    }

    // * return component identifier for v (between 0 and count()-1)
    public int id(int v){
        return componentId[v];
    }

    // * return number of connected components
    public int count(){
        return count;
    }

    // * client
    public static void main(String[] args){
        Graph G = new Graph(new In(args[0]));
        CC cc = new CC(G);

        int M = cc.count();
        StdOut.println(M + " components");

        LinkedListQueue<Integer>[] components = 
            (LinkedListQueue<Integer>[]) new LinkedListQueue[M];

        for(int i = 0; i < M; i++){
            components[i] = new LinkedListQueue<Integer>();
        }

        for (int v = 0; v < G.V(); v++){
            components[cc.id(v)].enqueue((v));
        }

        for(int i = 0; i < M; i++){
            for(int v: components[i]){
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }

    
}