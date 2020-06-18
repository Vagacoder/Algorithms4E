package javasrc.ch04_2;

/*
 * 4.2.28 Directed Eulerian cycle. 
 * A directed Eulerian cycle is a directed cycle that contains each edge exactly 
 * once. Write a Digraph client DirectedEulerianCycle that finds a directed Eulerian 
 * cycle or reports that no such cycle exists. 
 * Hint : Prove that a digraph G has a directed Eulerian cycle if and only if G 
 * is strongly connected and each vertex has its indegree equal to its outdegree.
 * 
*/
public class DirectedEulerianCycle {


    public static boolean isDirectedEulerianCycle(Digraph g){

        // * does each vertex's indegree equal its outdegree
        for(int i = 0; i < g.V(); i++){
            if(!isInEqualsOut(g, i)){
                return false;
            }
        }

        // * is whole digraph a strong connected
        KosarajuSharirSCC scc = new KosarajuSharirSCC(g);
        if(scc.count() != 1){
            return false;
        }

        return true;
    }

    private static boolean isInEqualsOut(Digraph dg, int v){
        return dg.indegree(v) == dg.outdegree(v);
    }

    public static void main(String[] args){

    }

}