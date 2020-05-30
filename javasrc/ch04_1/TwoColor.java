package javasrc.ch04_1;

/*
 * Two color (Is the graph bipartite?). P. 547 
 */

public class TwoColor {

    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable = true;

    public TwoColor(Graph G){
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for(int s = 0; s < G.V(); s++){
            if(!marked[s]){
                dfs(G, s);
            }
        }
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        for(int w: G.adj(v)){
            if(!marked[w]){
                color[w] = !color[v];
                dfs(G, w);
            }else if(color[w] == color[v]){
                this.isTwoColorable = false;
            }
        }
    }

    public boolean isBipartite(){
        return this.isTwoColorable;
    }
    
}