package javasrc.ch04_4;

/* 
* Derived from ch4.2/DirectedCycle.java. P.577
* by changing from 'Digraph' to 'EdgeWeightedDigraph'
 *
 * 4.4.12 Adapt the DirectedCycle and Topological classes from Section 4.2 to use
 * the EdgeWeightedDigraph and DirectedEdge APIs of this section, thus implementing
 * EdgeWeightedDirectedCycle and Topological classes.
 *
 
 ? Sample file: 
 ? tinyEWDG1.txt (edge-weighted digraph with cycle);
 ? tinyEWDAG.txt, P.659
 ? tinyEWDAGn.txt, modified from tinyEWDAG, 6->4 -0.93
 
 */

import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class EdgeWeightedDirectedCycle {
    
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private LinkedListStackX<DirectedEdge> cycle;
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
                cycle = new LinkedListStackX<DirectedEdge>();
                cycle.push(e);
                for(int x = v; x!= w; x=edgeTo[x].from()){
                    cycle.push(edgeTo[x]);
                }
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
        // * test 1
        StdOut.println("1. test tinyEWDAG.txt, no cycle");
        String filename = "data/tinyEWDAG.txt";
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(new In(filename));
        EdgeWeightedDirectedCycle cycle = new EdgeWeightedDirectedCycle(ewdg);
        StdOut.println("Is there cycle: " + cycle.hasCycle());
        
        // * test 2
        StdOut.println("\n2. test tinyEWDG1.txt, with cycle");
        filename = "data/tinyEWDG1.txt";
        ewdg = new EdgeWeightedDigraph(new In(filename));
        cycle = new EdgeWeightedDirectedCycle(ewdg);
        StdOut.println("Is there cycle: " + cycle.hasCycle());
        for(DirectedEdge e: cycle.cycle()){
            StdOut.println(e.toString());
        }

        // * test 3
        StdOut.println("\n3. test tinyEWDAGn.txt, no cycle");
        filename = "data/tinyEWDAGn.txt";
        ewdg = new EdgeWeightedDigraph(new In(filename));
        cycle = new EdgeWeightedDirectedCycle(ewdg);
        StdOut.println("Is there cycle: " + cycle.hasCycle());
    }

}