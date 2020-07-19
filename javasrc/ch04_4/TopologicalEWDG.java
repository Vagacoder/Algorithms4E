package javasrc.ch04_4;

/*
* Derived from Algorithm 4.5 (Topological for directed graph) at P.581 
* by changeing parameter from 'Digraph' to 'EdgeWeightedDigraph'
*
* 4.4.12 Adapt the DirectedCycle and Topological classes from Section 4.2 to use
* the EdgeWeightedDigraph and DirectedEdge APIs of this section, thus implementing
* EdgeWeightedDirectedCycle and Topological classes.

 ? Sample file: 
 ? tinyEWDAG.txt, P.659
 ? tinyEWDAGn.txt, modified from tinyEWDAG, 6->4 -0.93

*/

import javasrc.ch04_2.DepthFirstOrder;
import lib.*;

public class TopologicalEWDG {
    
    private Iterable<Integer> order;

    public TopologicalEWDG(EdgeWeightedDigraph g){
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

        filename = "data/tinyEWDGn.txt";
        StdOut.println("\n2. testing " + filename);
        ewdg = new EdgeWeightedDigraph(new In(filename));
        top = new TopologicalEWDG(ewdg);
        StdOut.println("Is it a DAG: " + top.isDAG());

        filename = "data/tinyEWDG.txt";
        StdOut.println("\n3. testing " + filename);
        ewdg = new EdgeWeightedDigraph(new In(filename));
        top = new TopologicalEWDG(ewdg);
        StdOut.println("Is it a DAG: " + top.isDAG());

        filename = "data/tinyEWDAGn.txt";
        StdOut.println("\n4. testing " + filename);
        ewdg = new EdgeWeightedDigraph(new In(filename));
        top = new TopologicalEWDG(ewdg);
        StdOut.println("Is it a DAG: " + top.isDAG());
        for(int v : top.order()){
            StdOut.println(v);
        }
    }
}