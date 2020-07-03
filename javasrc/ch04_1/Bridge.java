package javasrc.ch04_1;

import lib.*;

/*
 * 4.1.36 Two-edge connectivity. A bridge in a graph is an edge that, if removed, 
 * would increase the number of connected components. A graph that has no bridges 
 * is said to be two-edge connected. Develop a linear-time DFS-based algorithm 
 * for determining whether a given graph is edge connected.
 * 

 * Official Bridge class from website
*/


/******************************************************************************
 * Compilation: javac Bridge.java Execution: java Bridge V E Dependencies:
 * Graph.java GraphGenerator.java
 *
 * Identifies bridge edges and prints them out. This decomposes a directed graph
 * into two-edge connected components. Runs in O(E + V) time.
 *
 * Key quantity: low[v] = minimum DFS preorder number of v and the set of
 * vertices w for which there is a back edge (x, w) with x a descendant of v and
 * w an ancestor of v.
 *
 * Note: code assumes no parallel edges, e.g., two parallel edges would be
 * (incorrectly) identified as bridges.
 *
 ******************************************************************************/

public class Bridge {
    private int bridges; // number of bridges
    private int cnt; // counter
    private int[] pre; // pre[v] = order in which dfs examines v
    private int[] low; // low[v] = lowest preorder of any vertex connected to v

    public Bridge(Graph G) {
        low = new int[G.V()];
        pre = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            low[v] = -1;
        for (int v = 0; v < G.V(); v++)
            pre[v] = -1;

        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1)
                dfs(G, v, v);
    }

    public int components() {
        return bridges + 1;
    }

    private void dfs(Graph G, int u, int v) {
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : G.adj(v)) {
            // * if w is not visited
            if (pre[w] == -1) {
                dfs(G, v, w);
                low[v] = Math.min(low[v], low[w]);
                if (low[w] == pre[w]) {
                    StdOut.println(v + "-" + w + " is a bridge");
                    bridges++;
                }
            }
            // * if w is visited
            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }
    }

    // test client
    public static void main(String[] args) {
        // * official tester
        // int V = Integer.parseInt(args[0]);
        // int E = Integer.parseInt(args[1]);
        // Graph G = GraphGenerator.simple(V, E);
        // StdOut.println(G);

        // Bridge bridge = new Bridge(G);
        // StdOut.println("Edge connected components = " + bridge.components());

        String filename = "data/tinyG1.txt";
        Graph g = new Graph(new In(filename));
        Bridge b = new Bridge(g);
        StdOut.println(b.components());
    }

}
