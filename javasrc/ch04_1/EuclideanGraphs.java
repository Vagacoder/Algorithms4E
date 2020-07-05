package javasrc.ch04_1;

/*
 * 4.1.37 Euclidean graphs. Design and implement an API EuclideanGraph for graphs
 * whose vertices are points in the plane that include coordinates. Include a 
 * method show() that uses StdDraw to draw the graph.
 * 
  
 ? Sample data file: tinyECG1.txt
 5
 0.1 0.5
 -0.2 0.3
 0.0 0.0
 0.4 -0.25
 -0.3 -0.9
 4
 0 2
 1 2
 3 4
 1 4
 */

 import javasrc.ch01_3.BagX;
import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class EuclideanGraphs {
    
    private int V;
    private int E;
    private EuclideanVertex[] vertices;
    private BagX<Integer>[] adj;

    public EuclideanGraphs(int V){
        this.V = V;
        this.E = 0;
        this.vertices = new EuclideanVertex[V];
        this.adj = (BagX<Integer>[]) new BagX[V];
        for(int i = 0; i<V; i++){
            this.adj[i] = new BagX<Integer>();
        }
    }

    public EuclideanGraphs(In in){
        this(Integer.parseInt(in.readLine()));
        for(int i = 0; i < V; i++){
            String line = in.readLine();
            String[] vStr = line.split(" ");
            EuclideanVertex v = new EuclideanVertex(Double.parseDouble(vStr[0]), Double.parseDouble(vStr[1]));
            setVertex(i, v);
        }
        int e = Integer.parseInt(in.readLine());
        for(int i =0; i<e;i++){
            String line = in.readLine();
            String[] vStr = line.split(" ");
            addEdge(Integer.parseInt(vStr[0]), Integer.parseInt(vStr[1]));
        }
    }

    public void setVertex(int index, EuclideanVertex vertex) throws IllegalArgumentException {
        if(index <0 || index > V-1){
            throw new IllegalArgumentException("index out of bound");
        }
        this.vertices[index] = vertex;
    }

    public EuclideanVertex getVertex(int i){
        return this.vertices[i];
    }

    public EuclideanVertex[] getAllVerticesArray(){
        return this.vertices;
    }

    public Iterable<EuclideanVertex> getAllVertices(){
        LinkedListQueue<EuclideanVertex> allV = new LinkedListQueue<>();
        for(int i =0; i < V; i ++){
            if(this.vertices[i]!= null){
                allV.enqueue(this.vertices[i]);
            }
        }
        return allV;
    }

    public int getIndexOfVertex(EuclideanVertex v){
        for(int i = 0; i < V; i++){
            if(this.vertices[i] == v){
                return i;
            }
        }
        return -1;
    }

    public void addEdge(int v1, int v2){
        adj[v1].add(v2);
        adj[v2].add(v1);
        this.E++;
    }

    public Iterable<Integer> adj(int v){
        return this.adj[v];
    }

    public int degree(int v){
        return this.adj[v].size();
    }

    public int getV(){
        return this.V;
    }

    public void show(){
        // * set canvas
        int N = 1;
        StdDraw.setXscale(-N, N);
        StdDraw.setYscale(-N, N);
        // * set x-y axis
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(200, 200, 200);
        StdDraw.line(-1.0, 0.0, 1.0, 0.0);
        StdDraw.line(0.0, -1.0, 0.0, 1.0);
        // * paint vertices
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(255, 50, 0);
        for(EuclideanVertex v: this.getAllVertices()){
            StdDraw.point(v.x, v.y);
        }
        // * paint lines (edegs)
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(255, 0, 200);
        for(int i = 0; i < this.getV(); i++){
            for(int w: this.adj(i)){
                if(w > i){
                    double ix = this.getVertex(i).x;
                    double iy = this.getVertex(i).y;
                    double wx = this.getVertex(w).x;
                    double wy = this.getVertex(w).y;
                    StdDraw.line(ix, iy, wx, wy);
                }
            }
        }
    }

    public static void main(String[] args){
        String filename1 = "data/tinyECG1.txt";
        EuclideanGraphs ecg1 = new EuclideanGraphs(new In(filename1));
        ecg1.show();
    }
}