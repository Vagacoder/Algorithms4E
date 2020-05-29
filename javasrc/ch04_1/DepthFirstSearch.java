package javasrc.ch04_1;

/*
 * Depth-first search. P.531 
 * 
 * 
 * Proposition A. DFS marks all the vertices connected to a given source in time 
 * proportional to the sum of their degrees.
 * 
 */

public class DepthFirstSearch {

    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s){
        marked = new boolean[G.V()];
        dfs(G,s);
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        count++;
        for(int w: G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
    }

    public boolean marked(int w){
        return marked[w];
    }

    public int count(){
        return count;
    }

}