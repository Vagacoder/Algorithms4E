package javasrc.ch04_2;

/*
* 4.2.9 Write a method that checks whether a given permutation of a DAG’s vertices 
 * is a topological order of that DAG.
 
 ! A sequence of vertices is topological sort if and only if:
 ? 1. sequence contains all vertices, and each vertex presents only once;
 ? 2. if vertex a presents before vertex b in sequence, there is no edge from b
 ?    to a.

 ! Any DAG has at least one topological sort, could has more.

 * 
 * 4.2.33 Unique topological ordering. Design an algorithm to determine whether 
 * a DAG has a unique topological ordering. 
 * 
 * Hint : A DAG has a unique topological ordering if and only if there is a 
 * directed edge between each pair of consecutive vertices in a topological order 
 * (i.e., the digraph has a Hamiltonian path). If the DAG has multiple topological 
 * orderings, then a second topological order can be obtained by swapping any pair 
 * of consecutive and nonadjacent vertices.
 * 


 * Sample file is tinyDAG.txt, which is based on P.575.
 * Simple DAG files: data/tinyDAG1.txt, tinyDAG2.txt, tinyDAG3.txt
*/

import lib.*;

public class TopologicalUtil {

    // * 4.2.9
    // ? Parameter orders example: "8,9,3,4,0,7,1,2"
    public static boolean isTopogicalOrder(Digraph dag, String orders) {

        DirectedCycle dc = new DirectedCycle(dag);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not DAG!");
        }

        // * trim potential tailing ","
        if (orders.endsWith(",")) {
            orders = orders.substring(0, orders.length() - 1);
        }

        String[] vertices = orders.split(",");
        int length = vertices.length;
        int v = dag.V();

        // * every vertex must in sequence, and only once
        if (v != length) {
            return false;
        }
        for (int i = 0; i < v; i++) {
            if (!orders.contains("" + i)) {
                return false;
            }
        }

        // * if vertex a shows before vertex b in sequence, there is no edge from b to a
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int a = Integer.parseInt(vertices[i]);
                int b = Integer.parseInt(vertices[j]);
                for (int w : dag.adj(b)) {
                    if (w == a) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // * 4.2.33
    // ? Parameter orders example: "8,9,3,4,0,7,1,2"
    public static boolean isUniqueTopologicalOrder(Digraph dag, String orders) {

        DirectedCycle dc = new DirectedCycle(dag);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not DAG!");
        }

        if (!isTopogicalOrder(dag, orders)) {
            throw new IllegalArgumentException("Order is not a topological order!");
        }

        // * trim potential tailing ","
        if (orders.endsWith(",")) {
            orders = orders.substring(0, orders.length() - 1);
        }

        String[] vertices = orders.split(",");
        int length = vertices.length;

        for (int i = 0; i < length - 1; i++) {
            int a = Integer.parseInt(vertices[i]);
            int b = Integer.parseInt(vertices[i+1]);
            boolean isAtoB = false;
            for (int w : dag.adj(a)) {
                if (w == b) {
                    isAtoB = true;
                }
            }
            if(!isAtoB){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        StdOut.println("\n1. Testing isTopologicalOrder...");
        Digraph dag = new Digraph(new In("data/tinyDAG.txt"));

        // * check P.575 (right picture) for validated topological sequences
        // expect true
        StdOut.println(isTopogicalOrder(dag, "8,7,2,3,0,6,9,10,11,12,1,5,4"));
        // expect true
        StdOut.println(isTopogicalOrder(dag, "2,3,8,7,0,6,9,10,11,12,1,5,4"));
        // expect true
        StdOut.println(isTopogicalOrder(dag, "2,3,8,7,0,6,9,10,1,5,4,11,12"));
        // expect false
        StdOut.println(isTopogicalOrder(dag, "8,7,3,2,0,6,9,10,11,12,1,5,4"));
        // expect false
        StdOut.println(isTopogicalOrder(dag, "8,7,2,3,1,0,6,9,10,11,12,5,4"));
        // expect false
        StdOut.println(isTopogicalOrder(dag, "8,7,3,2,0,6,9,8,11,12,1,5,4"));
        // expect false
        StdOut.println(isTopogicalOrder(dag, "8,7,3,2,0,6,9,10,11,12,1,5,4,13"));

        StdOut.println("\n2. Testing isUniqueTopologicalOrder...");
        // expect false;
        StdOut.println(isUniqueTopologicalOrder(dag, "8,7,2,3,0,6,9,10,11,12,1,5,4"));

        // * use a simple DAG file tinyDAG1.txt
        StdOut.println("\n2.1 tinyDAG1.txt");
        Digraph dag1 = new Digraph(new In("data/tinyDAG1.txt"));
        Topological tp1 = new Topological(dag1);
        StdOut.println(tp1.isDAG());
        String order1 = "";
        for (int v : tp1.order()) {
            order1 += (v + ",");
        }
        StdOut.println(order1);
        // expect true
        StdOut.println(isUniqueTopologicalOrder(dag1, order1));

        // * use a simple DAG file tinyDAG2.txt
        StdOut.println("\n2.2 tinyDAG2.txt");
        Digraph dag2 = new Digraph(new In("data/tinyDAG2.txt"));
        Topological tp2 = new Topological(dag2);
        StdOut.println(tp2.isDAG());
        String order2 = "";
        for (int v : tp2.order()) {
            order2 += (v + ",");
        }
        StdOut.println(order2);
        // expect true
        StdOut.println(isUniqueTopologicalOrder(dag2, order2));

        // * use a simple DAG file tinyDAG3.txt
        StdOut.println("\n2.3 tinyDAG3.txt");
        Digraph dag3 = new Digraph(new In("data/tinyDAG3.txt"));
        Topological tp3 = new Topological(dag3);
        StdOut.println(tp3.isDAG());
        String order3 = "";
        for (int v : tp3.order()) {
            order3 += (v + ",");
        }
        StdOut.println(order3);
        // expect false
        StdOut.println(isUniqueTopologicalOrder(dag3, order3));
    }

}