package javasrc.ch04_4;

/*
 * Dijkstra all -pairs shortest paths. P.656
 * 
 */
public class DijkstraAllPairsSP {
   
    private DijkstraSP[] all;

    DijkstraAllPairsSP(EdgeWeightedDigraph g){
        int V = g.V();
        all = new DijkstraSP[V];
        for(int i = 0; i < V; i++){
            all[i] = new DijkstraSP(g, i);
        }
    }

    Iterable<DirectedEdge> path(int s, int t){
        return all[s].pathTo(t);
    }

    double dist(int s, int t){
        return all[s].distTo(t);
    }

}