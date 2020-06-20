package javasrc.ch04_2;

/*
* 4.2.32 Hamiltonian path in DAGs. Given a DAG, design a linear-time algorithm 
* to determine whether there is a directed path that visits each vertex exactly 
* once.

* algorithm: for DAG, the path goes through all vertices must start with only source
* and end with only sink.

? tinyDAG.txt schema is at P.583 

*/

import java.util.ArrayList;
import lib.*;

public class HamiltonianPathDAG {

    private ArrayList<Integer> sources = new ArrayList<>();
    private ArrayList<Integer> sinks = new ArrayList<>();
    private Digraph dag;
    
    public HamiltonianPathDAG(Digraph dag){
        int v = dag.V();
        this.dag = dag;

        DirectedCycle dc = new DirectedCycle(dag);
        if(dc.hasCycle()){
            throw new IllegalArgumentException("Digraph is not DAG!");
        }

        for (int i = 0; i < v; i++) {
            if (dag.indegree(i) == 0) {
                sources.add(i);
            }
            if(dag.outdegree(i) ==0 ){
                sinks.add(i);
            }
        }
    }

    public boolean hasHamiltonianPath(){
        if(this.sources.size()==0 && this.sinks.size()==0){
            DepthFirstPaths dfp = new DepthFirstPaths(this.dag, sources.get(0));
            if(dfp.hasPathTo(sinks.get(0))){
                return true;
            }
        }
        return false;
    }

    public void printSources() {
        for (int s : sources) {
            StdOut.print(s + ", ");
        }
        StdOut.println();
    }

    public void printSinks(){
        for(int s : sinks){
            StdOut.print(s + ", ");
        }
        StdOut.println();
    }

    public static void main(String[] args){
        Digraph dg = new Digraph(new In("data/tinyDAG.txt"));
        HamiltonianPathDAG hp = new HamiltonianPathDAG(dg);
        hp.printSources();
        hp.printSinks();

        StdOut.println();

        // ! tinyDG is not a DAG
        Digraph dg1 = new Digraph(new In("data/tinyDG.txt"));
        HamiltonianPathDAG hp1 = new HamiltonianPathDAG(dg1);
        hp1.printSources();
        hp1.printSinks();
    }
}