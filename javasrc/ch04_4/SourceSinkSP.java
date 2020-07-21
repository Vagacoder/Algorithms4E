package javasrc.ch04_4;

/*
 * 4.4.23 Source-sink shortest paths. Develop an API and implementation that use 
 * a version of Dijkstra’s algorithm to solve the source-sink shortest path problem 
 * on edge-weighted digraphs.
 * 
  
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 */

import lib.*;

public class SourceSinkSP {
    
    private EdgeWeightedDigraph g;

    public SourceSinkSP(EdgeWeightedDigraph g){
        this.g = g;
    }

    // * not used
    public boolean hasSP(int source, int sink){
        if(!isSource(source)){
            return false;
        }
        if(!isSink(sink)){
            return false;
        }
        // TODO
        return true;
    }

    public Iterable<DirectedEdge> getSP(int source, int sink){
        if(!isSource(source)){
            StdOut.println(source + " is not a source!");
            return null;
        }
        if(!isSink(sink)){
            StdOut.println(sink + " is not a sink!");
            return null;
        }

        DijkstraSP dsp = new DijkstraSP(g, source);
        
        return dsp.pathTo(sink);
    }

    private boolean isSource(int source){
        boolean result = false;
        for(int i = 0; i < g.V(); i++){
            if (i != source){
                for(DirectedEdge e: g.adj(i)){
                    if(e.to() == source){
                        return false;
                    }
                }
            }else{
                for(DirectedEdge e: g.adj(i)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private boolean isSink(int sink){
        boolean result = false;
        for(int i = 0; i < g.V(); i++){
            if(i != sink){
                for(DirectedEdge e : g.adj(i)){
                    if(e.to() == sink){
                        result = true;
                        break;
                    }
                }
            }else{
                for(DirectedEdge e: g.adj(i)){
                    return false;
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        StdOut.println("1. test tinyEWDG.txt 1 -> 3");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        SourceSinkSP sssp = new SourceSinkSP(g);
        var sp = sssp.getSP(1, 3);
        if(sp != null){
            for(DirectedEdge e : sp){
                StdOut.println(e.toString());
            }
        }else{
            StdOut.println("No shortest source-sink path");
        }

        StdOut.println("\n2. test tinyEWDAG.txt 5 -> 2");
        filename = "data/tinyEWDAG.txt";
        g = new EdgeWeightedDigraph(new In(filename));
        sssp = new SourceSinkSP(g);
        sp = sssp.getSP(5, 2);
        if(sp != null){
            for(DirectedEdge e : sp){
                StdOut.println(e.toString());
            }
        }else{
            StdOut.println("No shortest source-sink path");
        }
    }
}