package javasrc.ch04_3;

/*
* 4.3.24 Reverse-delete algorithm. Develop an implementation that computes the 
* MST as follows: Start with a graph containing all of the edges. Then repeatedly 
* go through the edges in decreasing order of weight. For each edge, check if 
* deleting that edge will disconnect the graph; if not, delete it. Prove that 
* this algorithm computes the MST. What is the order of growth of the number of 
* edge-weight compares performed by your implementation?

*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class ReverseDelete {
    
    private LinkedListQueue<Edge> mst;
    
    public ReverseDelete(EdgeWeightedGraph g) throws Exception {
        this.mst = new LinkedListQueue<>();
        int V = g.V();
        int E = g.E();
        
        // * boolean[] show whether ith edges should be included in mst
        boolean[] isInMstEdges = new boolean[E];
        for(int i =0; i < E; i++){
            isInMstEdges[i] = true;
        }

        // * all edges
        ArrayList<Edge> allEdges = new ArrayList<>();
        for(Edge e:g.edges()){
            allEdges.add(e);
        }

        // * sort edges in reversed order
        Collections.sort(allEdges, new Comparator<Edge>() {
            public int compare(Edge a, Edge b) {
                if(a.weight() < b.weight()){
                    return 1;
                }else if(a.weight() > b.weight()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        
        // * assert all edges
        for(Edge e:allEdges){
            StdOut.println(e.toString());
        }
        if(allEdges.size() != E || allEdges.size() != isInMstEdges.length){
            throw new Exception("allEdges size does not equal to isInMstEdges! Both should be E");
        }

        // * test each edge in allEdges
        for(int i = 0; i <E; i++){
            isInMstEdges[i] = false;
            EdgeWeightedGraph newMst = new EdgeWeightedGraph(V);
            for(int j = 0; j < E; j++){
                if(isInMstEdges[j]){
                    newMst.addEdge(allEdges.get(j));
                }
            }
            EWGCC cc = new EWGCC(newMst);
            if(cc.numberOfComponent()>1){
                isInMstEdges[i] = true;
            }
        }

        for(int i = 0; i < E; i++){
            if(isInMstEdges[i]){
                mst.enqueue(allEdges.get(i));
            }
        }
    }

    public Iterable<Edge> edges(){
        return this.mst;
    }

    public int mstEdgeNumber(){
        return this.mst.size();
    }

    public static void main(String[] args) throws Exception {
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph g = new EdgeWeightedGraph(new In(filename));
        ReverseDelete rd = new ReverseDelete(g);
        StdOut.println("MST:");
        for (Edge e: rd.edges()){
            StdOut.println(e.toString());
        }
    }

}