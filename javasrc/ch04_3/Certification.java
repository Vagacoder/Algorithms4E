package javasrc.ch04_3;

import javasrc.ch01_3.LinkedListQueue;

/*
* 4.3.33 Certification. Write an MST and EdgeWeightedGraph client check() that 
* uses the following cut optimality conditions implied by Proposition J to verify 
* that a proposed set of edges is in fact an MST: A set of edges is an MST if it 
* is a spanning tree and every edge is a minimum-weight edge in the cut defined 
* by removing that edge from the tree. What is the order of growth of the running 
* time of your method?
* 

? 

*/

import javasrc.ch01_5.UF;
import lib.*;

public class Certification {

    private EdgeWeightedGraph g;
    private Iterable<Edge> mstCandidate;
    
    public Certification(EdgeWeightedGraph g, Iterable<Edge> mstCandidate){
        this.g = g;
        this.mstCandidate = mstCandidate;
    }

    public boolean check(){

        int V = g.V();
        UF uf = new UF(V);
        double totalWeight = 0.0;
        int edgeNumber = 0;

        for(Edge e: mstCandidate){
            totalWeight += e.weight();
            int v = e.either();
            int w = e.other(v);
            // * candidate has a cycle
            if(uf.find(v) == uf.find(w)){
                return false;
            }
            uf.union(v, w);
            edgeNumber++;
        }

        // * candidate is separated (a forest, but a tree)
        if(uf.count() > 1){
            return false;
        }

        // * mst must have V-1 edges
        if(edgeNumber != V-1){
            return false;
        }

        // * simple checking whether is a mst
        // * get mst weight from Prim/Kruskal, compare with totalWeight;
        double weight = new KruskalMST(g).weight();
        if(totalWeight > weight){
            // ! disbale checking
            // return false;
        }

        // * check whether is a mst using Proposition J
        for(Edge checkingEdge: mstCandidate){

            // * add all edges IN CANDIDATE except checkingEdge into a temperary UF
            // * this means cut checkingEdge, mst becomes 2 separated components/trees
            UF tempUf = new UF(V);
            for(Edge otherE: mstCandidate){
                if(otherE != checkingEdge){
                    int v = otherE.either();
                    int w = otherE.other(v);
                    tempUf.union(v, w);
                }
            }

            // * loop all edges IN GRAPH and pick those which are not in same component,
            // * then compare weights of those edges with edge checkingEdge.
            for(Edge e: g.edges()){
                int v = e.either();
                int w = e.other(v);
                // * edge e is not in same component, compare it with checkingEdge
                if(!tempUf.connected(v, w)){
                    if(e.weight() < checkingEdge.weight()){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
       
        // * 1 test with mst from Kruskal
        StdOut.println("Using tinyEWG.txt");
        KruskalMST mst = new KruskalMST(ewg);
        LinkedListQueue<Edge> queue = new LinkedListQueue<>();
        for(Edge e: mst.edges()){
            queue.enqueue(e);
        }
        Certification cf = new Certification(ewg, queue);
        StdOut.println("1. check mst from Kruskal: " + cf.check());

        // * 2 remove edge 0-7 016 from mst 
        Edge e = queue.dequeue();
        cf = new Certification(ewg, queue);
        StdOut.println("2. check mst from Kruskal - 1: " + cf.check());
        StdOut.print("this edge is removed: ");
        StdOut.println(e);

        // * repalce it with 2-7 0.34
        for(Edge e1: ewg.edges()){
            int v = e1.either();
            int w = e1.other(v);
            if((v == 2 && w == 7) || (v==7&&w==2)){
                queue.enqueue(e1);
                StdOut.print("replace with this: ");
                StdOut.println(e1);
            }
        }
        for(Edge e1 : queue){
            StdOut.println(e1);
        }
        cf = new Certification(ewg, queue);
        StdOut.println("2. check mst from Kruskal: " + cf.check());
    }

}