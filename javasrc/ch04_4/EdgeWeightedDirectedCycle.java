package javasrc.ch04_4;

/* 
 * Derived from ch4.2/DirectedCycle.java. P.577
 * by changing from 'Digraph' to 'EdgeWeightedDigraph'
 * 

 ? Sample file: data/tinyEWDG1.txt (edge-weighted digraph with cycle);

*/

import java.util.Stack;
import lib.*;

public class EdgeWeightedDirectedCycle {
    
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private Stack<DirectedEdge> cycle;
    private boolean[] onStack;

    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph g){
        int V = g.V();
        this.onStack = new boolean[V];
        this.edgeTo = new DirectedEdge[V];
        this.marked = new boolean[V];
        for(int i = 0; i < V; i++){
            if(!marked[i]){
                dfs(g, i);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph g, int v){
        this.onStack[v] = true;
        this.marked[v] = true;
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(this.hasCycle()){
                return;
            }else if(!marked[w]){
                this.edgeTo[w] = e;
                dfs(g, w);
            }else if(onStack[w]){
                cycle = new Stack<DirectedEdge>();
                for(int x = v; x!= w; x=edgeTo[x].from()){
                    cycle.push(edgeTo[x]);
                }
                cycle.push(e);
            }
        }
        this.onStack[v] = false;
    }
 
    public boolean hasCycle(){
        return this.cycle != null;
    }

    public Iterable<DirectedEdge> cycle(){
        return cycle;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWDG1.txt";
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(new In(filename));
        EdgeWeightedDirectedCycle cycle = new EdgeWeightedDirectedCycle(ewdg);
        StdOut.println("Is there cycle: " + cycle.hasCycle());
        for(DirectedEdge e: cycle.cycle()){
            StdOut.println(e.toString());
        }
    }

}