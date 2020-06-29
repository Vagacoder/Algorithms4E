package javasrc.ch04_3;

import javasrc.ch01_3.LinkedListStack;

/*
 * Cycle detection in Edge-Weighted Graph. 
 * 
 * based on Cycle.java and CycleX.java from ch04.1
 */

import lib.*;

public class EWGcycle {

    private boolean[] marked;
    private Edge[] edgeTo;
    private LinkedListStack<Edge> cycle;

    public EWGcycle(EdgeWeightedGraph g){
        int V = g.V();
        this.marked = new boolean[V];
        this.edgeTo = new Edge[V];

        for(int i = 0; i < V; i++){
            if(!marked[i]){
                dfs(g, i, i);
            }
        }
    }

    private void dfs(EdgeWeightedGraph g, int v, int s){
        this.marked[v] = true;

        for(Edge e: g.adj(v)){
            if(this.cycle != null){
                return;
            }

            int w = e.other(v);
            if(!marked[w]){
                edgeTo[w] = e;
                dfs(g, w, v);
            }else if(w != s){
                cycle = new LinkedListStack<>();
                for(int i = v; i != w; i = edgeTo[i].other(i)){
                    cycle.push(edgeTo[i]);
                }
                cycle.push(e);
            }
        }
    }

    public boolean hasCycle(){
        return this.cycle != null;
    }

    public Iterable<Edge> cycle(){
        return this.cycle;
    }
}