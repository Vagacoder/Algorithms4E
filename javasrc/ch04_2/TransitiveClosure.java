package javasrc.ch04_2;

/*
 * Transitive Closure for digraph. P.593
 * 
 * Space usage: V^2, time proportional to V(V+E), therefore its not practical for
 * large graphs.
*/

public class TransitiveClosure {
    
    private DirectedDFS[] all;

    TransitiveClosure(Digraph G){
        this.all = new DirectedDFS[G.V()];

        for(int v= 0; v < G.V(); v++){
            this.all[v] = new DirectedDFS(G, v);
        }
    }

    public boolean reachable(int v, int w){
        return this.all[v].marked(w);
    }
}