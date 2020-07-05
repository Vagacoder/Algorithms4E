package javasrc.ch04_1;

/*
 * 4.1.37 Euclidean graphs. Design and implement an API EuclideanGraph for graphs
 * whose vertices are points in the plane that include coordinates. Include a 
 * method show() that uses StdDraw to draw the graph. 
 * 
 * Helper class of vertex
 */

public class EuclideanVertex {
    
    double x;
    double y;

    public EuclideanVertex(double x, double y){
        this.x = x;
        this.y = y;
    }
}