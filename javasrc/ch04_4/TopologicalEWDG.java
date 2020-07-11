package javasrc.ch04_4;

/*
* Derived from Algorithm 4.5 (Topological for directed graph) at P.581 
* by changeing parameter from 'Digraph' to 'EdgeWeightedDigraph'
*/

import javasrc.ch04_2.DepthFirstOrder;
import lib.*;

public class TopologicalEWDG {
    
    private Iterable<Integer> order;

    public TopologicalEWDG(EdgeWeightedDigraph g){
        // TODO
        EdgeWeightedDirectedCycle cycleFinder = new EdgeWeightedDirectedCycle(g);
        if(!cycleFinder.hasCycle()){
            DepthFirstOrder dfo = new DepthFirstOrder(g);
            order = dfo.reversePost();
        }
    }

    public boolean hasOrder(){
        return this.order != null;
    }

    public Iterable<Integer> order(){
        return this.order;
    }

    public boolean isDAG(){
        return order != null;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWDAG.txt";
        StdOut.println("1. testing " + filename);
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(new In(filename));
        TopologicalEWDG top = new TopologicalEWDG(ewdg);
        StdOut.println("Is it a DAG: " + top.isDAG());
        for(int v : top.order()){
            StdOut.println(v);
        }

        filename = "data/tinyEWDG.txt";
        StdOut.println("\n2. testing " + filename);
        ewdg = new EdgeWeightedDigraph(new In(filename));
        top = new TopologicalEWDG(ewdg);
        StdOut.println("Is it a DAG: " + top.isDAG());

    }
}