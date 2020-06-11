package javasrc.ch04_2;

import javasrc.ch01_3.BagX;
import lib.*;

/*
 * Algorithm 4.4 Reachability in digraphs. P.571
 * 
 * Proposition D. DFS marks all the vertices in a digraph reachable from a given 
 * set of sources in time proportional to the sum of the outdegrees of the vertices marked.
 * 
 */

public class DirectedDFS {
    
    private boolean[] marked;

    public DirectedDFS(Digraph G, int s){
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources){
        marked = new boolean[G.V()];
        for(int s: sources){
            if(!marked[s]){
                dfs(G, s);
            }
        }
    }

    private void dfs(Digraph G, int v){
        marked[v] = true;
        for(int w : G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
    }

    public boolean marked(int v){
        return marked[v];
    }

    public static void main(String[] args){
        Digraph G = new Digraph(new In(args[0]));

        BagX<Integer> sources = new BagX<>();
        for(int i = 1; i < args.length; i++){
            sources.add(Integer.parseInt(args[i]));
        }

        DirectedDFS reachable = new DirectedDFS(G, sources);

        for(int v=0; v < G.V(); v++){
            if(reachable.marked(v)){
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }
}