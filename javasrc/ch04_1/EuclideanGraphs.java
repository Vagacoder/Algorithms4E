package javasrc.ch04_1;

/*
 * 4.1.37 Euclidean graphs. Design and implement an API EuclideanGraph for graphs
 * whose vertices are points in the plane that include coordinates. Include a 
 * method show() that uses StdDraw to draw the graph.
 * 
  
 ? Sample data file: tinyEV1.txt
 5
 0.1 0.5
 -0.2 0.3
 0.0 0.0
 0.4 -0.25
 -0.3 -1.0
 4
 1 3
 2 3
 4 5
 2 5
 */

import lib.*;

public class EuclideanGraphs {
    
    private int V;
    private int E;
    private EuclideanVertex[] vertices;

    public EuclideanGraphs(int V){
        this.V = V;
        this.vertices = new EuclideanVertex[V];
    }

    public void addVertex(int index, EuclideanVertex vertex) throws IllegalArgumentException {
        if(index <0 || index > V-1){
            throw new IllegalArgumentException("index out of bound");
        }
        this.vertices[index] = vertex;
    }

    public void show(){

    }

    public static void main(String[] args){

    }
}