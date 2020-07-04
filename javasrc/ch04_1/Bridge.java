package javasrc.ch04_1;

import lib.*;

/*
 * 4.1.36 Two-edge connectivity. A bridge in a graph is an edge that, if removed, 
 * would increase the number of connected components. A graph that has no bridges 
 * is said to be two-edge connected. Develop a linear-time DFS-based algorithm 
 * for determining whether a given graph is edge connected.
 * 

 * Official Bridge class from website
 
 ? Test files
 ? tinyG1.txt       tinyG2.txt
 ?  0               0 ______ 2
 ?  |               \       /
 ?  1                \     /
 ?  |                 \   /
 ?  2                   1
  
 ? tinyG3.txt       tinyG4.txt
 ?   0__1              0__1
 ?   \ /               \ /|
 ?    2                 2 |
 ?    |                 | |
 ?    3                 3 |
 ?   / \               / \|
 ?  4__5              4__5

 * Algorithm explain: We do DFS traversal of the given graph. In DFS tree an edge 
 * (u, v) (u is parent of v in DFS tree, from u to v) is bridge if there does not 
 * exist any other alternative to reach u or an ancestor of u from subtree rooted 
 * with v.
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
    // * pre[v] = order (preorder) in which dfs examines v (reaches v at #order dfs calling)
    private int[] pre; 
    // * low[v] = lowest preorder(int pre[]) of any vertex connected to v
    private int[] low; 

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

        // * w(s) is(are) reached from v
        for (int w : G.adj(v)) {
            // * if w is not visited
            // * bridges only in unvisited vertices, visited before suggesting another way to it
            if (pre[w] == -1) {
                dfs(G, v, w);
                low[v] = Math.min(low[v], low[w]);
                // * w is reached from v, pre[w] is set in dfs calling 2 lines above.
                // * If low[w] = pre[w], that means all dfs callings to reach w 
                // * are through v;
                // * If there is another path from v or v's parents to w, low[w] 
                // * will be updated in: 
                // * (1) one line above (in recursive call of dfs(G, v, w));
                // * (2) else if( w!=u ) clause below.
                // * This means if removing the edge from v to w, no way to reach 
                // * v and vertices in v's subtree => edge from v to w is a brdge.
                if (low[w] == pre[w]) {
                    StdOut.println(v + "-" + w + " is a bridge");
                    bridges++;
                }
            }
            // * if w is visited, and, v is not from w
            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                // ? origin code is min(low[v], pre[w]), could it be min(low[v], LOW[w])?
                low[v] = Math.min(low[v], pre[w]);
                // low[v] = Math.min(low[v], low[w]);
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

        StdOut.println("1, test tinyG1.txt");
        String filename1 = "data/tinyG1.txt";
        Graph g1 = new Graph(new In(filename1));
        Bridge b1 = new Bridge(g1);
        StdOut.println(b1.components());

        StdOut.println("\n2, test tinyG2.txt");
        String filename2 = "data/tinyG2.txt";
        Graph g2 = new Graph(new In(filename2));
        Bridge b2 = new Bridge(g2);
        StdOut.println(b2.components());

        StdOut.println("\n3, test tinyG3.txt");
        String filename3 = "data/tinyG3.txt";
        Graph g3 = new Graph(new In(filename3));
        Bridge b3 = new Bridge(g3);
        StdOut.println(b3.components());

        StdOut.println("\n4, test tinyG4.txt");
        String filename4 = "data/tinyG4.txt";
        Graph g4 = new Graph(new In(filename4));
        Bridge b4 = new Bridge(g4);
        StdOut.println(b4.components());
    }

}
