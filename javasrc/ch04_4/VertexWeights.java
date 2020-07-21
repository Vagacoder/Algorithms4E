package javasrc.ch04_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * 4.4.22 Vertex weights. Show that shortest-paths computations in digraphs with 
 * non-negative weights on vertices (where the weight of a path is defined to be 
 * the sum of the weights of the vertices) can be handled by building an edge-
 * weighted digraph that has weights on only the edges.

 ? for digraph only with vertices weights, it is not unique. There could be many
 ? different digraphs.

 */

import lib.*;

public class VertexWeights {

    public static void main(String[] args) throws FileNotFoundException {

        String filename = "data/tinyEWDG.txt";
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(filename));

        int V = g.V();
        int source = 0;

        DijkstraSP dsp = new DijkstraSP(g, source);
        
        String outFileName = "data/tinyEWDGvertexWeight.txt";
        PrintWriter writer = new PrintWriter(new File(outFileName));

        writer.println(V);

        for(int i = 0; i < V; i++){
            if(dsp.hasPathTo(i)){
                double distToI = 0.0;
                for(DirectedEdge e:dsp.pathTo(i)){
                    distToI += e.weight();
                }
                StdOut.printf("From %d to %d distance: %.2f\n", source, i, distToI);
                writer.printf("%d %d %.2f\n", source, i, distToI);
            }
        }

        writer.close();
    }
}