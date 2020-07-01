package javasrc.ch04_3;


/*
* 4.3.23 Vyssotsky’s algorithm. Develop an implementation that computes the MST 
* by applying the cycle property (see Exercise 4.3.8) repeatedly: Add edges one 
* at a time to a putative tree, deleting a maximum-weight edge on the cycle if 
* one is formed. 
* Note : This method has received less attention than the others that we consider 
* because of the comparative difficulty of maintaining a data structure that 
* supports efficient implementation of the “delete the maximum-weight edge on 
* the cycle" operation.
* 
*/

import javasrc.ch01_3.DoubleLinkedList;
import lib.*;

public class VyssotskyMST {
    
    private DoubleLinkedList<Edge> mst;
    
    public VyssotskyMST(EdgeWeightedGraph g){
        int V = g.V();
        this.mst = new DoubleLinkedList<>();

        // * add and test edges one by one
        for(Edge e: g.edges()){
            this.mst.addBeginning(e);

            // * new graph with only edges from mst
            EdgeWeightedGraph newMstGraph = new EdgeWeightedGraph(V);
            for(Edge mstEdge: mst){
                newMstGraph.addEdge(mstEdge);
            }

            // * test cycle on new graph, if has cycle, remove max edge.
            EWGcycle newMstGcycle = new EWGcycle(newMstGraph);
            if(newMstGcycle.hasCycle()){
                Edge maxEdge = newMstGcycle.cycle().iterator().next();
                for(Edge cycleEdge: newMstGcycle.cycle()){
                    if(cycleEdge.compareTo(maxEdge) >0){
                        maxEdge = cycleEdge;
                    }
                }
                mst.remove(maxEdge);
            }
        }
    }

    public Iterable<Edge> edges(){
        return this.mst;
    }

    public int mstEdgeNumber(){
        return this.mst.size();
    }

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        VyssotskyMST vmst = new VyssotskyMST(g);
        for (Edge e: vmst.edges()){
            StdOut.println(e.toString());
        }
    }

}