package javasrc.ch04_2;

import java.util.ArrayList;
import lib.*;

/*
 * 4.2.7  The indegree of a vertex in a digraph is the number of directed edges 
 * that point to that vertex. 
 * 
 * The outdegree of a vertex in a digraph is the number of directed edges that 
 * emanate from that vertex. 
 * 
 * No vertex is reachable from a vertex of outdegree 0, which is called a sink; 
 * 
 * A vertex of indegree 0, which is called a source, is not reachable from any 
 * other vertex. 
 * 
 * A digraph where self-loops are allowed and every vertex has outdegree 1 is 
 * called a map (a function from the set of integers from 0 to V–1 onto itself). 
 * 
 * Write a program Degrees.java
*/

public class Degree {
    
    private int v;
    private int[] indegrees;
    private int[] outdegrees;
    private boolean isMap = true;

    public Degree(Digraph g){
        v = g.V();
        this.indegrees = new int[v];
        this.outdegrees = new int[v];

        for(int i = 0; i < v; i++){
            boolean selfLoop = false;

            for(int w: g.adj(i)){
                outdegrees[i]++;
                indegrees[w]++;
                if(w == i){
                    selfLoop =true;
                }
            }

            if(!selfLoop || outdegrees[i] != 1){
                isMap = false;
            }
        }
    }

    public int indegree(int v){
        return this.indegrees[v];
    }

    public int outdegree(int v){
        return this.outdegrees[v];
    }

    public int getNumberOfVertices(){
        return this.v;
    }

    public Iterable<Integer> sources(){
        ArrayList<Integer> sources = new ArrayList<>();
        for(int i = 0; i < this.v; i++){
            if(this.indegrees[i] == 0){
                sources.add(i);
            }
        }
        return sources;
    }

    public Iterable<Integer> sinks(){
        ArrayList<Integer> sinks = new ArrayList<>();
        for(int i =0; i < this.v; i++){
            if(this.outdegrees[i] == 0){
                sinks.add(i);
            }
        }
        return sinks;
    }

    public boolean isMap(){
        return this.isMap;
    }

    public static void main(String[] args){
        Digraph dg = new Digraph(new In(args[0]));

        Degree degree = new Degree(dg);

        StdOut.println("sources:");
        for(int s: degree.sources()){
            StdOut.print(s + ", ");
        }
        StdOut.println("\n");

        StdOut.println("sinks:");
        for(int s: degree.sinks()){
            StdOut.print(s + ", ");
        }
        StdOut.println("\n");
    }
}