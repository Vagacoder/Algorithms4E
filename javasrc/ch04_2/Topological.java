package javasrc.ch04_2;

import lib.StdOut;

/*
 * Algorithm 4.5 Topological sort. P.581
 * 
 * Proposition F. Reverse postorder in a DAG is a topological sort. P.582
 * 
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