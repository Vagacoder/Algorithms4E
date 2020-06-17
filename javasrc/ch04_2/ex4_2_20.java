package javasrc.ch04_2;

/*
 * 4.2.20 True or false: The reverse postorder of a digraph’s reverse is the same 
 * as the postorder of the digraph.

*/

import lib.*;

public class ex4_2_20{

    public static void main(String[] args){
    
        String filename = "data/tinyDG.txt";

        Digraph dg = new Digraph(new In(filename));
        Digraph dgr = dg.reverse();

        DepthFirstOrder dfo = new DepthFirstOrder(dg);
        StdOut.println("Graph preorder:");
        for(int v: dfo.pre()){
            StdOut.print(v + ", ");
        }
        StdOut.println("\nGraph post order:");
        for(int v: dfo.post()){
            StdOut.print(v + ", ");
        }
        StdOut.println("\nGraph reverse post order:");
        for(int v: dfo.reversePost()){
            StdOut.print(v + ", ");
        }
        StdOut.println("\n");

        DepthFirstOrder dfor = new DepthFirstOrder(dgr);
        StdOut.println("\nReversed graph preorder:");
        for(int v: dfor.pre()){
            StdOut.print(v + ", ");
        }
        StdOut.println("\nReversed graph post order:");
        for(int v: dfor.post()){
            StdOut.print(v + ", ");
        }
        StdOut.println("\nReversed graph reverse post order:");
        for(int v: dfor.reversePost()){
            StdOut.print(v + ", ");
        }
        StdOut.println();
    }
}