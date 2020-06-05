package javasrc.ch04_1;

/*
 * 4.1.16 The eccentricity of a vertex v is the length of the shortest path from 
 * that vertex to the furthest vertex from v. The diameter of a graph is the 
 * maximum eccentricity of any vertex. The radius of a graph is the smallest 
 * eccentricity of any vertex. A center is a vertex whose eccentricity is the 
 * radius. Implement the following API:
 
    GraphProperties(Graph G) constructor (exception if G not connected)
    int diameter() diameter of G
    int radius() radius of G
    int center() a center of G

 * 4.1.17 The Wiener index of a graph is the sum of the lengths of the shortest 
 * paths between all pairs of vertices. Mathematical chemists use this quantity 
 * to analyze molecular graphs, where vertices correspond to atoms and edges 
 * correspond to chemical bonds. Add a method wiener() to GraphProperties that 
 * returns the Wiener index of a graph.

 * 4.1.18 The girth of a graph is the length of its shortest cycle. If a graph is 
 * acyclic, then its girth is infinite. Add a method girth() to GraphProperties 
 * that returns the girth of the graph. Hint : Run BFS from each vertex. The 
 * shortest cycle containing s is an edge between s and some vertex v concatenated 
 * with a shortest path between s and v (that doesn’t use the edge s-v).

*/

import lib.*;

public class GraphProperties {

    private int[] longestPaths;
    private int diameter;
    private int radius;
    private int center;
    private int wiener;

    private Graph graph;

    public GraphProperties(Graph g) {
        this.graph = g;

        int v = g.V();
        this.longestPaths = new int[v];
        this.wiener = 0;
        for (int i = 0; i < v; i++) {
            BreadthFirstPaths bfp = new BreadthFirstPaths(g, i);
            int longestP = 0;
            for (int j = 0; j < v; j++) {
                int currentP = bfp.distTo(j);
                if (currentP > longestP) {
                    longestP = currentP;
                }

                this.wiener += currentP > 0?currentP:0;
            }
            this.longestPaths[i] = longestP;
        }

        this.diameter = -1;
        this.radius = Integer.MAX_VALUE;
        this.center = -1;
        for (int i = 0; i < longestPaths.length; i++) {
            int currentP = this.longestPaths[i];
            if (currentP > diameter) {
                diameter = currentP;
            }
            if (currentP < radius) {
                radius = currentP;
                center = i;
            }
        }

    }

    public int diameter() {
        return diameter;
    }

    public int radius() {
        return radius;
    }

    public int center() {
        return center;
    }

    public int wiener(){
        return wiener;
    }

    // * 4.1.18
    public int girth(){
        // * check is acyclic
        Cycle cycle = new Cycle(graph);
        if(!cycle.hasCycle()){
            return -1;
        }

        int shortestCycle = Integer.MAX_VALUE;

        // ? i is starting vertex
        for(int i = 0; i< this.graph.V(); i++){

            // ? bfp based on vertex, all paths start from i
            BreadthFirstPaths bfp = new BreadthFirstPaths(this.graph, i);

            // ? j is ending vertex, iterating all vertices in graph
            for(int j = 0; j < this.graph.V(); j++){

                // ? j should not be i, and j must connect to i
                if(j != i && bfp.hasPathTo(j)){
                    int s1 = Integer.MAX_VALUE;
                    int s2 = Integer.MAX_VALUE;

                    // ? check vertices surrounding with j, find 2 shortest paths
                    for(int w: this.graph.adj(j)){
                        int distance = bfp.distTo(w);
                        if(distance < s1){
                            s2 = s1;
                            s1 = distance;
                        }else if(distance < s2){
                            s2 = distance;
                        }
                    }
                    
                    // ? calculate cycle lenth and compare with shortest cycle length
                    if(s2 != Integer.MAX_VALUE && s1 != Integer.MAX_VALUE){
                        int currentCycle = s1 + s2 + 2;
                        if(currentCycle < shortestCycle){
                            shortestCycle = currentCycle;
                        }
                    }
                }
            }
        }
        return shortestCycle;
    }

    public static void check(){
        // ? using tinyGex2.txt as sample.
        StdOut.println("1. test tinyGex2.txt data");
        Graph g = new Graph(12);
        g.addEdge(8, 4);
        g.addEdge(2, 3);
        g.addEdge(1, 11);
        g.addEdge(0, 6);
        g.addEdge(3, 6);
        g.addEdge(10, 3);
        g.addEdge(7, 11);
        g.addEdge(7, 8);
        g.addEdge(11, 8);
        g.addEdge(2, 0);
        g.addEdge(6, 2);
        g.addEdge(5, 2);
        g.addEdge(5, 10);
        g.addEdge(5, 0);
        g.addEdge(8, 1);
        g.addEdge(4, 1);

        GraphProperties gp = new GraphProperties(g);
        StdOut.println("diameter: " + gp.diameter());
        StdOut.println("radius: " + gp.radius());
        StdOut.println("center: " + gp.center());
        StdOut.println("wiener: " + gp.wiener());
        StdOut.println("girth: " + gp.girth());

        // ? using tinyG.txt as sample.
        StdOut.println("\n2. test tinyG.txt data");
        Graph g1 = new Graph(13);
        g1.addEdge(0, 5);
        g1.addEdge(4, 3);
        g1.addEdge(0, 1);
        g1.addEdge(9, 12);
        g1.addEdge(6, 4);
        g1.addEdge(5, 4);
        g1.addEdge(0, 2);
        g1.addEdge(11, 12);
        g1.addEdge(9, 10);
        g1.addEdge(0, 6);
        g1.addEdge(7, 8);
        g1.addEdge(9, 11);
        g1.addEdge(5, 3);

        GraphProperties gp1 = new GraphProperties(g1);
        StdOut.println("diameter: " + gp1.diameter());
        StdOut.println("radius: " + gp1.radius());
        StdOut.println("center: " + gp1.center());
        StdOut.println("wiener: " + gp1.wiener());
        StdOut.println("girth: " + gp1.girth());
    }
    public static void main(String[] args) {
        check();
    }
}