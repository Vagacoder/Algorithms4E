package javasrc.ch04_4;

/*
*  4.4.39 Lazy implementation of Dijkstra’s algorithm. Develop an implementation 
* of the lazy version of Dijkstra’s algorithm that is described in the text.
* 

! Notice the difference of Prim and Dijkstra:
? (1) Prim is to find minimum spanning tree, Dijkstra is to find shortest path
? (2) Prim is to pick shortest edge between v in MST and v not in MST.
? (3) Dijkstra is to pick shortest distance from source to v not in SPT.
? (4) (2) suggests Prim compare length of edges
? (5) (3) suggests Dijstra compare distance from source to v ( = distTo[v] + e.weight())

? P.654 'Both algorithms build a rooted tree by adding an edge to a growing tree: 
? Prim’s adds next the non-tree vertex that is closest to the TREE; Dijkstra’s 
? adds next the non-tree vertex that is closest to the SOURCE.'
*/

import java.util.Comparator;

import javasrc.ch01_3.LinkedListStackX;
import javasrc.ch02_4.MinPQ;

import lib.*;

public class DijkstraSPlazy {

    private class DirectedEdgeComparator implements Comparator<DirectedEdge>{

        @Override
        public int compare(DirectedEdge e1, DirectedEdge e2) {
            // ! WRONG COMPARISON
            // if(e1.weight() - e2.weight() < 0){
            //     return -1;
            // }else if(e1.weight() - e2.weight() > 0){
            //     return 1;
            // }else{
            //     return 0;
            // }

            // * CORRECT COMPARISON
            double dist1 = distTo[e1.from()] + e1.weight();
            double dist2 = distTo[e2.from()] + e2.weight();
            return Double.compare(dist1, dist2);
        }
    }
    
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private MinPQ<DirectedEdge> pq;


    public DijkstraSPlazy(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.marked = new boolean[V];
        this.edgeTo = new DirectedEdge[V]; 
        this.distTo = new double[V];
        this.pq = new MinPQ<>(new DirectedEdgeComparator());

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;

        relax(g, s);

        while(!pq.isEmpty()){
            DirectedEdge e = pq.delMin();
            // StdOut.println(e);
            // int v = e.from();
            int w = e.to();
            // if(marked[v] && marked[w]){
            if(marked[w]){
                continue;
            }

            relax(g, w);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v){
        marked[v] = true;

        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                pq.insert(e);
            }
        }
    }

    public double distTo(int v){
        return this.distTo[v];
    }

    public boolean hasPathTo(int v){
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v){
        if(!hasPathTo(v)){
            return null;
        }

        LinkedListStackX<DirectedEdge> path = new LinkedListStackX<>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args){
        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
        DijkstraSPlazy dspl = new DijkstraSPlazy(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", dspl.distTo(i));
            if(dspl.hasPathTo(i)){
                for(DirectedEdge e: dspl.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }

            // * tester #2 
            StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
            filename = "data/tinyEWDGn.txt";
            g = new EdgeWeightedDigraph(new In(filename));
            s = 0;
            dspl = new DijkstraSPlazy(g, s);
            for(int i = 0; i < g.V(); i++){
                StdOut.print(s + " to " + i);
                StdOut.printf(" (%4.2f): ", dspl.distTo(i));
                if(dspl.hasPathTo(i)){
                    for(DirectedEdge e: dspl.pathTo(i)){
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
    }
}