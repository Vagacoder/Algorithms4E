package javasrc.ch04_1;

/*
 * 4.1.30 Eulerian and Hamiltonian cycles. Consider the graphs defined by the 
 * following four sets of edges:
 * 
 * Which of these graphs have Euler cycles (cycles that visit each edge exactly once)?
 * Which of them have Hamilton cycles (cycles that visit each vertex exactly once)?
 * 
 *  Develop a linear-time DFS-based algorithm to determine whether a graph has an 
 * Euler cycle (and if so find one).
 * 
 
 */

import java.util.ArrayList;
import lib.*;

public class EulerCycle {

    private Graph graph;
    private boolean isCycle;
    private boolean isEulerCycle;

    private boolean[] visited;
    private int[] edgeTo;
    private ArrayList<Integer> cycle;

    public EulerCycle(Graph graph) {
        // this.graph = graph;

        // Cycle cycle = new Cycle(graph);
        // this.isCycle = cycle.hasCycle();

        // if(isCycle){
        int vertexNumber = graph.V();
        this.edgeTo = new int[vertexNumber];
        this.visited = new boolean[vertexNumber];
        this.cycle = new ArrayList<>();

        for (int v = 0; v < vertexNumber; v++) {
            if (!visited[v]) {
                dfs(graph, -1, v);
            }
        }
        // }
    }

    private void dfs(Graph graph, int fromV, int thisV) {
        visited[thisV] = true;

        for (int neighborV : graph.adj(thisV)) {

            // * already found euler cycle
            if(cycle.size() != 0){
                return;
            }

            if (!visited[neighborV]) {
                edgeTo[neighborV] = thisV;
                dfs(graph, thisV, neighborV);
            }
            // * found a cycle
            else if(neighborV != fromV){
                // * get all vertices on this cycle
                for(int cur = thisV; cur != neighborV && cur !=edgeTo[cur]; cur=edgeTo[cur]){
                    cycle.add(cur);
                }
                cycle.add(neighborV);
                cycle.add(thisV);

                boolean areAllEdgeInCycle = true;
                for(int i = 0; i < graph.V(); i++){
                    for(int j: graph.adj(i)){
                        if(!isEdgeInCycle(i, j)){
                            areAllEdgeInCycle = false;
                            break;
                        }
                    }
                    if(!areAllEdgeInCycle){
                        break;
                    }
                }

                if(areAllEdgeInCycle){
                    return;
                }else{
                    cycle.clear();
                }
            }
        }
    }

    private boolean isEdgeInCycle(int a, int b){

        for(int i = 1; i < this.cycle.size(); i++){
            int curV = this.cycle.get(i);
            int preV = this.cycle.get(i-1);
            if(curV == a && preV == b){return true;}
            if(curV == b && preV == a){return true;}
        }
        return false;
    }

    public boolean hasEulerCycle(){
        return cycle.size() != 0;
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    public static void main(String[] args) {
        String filename = "data/eulerCycle.txt";
        In in = new In(filename);
        while(in.hasNextLine()){
            String line = in.readLine();
            String[] edges = line.split(" ");
            Graph g = new Graph(10);
            for(int i = 0; i < edges.length; i++){
                String edge = edges[i];
                g.addEdge(Integer.parseInt(edge.substring(0, 1)), 
                    Integer.parseInt(edge.substring(2, 3)));
            }
            StdOut.println(g.toString());

            EulerCycle ec = new EulerCycle(g);
            StdOut.println(ec.hasEulerCycle());
            StdOut.println();
        }
    }
}