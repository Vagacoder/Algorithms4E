package javasrc.ch04_3;

/*
* Edge weighted graph connected componene
* 
* based on CC.java in ch04.1
*/

import java.util.ArrayList;
import java.util.List;

import lib.*;

public class EWGCC {

    private boolean[] marked;
    private int[] componentId;
    private int count;

    public EWGCC(EdgeWeightedGraph g){
        int V = g.V();
        this.marked = new boolean[V];
        this.componentId = new int[V];
        for(int i = 0; i < V; i++){
            if(!marked[i]){
                dfs(g, i);
                this.count++;
            }
        }
    }
    
    private void dfs(EdgeWeightedGraph g, int v){
        marked[v] = true;
        componentId[v] = this.count;
        for(Edge e: g.adj(v)){
            int w = e.other(v);
            if(!marked[w]){
                dfs(g, w);
            }
        }
    }

    public boolean isConnected(int v1, int v2){
        return componentId[v1] == componentId[v2];
    }

    public int componentId(int v){
        return this.componentId[v];
    }

    public int numberOfComponent(){
        return this.count;
    }

    public int[] getConnectedVertices(int componentId){
        ArrayList<Integer> vertices = new ArrayList<>();
        for(int i = 0; i < this.componentId.length; i++){
            if(this.componentId[i] == componentId){
                vertices.add(i);
            }
        }

        int length = vertices.size();
        int[] result = new int[length];
        for(int i = 0; i < length; i++){
            result[i] = vertices.get(i);
        }
        return result;
    }
}