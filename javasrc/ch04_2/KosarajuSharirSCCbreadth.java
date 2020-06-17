package javasrc.ch04_2;

/*
* 4.2.23  True or false: If we modify the Kosaraju–Sharir algorithm to replace 
* the second depth-first search with breadth-first search, then it will still 
* find the strong components.

*/

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class KosarajuSharirSCCbreadth{

    private boolean[] marked;
    private int[] componentId;
    private int count;

    public KosarajuSharirSCCbreadth(Digraph g){
        this.marked = new boolean[g.V()];
        this.componentId = new int[g.V()];
        this.count = 0;

        DepthFirstOrder order = new DepthFirstOrder(g.reverse());
        for(int s: order.reversePost()){
            if(!marked[s]){
                bfs(g, s);
                count++;
            }
        }
    }
    
    private void bfs(Digraph g, int s){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        queue.enqueue(s);

        while(!queue.isEmpty()){
            int v = queue.dequeue();
            this.marked[v] = true;
            this.componentId[v] = count;

            for(int w: g.adj(v)){
                if(!marked[w]){
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean stronglyConnected(int v, int w){
        return componentId[v] == componentId[w];
    }

    public int id(int v){
        return this.componentId[v];
    }

    public int count(){
        return this.count;
    }


    public static void main(String[] args){
        // Digraph g = new Digraph(new In("data/tinyDG.txt"));
        Digraph g = new Digraph(new In(args[0]));

        KosarajuSharirSCCbreadth scc = new KosarajuSharirSCCbreadth(g);

        StdOut.println("component #: " + scc.count());

        for(int i = 0; i < g.V(); i++){
            StdOut.println(i + ": " + scc.id(i));
        }

        // * 4.2.22
        StdOut.println();
        KosarajuSharirSCCbreadth sccr = new KosarajuSharirSCCbreadth(g.reverse());

        StdOut.println("component #: " + sccr.count());

        for(int i = 0; i < g.V(); i++){
            StdOut.println(i + ": " + sccr.id(i));
        }
    }
}