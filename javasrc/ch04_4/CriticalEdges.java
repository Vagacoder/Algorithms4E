package javasrc.ch04_4;

/*
 * 4.4.37 Critical edges. Develop an algorithm for finding an edge whose removal 
 * causes maximal increase in the shortest-paths length from one given vertex to 
 * another given vertex in a given edge-weighted digraph.
 * 

 ? Algorithm: 
 ? (1) get original SP tree from original weighted-digraph;
 ? (2) build new weighted-digraphs using edges from original digraph, without
 ?      one edge from original SP tree.
 ? (3) get new SP tree from new weighted-digraphs
 ? (4) find max new SP distance from new SP trees
 
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 */

import lib.*;

public class CriticalEdges {

    private DirectedEdge criticalEdge;
    private Double distance;
    private Iterable<DirectedEdge> path;

    public CriticalEdges(EdgeWeightedDigraph g, int source, int terminal) {
        int V = g.V();
        this.criticalEdge = null;
        this.distance = Double.POSITIVE_INFINITY;
        this.path = null;

        DijkstraSP dsp = new DijkstraSP(g, source);
        Iterable<DirectedEdge> sPath = dsp.pathTo(terminal);

        if (sPath != null) {
            double maxSPdistance = dsp.distTo(terminal);

            for (DirectedEdge spE : sPath) {
                var newDigraph = new EdgeWeightedDigraph(V);
                for (DirectedEdge originalE : g.edges()) {
                    if (originalE != spE) {
                        newDigraph.addEdge(originalE);
                    }

                    DijkstraSP dspTesting = new DijkstraSP(newDigraph, source);
                    Double newDistance = dspTesting.distTo(terminal);
                    if (newDistance < Double.POSITIVE_INFINITY) {
                        if (newDistance > maxSPdistance) {
                            maxSPdistance = newDistance;
                            criticalEdge = spE;
                            distance = dspTesting.distTo(terminal);
                            path = dspTesting.pathTo(terminal);
                        }
                    }
                }
            }
        }
    }

    public DirectedEdge getCriticalEdge() {
        return this.criticalEdge;
    }

    public double getNewDist(){
        return this.distance;
    }

    public Iterable<DirectedEdge> getNewSP() {
        return this.path;
    }

    public static void main(String[] args) {
        StdOut.println("1. tinyEWDG.txt no negative edge");
        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));
        int s = 0;

        CriticalEdges ce = new CriticalEdges(g, s, 6);
        StdOut.println("Remove: " + ce.getCriticalEdge());
        StdOut.println("New shortest path:");
        for (DirectedEdge e : ce.getNewSP()) {
            StdOut.print(e + "   ");
        }
        StdOut.println();
        StdOut.printf("Distance: %.4f\n", ce.getNewDist());
    }
}