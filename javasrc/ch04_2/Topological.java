package javasrc.ch04_2;

import lib.*;

/*
 * Algorithm 4.5 Topological sort. P.581
  
 ? Topological sort: Given a digraph, put the vertices in order such that all its 
 ? directed edges point from a vertex earlier in the order to a vertex later in 
 ? the order

 ! A sequence of vertices is topological sort if and only if:
 ? 1. sequence contains all vertices, and each vertex presents only once;
 ? 2. if vertex a presents before vertex b in sequence, there is no edge from b
 ?    to a.

 ! Any DAG has at least one topological sort, could has more.
  
 * Step 1: check whether a cycle;
 * Step 2: if acyclic, get depth-first reverse post order.
 * 
 * Client solves the precedence-constrained scheduling problem for a SymbolDigraph.
 * 
 
 ! Proposition E. A digraph has a topological order if and only if it is a DAG. P.578
 
 ! Proposition F. Reverse postorder in a DAG is a topological sort. P.582
  
 * Proposition G. With DFS, we can topologically sort a DAG in time proportional to V+E.
 * 
 */
public class Topological {
   
    // * topological order
    private Iterable<Integer> order;

    public Topological(Digraph G){
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if(!cyclefinder.hasCycle()){
            DepthFirstOrder dfo = new DepthFirstOrder(G);
            order = dfo.reversePost();
        }
    }

    public Iterable<Integer> order(){
        return order;
    }

    public boolean isDAG(){
        return order != null;
    }

    public static void main(String[] args){
        String filename = args[0];
        String separator = args[1];
        SymbolDigraph sg =  new SymbolDigraph(filename, separator);

        Topological top = new Topological(sg.G());

        for(int v: top.order()){
            StdOut.println(sg.name(v));
        }
    }
}