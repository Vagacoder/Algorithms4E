package javasrc.ch04_1;

import lib.*;

/*
* Cycle detection in graph. P. 547

* 4.1.29 Modify Cycle so that it works even if the graph contains self-loops and 
* parallel edges.

! Important algorithm, see comments on dfs(). Must understand it!
*/

public class Cycle {
    
    private boolean[] marked;
    private boolean hasCycle = false;

    public Cycle(Graph G){
        marked = new boolean[G.V()];
        for(int s = 0; s < G.V(); s++){
            if(!marked[s]){
                dfs(G, s, s);
            }
        }
    }

    // ! v = current vertex, u = vertex from, current edge is from u => v
    private void dfs(Graph G, int v, int u){
        marked[v] = true;
        for(int w: G.adj(v)){       // ! w = vertex adjacent to current on

            // * 4.1.29 allow self loop
            if(w == v){       
                hasCycle = true;
            }
            // * 4.1.29 allow paralle loop
            if(w == u){
                hasCycle = true;
            }

            if(!marked[w]){
                dfs(G, w, v);
            }else if(w != u){       // ! (w is marked and w != u) means there must be another path from u to v through w
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle(){
        return this.hasCycle;
    }

    public static void main(String[] args){
        // Graph G = new Graph(new In(args[0]));

        Graph G = new Graph(10);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 0);

        Cycle cycle = new Cycle(G);

        System.out.println(cycle.hasCycle());
    }
}