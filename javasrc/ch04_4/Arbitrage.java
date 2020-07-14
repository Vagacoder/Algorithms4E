package javasrc.ch04_4;

/*
 * Arbitrage in currency exchange. P.680
 * 
 * Proposition Z. The arbitrage problem is a negative-cycle-detection problem in
 * edge-weighted digraphs.
 * 
 * Replace each weight by its logarithm, negated. With this change, computing path 
 * weights by multiplying edge weights in the original problem corresponds to 
 * adding them in the transformed problem. Specifically:
  
 ?      any product w1 * w2 . . . * wk corresponds to
 ?      a sum - ln(w1) - ln(w2) . . . - ln(wk)
  
 * The transformed edge weights might be negative or positive, a path from v to 
 * w gives a way of converting from currency v to currency w, and any negative 
 * cycle is an arbitrage opportunity.

 */

import lib.*;

public class Arbitrage {

    public static void main(String[] args){
        // int V = StdIn.readInt();

        String filename = "data/rates.txt";
        In in = new In(filename);
        int V = Integer.parseInt(in.readLine());

        String[] name = new String[V];
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(V);
        for(int i = 0; i < V; i++){
            // name[i] = StdIn.readString();
            String line = in.readLine();
            String[] tokens = line.split("\s+");
            name[i] = tokens[0];
            for(int j = 0; j < V; j ++){
                // double rate = StdIn.readDouble();
                double rate = Double.parseDouble(tokens[j+1]);
                DirectedEdge e = new DirectedEdge(i, j, -Math.log(rate));
                g.addEdge(e);
            }
        }

        BellmanFordSP bfsp = new BellmanFordSP(g, 0);
        if(bfsp.hasNegativeCycle()){
            double stake = 1000.0;
            for(DirectedEdge e : bfsp.negativeCycle()){
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        }else{
            StdOut.println("No arbitrage opportunity");
        }
    }
}