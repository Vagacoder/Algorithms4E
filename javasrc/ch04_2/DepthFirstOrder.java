package javasrc.ch04_2;

/*
* Depth-first search vertex ordering in a digraph. P.580
* 
* This is a helper class. Useful for Strong Connection.
*
* Add support for Edge-weighted directe graph (chapter 4.4 Shortest Paths)
*/

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStack;
import javasrc.ch04_4.DirectedEdge;
import javasrc.ch04_4.EdgeWeightedDigraph;

public class DepthFirstOrder {

    private boolean[] marked;
    private LinkedListQueue<Integer> pre;
    private LinkedListQueue<Integer> post;
    private LinkedListStack<Integer> reversePost;

    public DepthFirstOrder(Digraph G){
        this.pre = new LinkedListQueue<>();
        this.post = new LinkedListQueue<>();
        this.reversePost = new LinkedListStack<>();
        marked = new boolean[G.V()];

        for(int v = 0; v< G.V(); v++){
            if(!marked[v]){
                dfs(G, v);
            }
        }
    }

    // * for chapter 4.4, Edge-weighted directed graph
    public DepthFirstOrder(EdgeWeightedDigraph g){
        this.pre = new LinkedListQueue<>();
        this.post = new LinkedListQueue<>();
        this.reversePost = new LinkedListStack<>();
        int V = g.V();
        marked = new boolean[V];

        for(int i = 0; i < V; i++){
            if(!marked[i]){
                dfs(g, i);
            }
        }
    }

    private void dfs(Digraph G, int v){
        this.pre.enqueue(v);

        marked[v] = true;
        for(int w: G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
        
        this.post.enqueue(v);
        this.reversePost.push(v);
    }

    private void dfs(EdgeWeightedDigraph g, int v){
        this.pre.enqueue(v);

        marked[v] = true;
        for(DirectedEdge e: g.adj(v)){
            int w = e.to();
            if(!marked[w]){
                dfs(g, w);
            }
        }
        
        this.post.enqueue(v);
        this.reversePost.push(v);
    }


    public Iterable<Integer> pre(){
        return this.pre;
    }

    public Iterable<Integer> post(){
        return this.post;
    }

    public Iterable<Integer> reversePost(){
        return this.reversePost;
    }


}