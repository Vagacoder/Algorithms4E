package javasrc.ch04_1;

import lib.*;

/*
* P.529
*/

public class TestSearch {
    
    public static void main(String[] args){
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        DepthFirstSearch search = new DepthFirstSearch(G, s);

        for(int v = 0; v < G.V(); v++){
            if(search.marked(v)){
                StdOut.println(v + " ");
            }
        }
        StdOut.println();

        if(search.count() != G.V()){
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}