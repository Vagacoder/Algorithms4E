package javasrc.ch04_4;

/*
* Algorithm 4.9 Dijkstra's Shortest-Paths Algorithm. P.655
* 
 * Proposition P. (shortest-paths optimality conditions) Let G be an edge-weighted
 * digraph, with s a source vertex in G and distTo[] a vertex-indexed array of 
 * path lengths in G such that, for all v reachable from s, the value of distTo[v] 
 * is the length of some path from s to v with distTo[v] equal to infinity for 
 * all v not reachable from s. These values are the lengths of shortest paths if 
 * and only if they satisfy distTo[w] <= distTo[v] + e.weight() for each edge e 
 * from v to w (or, in other words, no edge is eligible).
 * 
 * Proposition Q. (Generic shortest-paths algorithm) 
 
 ? Initialize distTo[s] to 0 and all other distTo[] values to infinity, and proceed as follows: 
 ?      Relax any edge in G, continuing until no edge is eligible. 
 
 * For all vertices w reachable from s, the value of distTo[w] after this computation
 * is the length of a shortest path from s to w (and edgeTo[w] is the last edge 
 * on such a path).
 * 
 * Proposition R. Dijkstra’s algorithm solves the single-source shortest-paths 
 * problem in edge-weighted digraphs with nonnegative weights.
 * 
 * Proposition R (continued). Dijkstra’s algorithm uses extra space proportional 
 * to V and time proportional to E log V (in the worst case) to solve the single-
 * source shortest paths problem in an edge-weighted digraph with E edges and V 
 * vertices.
  
 ! This Dijkstra algorithm allows a vertex to be enqueued more than ONCE, and it 
 ! is correct in the presence of NEGATIVE edge weights (but no negative cycles), 
 ! but its running time is exponential in the worst case.

 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653
 ? 2. tinyEWDGn.txt (an edge weighted digraph with NEGATIVE edges), P.668
 ? 3. tinyEWDGnc.txt (an edge weighted digraph has CYCLE with NEGATIVE edges), P.669
 ? 4. tinyEWDGnConvert.txt (from tinyEWDGn.txt, for strawman I P.688)
 
*/

import javasrc.ch01_3.LinkedListStackX;
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
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if(pq.contains(w)){
                    pq.changeKey(w, distTo[w]);
                }else{
                    pq.insert(w, distTo[w]);
                }
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
            if(pq.contains(w)){
                pq.changeKey(w, distTo[w]);
            }else{
                pq.insert(w, distTo[w]);
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
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;
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

        // * tester #2 
        StdOut.println("\n2. tinyEWDGn.txt with 3 negative edges");
        filename = "data/tinyEWDGn.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        dsp = new DijkstraSP(g, s);
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
        // dsp = new DijkstraSP(g, s);
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
        g = new EdgeWeightedDigraph(new In(filename));
        s = 0;
        dsp = new DijkstraSP(g, s);
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