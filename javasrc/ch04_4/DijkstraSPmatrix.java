package javasrc.ch04_4;


/*
* 4.4.26 Single-source shortest paths in dense graphs. Develop a version of 
* Dijkstra’s algorithm that can find the SPT from a given vertex in a dense edge-
* weighted digraph in time proportional to V 2. 
* 
* Use an adjacency-matrix representation (see Exercise 4.4.3 and Exercise 4.3.29).
* 

? sample file:
? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
*/

import javasrc.ch01_3.LinkedListStackX;
import javasrc.ch02_4.IndexMinPQ;

import lib.*;

public class DijkstraSPmatrix {
    
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSPmatrix(EdgeWeightedDigraphMatrix g, int s){
        int V = g.V();
        this.distTo = new double[V];
        this.edgeTo = new DirectedEdge[V];
        this.pq = new IndexMinPQ<>(V);

        for(int i = 0; i < V; i++){
            this.distTo[i] = Double.POSITIVE_INFINITY;
        }

        this.distTo[s] = 0.0;
        this.pq.insert(s, 0.0);

        while(!this.pq.isEmpty()){
            relax(g, this.pq.delMin());
        }
    }

    public void relax(EdgeWeightedDigraphMatrix g, int v){
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            double distanceToW = distTo[v] + e.weight();
            if(distTo[w] > distanceToW){
                distTo[w] = distanceToW;
                edgeTo[w] = e;
                if(pq.contains(w)){
                    pq.changeKey(w, distanceToW);
                }else{
                    pq.insert(w, distanceToW);
                }
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

        var path = new LinkedListStackX<DirectedEdge>();
        for(DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args){
        // * official tester, running at CLI
        // EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(args[0]));
        // int s = Integer.parseInt(args[1]);
        // DijkstraSP dsp = new DijkstraSP(g, s);

        // for(int i = 0; i < g.V(); i++){
        //     StdOut.print(s + " to " + i);
        //     StdOut.printf(" (%4.2f): ", dsp.distTo(i));
        //     if(dsp.hasPathTo(i)){
        //         for(DirectedEdge e: dsp.pathTo(i)){
        //             StdOut.print(e + "   ");
        //         }
        //     }
        //     StdOut.println();
        // }

        // * tester #1
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraphMatrix g = new EdgeWeightedDigraphMatrix(new In(filename));
        int s = 0;
        DijkstraSPmatrix dsp = new DijkstraSPmatrix(g, s);

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

        // * tester #2 
        StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
        filename = "data/tinyEWDGn.txt";
        g = new EdgeWeightedDigraphMatrix(new In(filename));
        s = 0;
        dsp = new DijkstraSPmatrix(g, s);
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

        // * tester #3 
        // ! This one DOES NOT work, since Dijkstra algorithm will keep spinning 
        // ! at the cycle with negative edge.
        // StdOut.println("\n3. tinyEWDGnc.txt with a negative edge cycle");
        // filename = "data/tinyEWDGnc.txt";
        // g = new EdgeWeightedDigraph(new In(filename));
        // s = 0;
        // dsp = new DijkstraSPmatrix(g, s);
        // for(int i = 0; i < g.V(); i++){
        //     StdOut.print(s + " to " + i);
        //     StdOut.printf(" (%4.2f): ", dsp.distTo(i));
        //     if(dsp.hasPathTo(i)){
        //         for(DirectedEdge e: dsp.pathTo(i)){
        //             StdOut.print(e + "   ");
        //         }
        //     }
        //     StdOut.println();
        // }

        // * tester #4 ex4.4.14
        StdOut.println("\n4. tinyEWDGnConvert.txt from tinyEWDGn.txt, every edege weight += abs(minEdgeWeight)");
        filename = "data/tinyEWDGnConvert.txt";
        g = new EdgeWeightedDigraphMatrix(new In(filename));
        s = 0;
        dsp = new DijkstraSPmatrix(g, s);
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