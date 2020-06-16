package javasrc.ch04_2;

/*
* 4.2.9 Write a method that checks whether a given permutation of a DAG’s vertices 
 * is a topological order of that DAG.
 
 ! A sequence of vertices is topological sort if and only if:
 ? 1. sequence contains all vertices, and each vertex presents only once;
 ? 2. if vertex a presents before vertex b in sequence, there is no edge from b
 ?    to a.

 ! Any DAG has at least one topological sort, could has more.

 * Sample file is tinyDAG.txt, which is based on P.575.
*/

import lib.*;

public class TopologicalUtil {
    
    // * 4.2.9
    // ? Parameter orders example: "8,9,3,4,0,7,1,2"
    public static boolean isTopogicalOrder(Digraph g, String orders){
        
        // * trim potential tailing ","
        if(orders.endsWith(",")){
            orders = orders.substring(0, orders.length()-1);
        }

        String[] vertices = orders.split(",");
        int length = vertices.length;
        int v = g.V();

        // * every vertex must in sequence, and only once
        if(v != length){
            return false;
        }
        for(int i = 0; i < v; i++){
            if(!orders.contains("" + i)){
                return false;
            }
        }

        // * if vertex a shows before vertex b in sequence, there is no edge from b to a
        for(int i = 0; i < length; i++){
            for(int j = i+1; j < length; j++){
                int a = Integer.parseInt(vertices[i]);
                int b = Integer.parseInt(vertices[j]);
                for(int w: g.adj(b)){
                    if(w == a){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args){

        StdOut.println("\nTesting isTopologicalOrder...");
        Digraph dg = new Digraph(new In("data/tinyDAG.txt"));

        // * check P.575 (right picture) for validated topological sequences
        // expect true
        StdOut.println(isTopogicalOrder(dg, "8,7,2,3,0,6,9,10,11,12,1,5,4"));  
        // expect true 
        StdOut.println(isTopogicalOrder(dg, "2,3,8,7,0,6,9,10,11,12,1,5,4")); 
        // expect true 
        StdOut.println(isTopogicalOrder(dg, "2,3,8,7,0,6,9,10,1,5,4,11,12")); 
        // expect false
        StdOut.println(isTopogicalOrder(dg, "8,7,3,2,0,6,9,10,11,12,1,5,4"));   
        // expect false
        StdOut.println(isTopogicalOrder(dg, "8,7,2,3,1,0,6,9,10,11,12,5,4")); 
        // expect false
        StdOut.println(isTopogicalOrder(dg, "8,7,3,2,0,6,9,8,11,12,1,5,4"));  
        // expect false
        StdOut.println(isTopogicalOrder(dg, "8,7,3,2,0,6,9,10,11,12,1,5,4,13"));   
    }
}