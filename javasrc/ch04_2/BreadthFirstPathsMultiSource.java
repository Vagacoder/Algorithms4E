package javasrc.ch04_2;

/*
* Breadth-first search shortest path from multiple sources
* Based on Algorithm 4.2 Breadth-first search to find paths in a graph. P.540

? sample file
? 1. tinyDG.txt P.589

*/

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch01_3.LinkedListStackX;
import lib.*;

public class BreadthFirstPathsMultiSource {
    
    private boolean[] visited;
    private int[] edgeTo;
    private final int[] sources;


    public BreadthFirstPathsMultiSource(Digraph g, int[] sources){
        this.sources = sources;
        int V = g.V();
        this.visited = new boolean[V];
        this.edgeTo = new int[V];
        
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for(int i = 0; i < sources.length; i++){
            this.visited[sources[i]] = true;
            queue.enqueue(sources[i]);
        }

        while(!queue.isEmpty()){
            int v = queue.dequeue();
            for(int w: g.adj(v)){
                if(!visited[w]){
                    this.edgeTo[w] = v;
                    this.visited[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return this.visited[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)){
            return null;
        }

        LinkedListStackX<Integer> path = new LinkedListStackX<>();
        int cur = v;
        Outerloop:
        while(true){
            path.push(cur);

            for(int i = 0; i < this.sources.length; i++){
                if(sources[i] == cur){
                    break Outerloop;
                }
            }

            cur =  this.edgeTo[cur];
        }
        return path;
    }

    public static void main(String[] args){
        String filename = "data/tinyDG.txt";

        Digraph g = new Digraph(new In(filename));
        int[] sources = {1, 7, 10};
        var bfpms = new BreadthFirstPathsMultiSource(g, sources);

        StdOut.println("1. Is there shortest path to 4: " + bfpms.hasPathTo(4));
        for(int v: bfpms.pathTo(4)){
            StdOut.print(v + " -> ");
        }
        StdOut.println("Done");

        StdOut.println("2. Is there shortest path to 5: " + bfpms.hasPathTo(5));
        for(int v: bfpms.pathTo(5)){
            StdOut.print(v + " -> ");
        }
        StdOut.println( "Done");

        StdOut.println("3. Is there shortest path to 12: " + bfpms.hasPathTo(12));
        for(int v: bfpms.pathTo(12)){
            StdOut.print(v + " -> ");
        }
        StdOut.println( "Done");
    }
}