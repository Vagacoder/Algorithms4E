package javasrc.ch04_2;

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStack;

/*
 * Depth-first search vertex ordering in a digraph. P.580
 * 
 * This is a helper class. Useful for Strong Connection.
 */

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