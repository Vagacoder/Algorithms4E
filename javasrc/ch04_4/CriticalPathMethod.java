package javasrc.ch04_4;

/*
 * CPM: Critical Path Method for parallel precedence-constrained job scheduling. P.665 
 * 
 * Proposition U. The critical path method solves the parallel precedence constrained 
 * scheduling problem in linear time.
 * 
 
 ? Sample file jobsPC.txt
 
 * 4.4.18 Write a CPM client that prints all critical paths.
 * 
 */

import lib.*;

public class CriticalPathMethod {

    public static void main(String[] args){

        String filename = "./data/jobsPC.txt";
        In in = new In(filename);

        // int N = StdIn.readInt();
        int N = in.readInt();
        // * each job has 2 vertices. plus 2 more for source and terminal
        int V = 2 * N + 2;
        // StdIn.readLine();
        in.readLine();
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(V);

        // * source
        int s = 2 * N;
        // * terminal
        int t = 2 * N + 1;

        for(int i = 0; i < N; i++){
            // String[] a = StdIn.readLine().split("\\s+");
            String[] a = in.readLine().split("\\s+");
            double duration = Double.parseDouble(a[0]);

            // * each job has a weighted edge (2 vertices), start at i, end at i + N
            g.addEdge(new DirectedEdge(i, i + N, duration));

            // * each job has an edge from source to start, weight = 0
            g.addEdge(new DirectedEdge(s, i, 0.0));

            // * each job has an edge to terminal, weight = 0
            g.addEdge(new DirectedEdge(i + N, t, 0.0));

            // * add precedence constraint, weight = 0
            for(int j = 1; j < a.length; j++){
                int successor = Integer.parseInt(a[j]);
                g.addEdge(new DirectedEdge(i + N, successor, 0.0));
            }
        }

        AcyclicLP lp = new AcyclicLP(g, s);

        StdOut.println("Start times:");
        for(int i =0; i < N; i++){
            StdOut.printf("%4d: %5.1f: ", i, lp.distTo(i));
            if(lp.hasPathTo(i)){
                for(DirectedEdge e: lp.pathTo(i)){
                    StdOut.print(e.toString() + "  ");
                }
            }
            StdOut.println();
        }
        StdOut.printf("Finish time: %5.1f\n", lp.distTo(t));
    }
}