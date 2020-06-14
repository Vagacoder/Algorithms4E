package javasrc.ch04_2;

import java.util.Stack;

/*
 * Finding a directed cycle in digraph. P.577
 * 
*/

import lib.*;

public class DirectedCycle{

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;      // * vertices on recursive call stack

    public DirectedCycle(Digraph G){
        int V = G.V();
        onStack = new boolean[V];
        edgeTo = new int[V];
        marked = new boolean[V];
        for (int v = 0; v< V; v++){
            if(!marked[v]){
                dfs(G, v);
            }
        }
    }

    // ! onStack [] marks whether current vertex is on recursove call stack
    private void dfs(Digraph G, int v){
        onStack[v] = true;
        marked[v] = true;
        for(int w: G.adj(v)){
            if(this.hasCycle()){
                return;
            }else if(!marked[w]){
                this.edgeTo[w] = v;
                dfs(G, w);
            }else if(onStack[w]){
                cycle = new Stack<Integer>();
                for(int x = v; x != w; x= edgeTo[x]){
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle(){
        return this.cycle != null;
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }
    

    public static void main(String[] args){
    
    }
}