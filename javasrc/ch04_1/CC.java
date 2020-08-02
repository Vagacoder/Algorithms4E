package javasrc.ch04_1;

import java.util.ArrayList;
import javasrc.ch01_3.LinkedListQueue;
import lib.*;

/*
 * Algorithm 4.3 Depth-first search to find connected components in a graph
 * 
 * Proposition c. DFS uses preprocessing time and space proportional to V + E to 
 * support constant-time connectivity queries in a graph.
 * 
 * 4.1.24 Compute the number of connected components in movies.txt, the size of 
 * the largest component, and the number of components of size less than 10. 
 * Find the eccentricity, diameter, radius, a center, and the girth of the largest 
 * component in the graph. Does it contain Kevin Bacon?

 ? Sample file:
 ? data/tinyG.txt

 */

public class CC {

    private boolean[] marked;
     // * original name: id
    private int[] componentId;     
    private int count;

    public CC(Graph G){
        marked = new boolean[G.V()];
        componentId = new int[G.V()];
        for(int s = 0; s < G.V(); s++){
            if(!marked[s]){
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        componentId[v] = count;
        for(int w : G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
    }

    // * are v and w connected
    public boolean connected(int v, int w){
        return componentId[v] == componentId[w];
    }

    // * return component identifier for v (between 0 and count()-1)
    public int id(int v){
        return componentId[v];
    }

    // * return number of connected components
    public int count(){
        return count;
    }

    // * 4.1.24 helper
    // * return the size of component, whose id == parameter:index.
    public int componentSize(int componentIndex){
        int size = 0;
        for(int i = 0; i < this.componentId.length; i++){
            if(this.componentId[i] == componentIndex){
                size++;
            }
        }
        return size;
    }

    // * 4.1.24 helper
    // * input componentIndex, return id of first vertex which belongs to this component
    public int[] getFirstVertexId(int componentIndex){
        ArrayList<Integer> vertices = new ArrayList<>();
        for(int i = 0; i < this.componentId.length; i++){
            if(this.componentId[i] == componentIndex){
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

    // * client
    public static void main(String[] args){
        Graph G = new Graph(new In(args[0]));
        CC cc = new CC(G);

        int M = cc.count();
        StdOut.println(M + " components");

        LinkedListQueue<Integer>[] components = 
            (LinkedListQueue<Integer>[]) new LinkedListQueue[M];

        for(int i = 0; i < M; i++){
            components[i] = new LinkedListQueue<Integer>();
        }

        for (int v = 0; v < G.V(); v++){
            components[cc.id(v)].enqueue((v));
        }

        for(int i = 0; i < M; i++){
            for(int v: components[i]){
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }

    
}