package javasrc.ch04_3;

import javasrc.ch01_3.LinkedListQueue;

/*
! Based on Prim Eager MST

* 4.3.29 Dense graphs. Develop an implementation of Prim’s algorithm that uses 
* an eager approach (but not a priority queue) and computes the MST using V^2 
* edge-weight comparisons.
* 
*/

import javasrc.ch03_4.SeparateChainingHashST;

import lib.*;

public class DenseGraph {

    private SeparateChainingHashST<Integer, Double> map;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] visited;
    private int V;

    public DenseGraph(EdgeWeightedGraph g){
        this.V = g.V();
        this.map = new SeparateChainingHashST<>();
        this.edgeTo = new Edge[V];
        this.distTo = new double[V];
        this.visited = new boolean[V];

        for(int i = 0; i < V; i++){
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[0] = 0.0;
        map.put(0, 0.0);
        while(map.size()>0){
            visit(g, getMinV());
        }
    }
    
    private int getMinV(){
        int index = -1;
        double minDist = Double.POSITIVE_INFINITY;
        for(int i = 0; i< V; i++){
            Double distance = map.get(i);
            if(distance != null && distance < minDist){
                minDist = distance;
                index = i;
            }
        }
        map.delete(index);
        return index;
    }

    private void visit(EdgeWeightedGraph g, int v){
        this.visited[v] = true;
        for(Edge e : g.adj(v)){
            int w = e.other(v);
            if(!visited[w] && e.weight() < distTo[w]){
                edgeTo[w] = e;
                distTo[w] = e.weight();
                map.put(w, e.weight());
            }
        }
    }

    public Iterable<Edge> edges(){
        LinkedListQueue<Edge> edges = new LinkedListQueue<>();

        // ! Or i starts at 1, then no need check whether e is null or not
        for(int i =0; i<this.edgeTo.length;i++){
            Edge e = this.edgeTo[i];
            if(e != null){
                edges.enqueue(e);
            }
        }
        return edges;
    }

    public static void main(String[] args){
        String filename = "data/tinyEWG.txt";
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In(filename));
        PrimMST mst = new PrimMST(ewg);

        for(Edge e: mst.edges()){
            StdOut.println(e.toString());
        }
    }
    
}