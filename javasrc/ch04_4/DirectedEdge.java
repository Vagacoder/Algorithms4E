package javasrc.ch04_4;

/*
 * Directed weighted edge. P.642 
 */

public class DirectedEdge {
    
    private final int from;
    private final int to;
    private final double weight;

    public DirectedEdge(int v, int w, double weight){
        this.from = v;
        this.to = w;
        this.weight = weight;
    }

    public int from(){
        return this.from;
    }

    public int to(){
        return this.to;
    }

    public double weight(){
        return this.weight;
    }

    public String toString(){
        return String.format("%d->%d %.2f", from, to, weight);
    }
}