package javasrc.ch04_2;

/*
 * 4.2.30 Shortest ancestral path. Given a DAG and two vertices v and w, find a 
 * shortest ancestral path between v and w. An ancestral path between v and w is 
 * a common ancestor x along with a shortest directed path from v to x and a 
 * shortest directed path from w to x. A shortest ancestral path is the ancestral 
 * path whose total length is minimized. 
 * 
 * Warmup: Find a DAG where the shortest ancestral path goes to a common ancestor 
 * x that is not an LCA. 
 * 
 * Hint: Run BFS twice, once from v and once from w.
 * 
 
 ? tinyDAG.txt schema is at P.583

*/

import lib.*;

public class ShortestAncestralPath{

    private Digraph dg;

    public ShortestAncestralPath(Digraph dg){
        this.dg = dg;
    }

    public int getPShortestAncester(int v1, int v2){
        int V = dg.V();
        int minDist = -1;
        int ancester = -1;
        for(int i = 0; i < V; i++){
            BreadthFirstPaths bfs = new BreadthFirstPaths(dg, i);
            int distance1 = bfs.distTo(v1);
            if(distance1 < 0){
                continue;
            }
            int distance2 = bfs.distTo(v2);
            if(distance2 < 0){
                continue;
            }
            int totalDistance = distance1 + distance2;
            if(minDist < 0 || totalDistance < minDist){
                minDist = totalDistance;
                ancester = i;
            }
        }
        return ancester;
    }

    public static void main(String[] args){
        Digraph dg = new Digraph(new In("data/tinyDAG.txt"));
        ShortestAncestralPath sap = new ShortestAncestralPath(dg);
        StdOut.println(sap.getPShortestAncester(4, 11));
    }
}