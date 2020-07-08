package javasrc.ch04_4;

/*
* Algorithm 4.9 Dijkstra's Shortedt-Paths Algorithm. P.655
* 
*/

import java.util.Stack;
import javasrc.ch02_4.IndexMinPQ;
import lib.*;

public class DijkstraSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    
    public DijkstraSP(EdgeWeightedDigraph g, int s){
        int V = g.V();
        this.edgeTo = new DirectedEdge[V];
        this.distTo = new double[V];
        this.pq = new IndexMinPQ<>(V);

        for(int i = 0; i < V; i++){
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        this.pq.insert(s, 0.0);
        while(!pq.isEmpty()){
            relax(g, pq.delMin());
        }
    }

    // ! key method for vertex relaxation. P.648
    private void relax(EdgeWeightedDigraph g, int v){
        // TODO
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }

    // ! key method for edge relaxation. P.646 and P.647
    private void relax(DirectedEdge e){
        int v = e.from();
        int w = e.to();
        double newDistToW = distTo[v] + e.weight();
        if(distTo[w] > newDistToW){
            distTo[w] = newDistToW;
            edgeTo[w] = e;
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

        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return null;
    }


    public static void main(String[] args){
        // * official tester, running at CLI
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        DijkstraSP dsp = new DijkstraSP(g, s);

        for(int i = 0; i < g.V(); i++){
            StdOut.print(s + " to " + i);
            StdOut.printf(" (%4.2f): ", dsp.distTo(i));
            if(dsp.hasPathTo(i)){
                for(DirectedEdge e: dsp.pathTo(i)){
                    StdOut.print(e + "   ");
                }
            }
            StdOut.println();
        }



    }
}